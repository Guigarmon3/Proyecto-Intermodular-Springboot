package com.ieslavereda.ampa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa tanto alumnos como profesores del IES La Vereda.
 * El campo 'tipo' distingue entre ALUMNO y PROFESOR.
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * NIA: Número de Identificación del Alumno/Profesor.
     * Ej: 202601, 202602...
     */
    @Column(name = "nia", unique = true, nullable = false, length = 20)
    @NotBlank(message = "El NIA es obligatorio")
    private String nia;

    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 150)
    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    /**
     * ALUMNO o PROFESOR
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    @NotNull
    private TipoUsuario tipo;

    /**
     * Curso del alumno (ej: "2º DAW") o cargo del profesor (ej: "Matemáticas").
     */
    @Column(name = "curso_o_cargo", length = 100)
    private String cursoOCargo;

    /**
     * Email de contacto (opcional).
     */
    @Column(name = "email", length = 150)
    private String email;

    /**
     * Préstamos activos o pasados de este usuario.
     * mappedBy apunta al campo 'usuario' en Prestamo.
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Prestamo> prestamos = new ArrayList<>();

    public enum TipoUsuario {
        ALUMNO, PROFESOR
    }
}
