package com.ieslavereda.ampa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "nia", unique = true, nullable = false, length = 20)
    @NotBlank(message = "El NIA es obligatorio")
    private String nia;

    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 150)
    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    @NotNull
    private TipoUsuario tipo;

    @Column(name = "curso_o_cargo", length = 100)
    private String cursoOCargo;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    /**
     * Username único para login (solo profesores).
     */
    @Column(name = "username", unique = true, length = 100)
    private String username;

    /**
     * Contraseña hasheada (solo profesores).
     */
    @Column(name = "password", length = 255)
    private String password;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @Builder.Default
    private List<Prestamo> prestamos = new ArrayList<>();

    public enum TipoUsuario {
        ALUMNO, PROFESOR
    }
}
