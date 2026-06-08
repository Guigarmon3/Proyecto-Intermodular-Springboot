package com.ieslavereda.ampa.service;

import com.ieslavereda.ampa.model.Libro;
import com.ieslavereda.ampa.model.Prestamo;
import com.ieslavereda.ampa.model.Usuario;
import com.ieslavereda.ampa.repository.PrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroService libroService;
    private final UsuarioService usuarioService;

    /** Todos los préstamos */
    @Transactional(readOnly = true)
    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    /** Préstamo por ID */
    @Transactional(readOnly = true)
    public Prestamo findById(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo con ID " + id + " no encontrado"));
    }

    /** Historial de préstamos de un usuario por su ID */
    @Transactional(readOnly = true)
    public List<Prestamo> findByUsuario(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId);
    }

    /** Historial de préstamos de un usuario por su NIA */
    @Transactional(readOnly = true)
    public List<Prestamo> findByNia(String nia) {
        return prestamoRepository.findByNiaUsuario(nia);
    }

    /** Préstamos activos (pendientes de devolución) */
    @Transactional(readOnly = true)
    public List<Prestamo> findActivos() {
        return prestamoRepository.findByEstado(Prestamo.EstadoPrestamo.PENDIENTE);
    }

    /** Préstamos vencidos */
    @Transactional(readOnly = true)
    public List<Prestamo> findVencidos() {
        return prestamoRepository.findVencidos(LocalDate.now());
    }

    /**
     * Realizar un nuevo préstamo.
     * Reduce la disponibilidad del libro automáticamente.
     *
     * @param usuarioId ID del usuario
     * @param libroId   ID del libro
     * @param diasPrestamo Días que dura el préstamo (por defecto 30)
     */
    public Prestamo realizarPrestamo(Long usuarioId, Long libroId, int diasPrestamo) {
        Usuario usuario = usuarioService.findById(usuarioId);
        Libro libro = libroService.findById(libroId);

        if (!libro.isDisponible()) {
            throw new RuntimeException("El libro '" + libro.getTitulo() + "' no tiene ejemplares disponibles");
        }

        // Reducir stock
        libroService.reducirDisponibilidad(libroId);

        // Crear préstamo
        Prestamo prestamo = Prestamo.builder()
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(LocalDate.now())
                .fechaDevolucionPrevista(LocalDate.now().plusDays(diasPrestamo))
                .estado(Prestamo.EstadoPrestamo.PENDIENTE)
                .build();

        return prestamoRepository.save(prestamo);
    }

    /**
     * Devolver un libro.
     * Aumenta la disponibilidad del libro automáticamente.
     */
    public Prestamo devolverLibro(Long prestamoId, String observaciones) {
        Prestamo prestamo = findById(prestamoId);

        if (prestamo.getEstado() == Prestamo.EstadoPrestamo.DEVUELTO) {
            throw new RuntimeException("Este préstamo ya fue marcado como devuelto");
        }

        // Devolver al stock
        libroService.aumentarDisponibilidad(prestamo.getLibro().getId());

        // Actualizar préstamo
        prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
        prestamo.setFechaDevolucionReal(LocalDate.now());
        if (observaciones != null && !observaciones.isBlank()) {
            prestamo.setObservaciones(observaciones);
        }

        return prestamoRepository.save(prestamo);
    }

    /** Actualizar préstamos vencidos automáticamente */
    public int actualizarVencidos() {
        List<Prestamo> vencidos = prestamoRepository.findVencidos(LocalDate.now());
        vencidos.forEach(p -> p.setEstado(Prestamo.EstadoPrestamo.VENCIDO));
        prestamoRepository.saveAll(vencidos);
        return vencidos.size();
    }
}
