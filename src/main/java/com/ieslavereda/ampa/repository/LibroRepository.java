package com.ieslavereda.ampa.repository;

import com.ieslavereda.ampa.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    List<Libro> findByCategoria(Libro.CategoriaLibro categoria);

    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    List<Libro> findByAutorContainingIgnoreCase(String autor);

    /** Todos los libros con stock disponible */
    List<Libro> findByCantidadDisponibleGreaterThan(int cantidad);

    /** Libros de texto por asignatura y nivel */
    List<Libro> findByAsignaturaIgnoreCaseAndNivelEducativoIgnoreCase(String asignatura, String nivel);

    /** Libros de texto por asignatura */
    List<Libro> findByAsignaturaIgnoreCase(String asignatura);

    /** Búsqueda por título o autor */
    @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Libro> buscar(String q);
}
