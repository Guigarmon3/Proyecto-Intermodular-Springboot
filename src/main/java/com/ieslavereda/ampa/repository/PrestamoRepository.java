package com.ieslavereda.ampa.repository;

import com.ieslavereda.ampa.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    /** Todos los préstamos de un usuario */
    List<Prestamo> findByUsuarioId(Long usuarioId);

    /** Préstamos activos (PENDIENTE) de un usuario */
    List<Prestamo> findByUsuarioIdAndEstado(Long usuarioId, Prestamo.EstadoPrestamo estado);

    /** Todos los préstamos activos de un libro */
    List<Prestamo> findByLibroIdAndEstado(Long libroId, Prestamo.EstadoPrestamo estado);

    /** Préstamos vencidos: PENDIENTE y fecha prevista < hoy */
    @Query("SELECT p FROM Prestamo p WHERE p.estado = 'PENDIENTE' AND p.fechaDevolucionPrevista < :hoy")
    List<Prestamo> findVencidos(LocalDate hoy);

    /** Todos los préstamos activos del sistema */
    List<Prestamo> findByEstado(Prestamo.EstadoPrestamo estado);

    /** Préstamos por NIA del usuario */
    @Query("SELECT p FROM Prestamo p WHERE p.usuario.nia = :nia ORDER BY p.fechaPrestamo DESC")
    List<Prestamo> findByNiaUsuario(String nia);
}
