package com.ieslavereda.ampa.service;

import com.ieslavereda.ampa.model.Usuario;
import com.ieslavereda.ampa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));
    }

    @Transactional(readOnly = true)
    public Usuario findByNia(String nia) {
        return usuarioRepository.findByNia(nia)
                .orElseThrow(() -> new RuntimeException("Usuario con NIA " + nia + " no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAlumnos() {
        return usuarioRepository.findByTipo(Usuario.TipoUsuario.ALUMNO);
    }

    @Transactional(readOnly = true)
    public List<Usuario> findProfesores() {
        return usuarioRepository.findByTipo(Usuario.TipoUsuario.PROFESOR);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscar(String query) {
        return usuarioRepository.findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(query, query);
    }

    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByNia(usuario.getNia())) {
            throw new RuntimeException("Ya existe un usuario con el NIA: " + usuario.getNia());
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario datosNuevos) {
        Usuario existente = findById(id);
        existente.setNombre(datosNuevos.getNombre());
        existente.setApellidos(datosNuevos.getApellidos());
        existente.setTipo(datosNuevos.getTipo());
        existente.setCursoOCargo(datosNuevos.getCursoOCargo());
        existente.setEmail(datosNuevos.getEmail());
        return usuarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario con ID " + id + " no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Registro de un nuevo profesor.
     * Solo usuarios con tipo PROFESOR pueden registrarse.
     */
    public Usuario registrarProfesor(Usuario profesor) {
        if (profesor.getTipo() != Usuario.TipoUsuario.PROFESOR) {
            throw new RuntimeException("Solo los profesores pueden registrarse");
        }
        if (profesor.getUsername() == null || profesor.getUsername().isBlank()) {
            throw new RuntimeException("El username es obligatorio para profesores");
        }
        if (profesor.getPassword() == null || profesor.getPassword().isBlank()) {
            throw new RuntimeException("La contraseña es obligatoria para profesores");
        }
        if (usuarioRepository.existsByUsername(profesor.getUsername())) {
            throw new RuntimeException("Ya existe un profesor con ese username");
        }
        if (usuarioRepository.existsByNia(profesor.getNia())) {
            throw new RuntimeException("Ya existe un usuario con el NIA: " + profesor.getNia());
        }
        // En producción se debería hashear la contraseña (BCrypt, etc.)
        return usuarioRepository.save(profesor);
    }

    /**
     * Login de un profesor.
     * Acepta username o nombre, junto con contraseña.
     * Devuelve un Map con el usuario (sin contraseña) y un token simulado.
     */
    @Transactional(readOnly = true)
    public Map<String, Object> loginProfesor(String identificador, String password) {
        // Buscar por username primero, luego por nombre
        Usuario profesor = usuarioRepository.findByUsername(identificador)
                .or(() -> usuarioRepository.findByNombreIgnoreCaseAndTipo(identificador, Usuario.TipoUsuario.PROFESOR))
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        if (profesor.getTipo() != Usuario.TipoUsuario.PROFESOR) {
            throw new RuntimeException("Solo los profesores pueden iniciar sesión");
        }

        if (!password.equals(profesor.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Devolver datos sin la contraseña
        return Map.of(
            "id", profesor.getId(),
            "nombre", profesor.getNombre(),
            "apellidos", profesor.getApellidos(),
            "username", profesor.getUsername() != null ? profesor.getUsername() : "",
            "tipo", profesor.getTipo().name(),
            "cursoOCargo", profesor.getCursoOCargo() != null ? profesor.getCursoOCargo() : "",
            "email", profesor.getEmail() != null ? profesor.getEmail() : "",
            "nia", profesor.getNia()
        );
    }
}
