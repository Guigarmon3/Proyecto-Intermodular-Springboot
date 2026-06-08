package com.ieslavereda.ampa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un libro de la biblioteca AMPA o del inventario de clase.
 */
@Entity
@Table(name = "libros")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 200)
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    /**
     * Autor del libro. Los libros de texto pueden no tenerlo.
     */
    @Column(name = "autor", length = 150)
    private String autor;

    /**
     * ISBN del libro.
     */
    @Column(name = "isbn", unique = true, length = 20)
    private String isbn;

    /**
     * Categoría principal: COMEDIA, TERROR, FICCION, HISTORIA, LIBRO_TEXTO
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaLibro categoria;

    /**
     * Géneros separados por coma. Ej: "Comedia, Fantasía Humorística, Aventura"
     */
    @Column(name = "generos", length = 300)
    private String generos;

    @Column(name = "sinopsis", columnDefinition = "TEXT")
    private String sinopsis;

    @Column(name = "paginas")
    private Integer paginas;

    @Column(name = "edad_recomendada", length = 20)
    private String edadRecomendada;

    /**
     * Asignatura si es libro de texto (Ej: "Matemáticas", "Historia", "Inglés", "Biología").
     * Null si es libro de la biblioteca AMPA.
     */
    @Column(name = "asignatura", length = 100)
    private String asignatura;

    /**
     * Curso si es libro de texto (Ej: "1 ESO", "2 ESO", "3 ESO", "4 ESO").
     */
    @Column(name = "nivel_educativo", length = 50)
    private String nivelEducativo;

    /**
     * Cantidad total de ejemplares que tiene el centro.
     */
    @Column(name = "cantidad_total", nullable = false)
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Builder.Default
    private Integer cantidadTotal = 1;

    /**
     * Ejemplares actualmente disponibles para préstamo.
     */
    @Column(name = "cantidad_disponible", nullable = false)
    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    @Builder.Default
    private Integer cantidadDisponible = 1;

    /**
     * Historial de préstamos de este libro.
     */
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Prestamo> prestamos = new ArrayList<>();

    /**
     * Indica si el libro está disponible para préstamo.
     */
    public boolean isDisponible() {
        return this.cantidadDisponible > 0;
    }

    public enum CategoriaLibro {
        COMEDIA,
        TERROR,
        FICCION,
        HISTORIA,
        LIBRO_TEXTO
    }
}
