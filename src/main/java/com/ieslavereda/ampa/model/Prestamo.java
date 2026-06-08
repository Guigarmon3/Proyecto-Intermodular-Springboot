package com.ieslavereda.ampa.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Representa un préstamo de un libro a un usuario (alumno o profesor).
 * Esta tabla registra quién tiene cada libro prestado y su historial.
 */
@Entity
@Table(name = "prestamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario que tiene el libro en préstamo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuario;

    /**
     * Libro prestado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Libro libro;

    /**
     * Fecha en que se realizó el préstamo.
     */
    @Column(name = "fecha_prestamo", nullable = false)
    @Builder.Default
    private LocalDate fechaPrestamo = LocalDate.now();

    /**
     * Fecha límite de devolución (por defecto 30 días).
     */
    @Column(name = "fecha_devolucion_prevista")
    private LocalDate fechaDevolucionPrevista;

    /**
     * Fecha real en que se devolvió el libro. Null si sigue prestado.
     */
    @Column(name = "fecha_devolucion_real")
    private LocalDate fechaDevolucionReal;

    /**
     * Estado del préstamo.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    @Builder.Default
    private EstadoPrestamo estado = EstadoPrestamo.PENDIENTE;

    /**
     * Observaciones opcionales (ej: "libro devuelto con daños").
     */
    @Column(name = "observaciones", length = 300)
    private String observaciones;

    /**
     * Indica si el préstamo está activo (libro no devuelto).
     */
    public boolean isActivo() {
        return this.estado == EstadoPrestamo.PENDIENTE;
    }

    /**
     * Indica si el préstamo está vencido.
     */
    public boolean isVencido() {
        return this.estado == EstadoPrestamo.PENDIENTE
                && this.fechaDevolucionPrevista != null
                && LocalDate.now().isAfter(this.fechaDevolucionPrevista);
    }

    public enum EstadoPrestamo {
        PENDIENTE,   // El libro está prestado, no devuelto
        DEVUELTO,    // El libro ha sido devuelto
        VENCIDO      // El plazo ha pasado sin devolución
    }
}
