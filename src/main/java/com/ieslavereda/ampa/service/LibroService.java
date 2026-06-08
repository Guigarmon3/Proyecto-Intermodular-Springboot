package com.ieslavereda.ampa.service;

import com.ieslavereda.ampa.model.Libro;
import com.ieslavereda.ampa.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;

    @Transactional(readOnly = true)
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Libro findById(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro con ID " + id + " no encontrado"));
    }

    @Transactional(readOnly = true)
    public Libro findByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Libro con ISBN " + isbn + " no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Libro> findByCategoria(Libro.CategoriaLibro categoria) {
        return libroRepository.findByCategoria(categoria);
    }

    @Transactional(readOnly = true)
    public List<Libro> findDisponibles() {
        return libroRepository.findByCantidadDisponibleGreaterThan(0);
    }

    @Transactional(readOnly = true)
    public List<Libro> buscar(String query) {
        return libroRepository.buscar(query);
    }

    @Transactional(readOnly = true)
    public List<Libro> findLibrosTexto(String asignatura, String nivel) {
        if (asignatura != null && nivel != null) {
            return libroRepository.findByAsignaturaIgnoreCaseAndNivelEducativoIgnoreCase(asignatura, nivel);
        } else if (asignatura != null) {
            return libroRepository.findByAsignaturaIgnoreCase(asignatura);
        }
        return libroRepository.findByCategoria(Libro.CategoriaLibro.LIBRO_TEXTO);
    }

    public Libro crear(Libro libro) {
        if (libro.getIsbn() != null && libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new RuntimeException("Ya existe un libro con el ISBN: " + libro.getIsbn());
        }
        if (libro.getCantidadDisponible() == null) {
            libro.setCantidadDisponible(libro.getCantidadTotal());
        }
        return libroRepository.save(libro);
    }

    public Libro actualizar(Long id, Libro datosNuevos) {
        Libro existente = findById(id);
        existente.setTitulo(datosNuevos.getTitulo());
        existente.setAutor(datosNuevos.getAutor());
        existente.setGeneros(datosNuevos.getGeneros());
        existente.setSinopsis(datosNuevos.getSinopsis());
        existente.setPaginas(datosNuevos.getPaginas());
        existente.setEdadRecomendada(datosNuevos.getEdadRecomendada());
        existente.setCantidadTotal(datosNuevos.getCantidadTotal());
        existente.setCategoria(datosNuevos.getCategoria());
        existente.setAsignatura(datosNuevos.getAsignatura());
        existente.setNivelEducativo(datosNuevos.getNivelEducativo());
        return libroRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro con ID " + id + " no encontrado");
        }
        libroRepository.deleteById(id);
    }

    /** Reducir stock disponible al prestar */
    public void reducirDisponibilidad(Long libroId) {
        Libro libro = findById(libroId);
        if (libro.getCantidadDisponible() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles del libro: " + libro.getTitulo());
        }
        libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);
        libroRepository.save(libro);
    }

    /** Aumentar stock disponible al devolver */
    public void aumentarDisponibilidad(Long libroId) {
        Libro libro = findById(libroId);
        if (libro.getCantidadDisponible() >= libro.getCantidadTotal()) {
            throw new RuntimeException("Todos los ejemplares ya están disponibles");
        }
        libro.setCantidadDisponible(libro.getCantidadDisponible() + 1);
        libroRepository.save(libro);
    }
}
