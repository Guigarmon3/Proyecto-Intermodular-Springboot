package com.ieslavereda.ampa.service;

import com.ieslavereda.ampa.model.Usuario;
import com.ieslavereda.ampa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /** Obtener todos los usuarios */
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /** Obtener usuario por ID */
    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));
    }

    /** Obtener usuario por NIA */
    @Transactional(readOnly = true)
    public Usuario findByNia(String nia) {
        return usuarioRepository.findByNia(nia)
                .orElseThrow(() -> new RuntimeException("Usuario con NIA " + nia + " no encontrado"));
    }

    /** Obtener solo alumnos */
    @Transactional(readOnly = true)
    public List<Usuario> findAlumnos() {
        return usuarioRepository.findByTipo(Usuario.TipoUsuario.ALUMNO);
    }

    /** Obtener solo profesores */
    @Transactional(readOnly = true)
    public List<Usuario> findProfesores() {
        return usuarioRepository.findByTipo(Usuario.TipoUsuario.PROFESOR);
    }

    /** Buscar por nombre o apellidos */
    @Transactional(readOnly = true)
    public List<Usuario> buscar(String query) {
        return usuarioRepository.findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(query, query);
    }

    /** Crear nuevo usuario */
    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByNia(usuario.getNia())) {
            throw new RuntimeException("Ya existe un usuario con el NIA: " + usuario.getNia());
        }
        return usuarioRepository.save(usuario);
    }

    /** Actualizar usuario existente */
    public Usuario actualizar(Long id, Usuario datosNuevos) {
        Usuario existente = findById(id);
        existente.setNombre(datosNuevos.getNombre());
        existente.setApellidos(datosNuevos.getApellidos());
        existente.setTipo(datosNuevos.getTipo());
        existente.setCursoOCargo(datosNuevos.getCursoOCargo());
        existente.setEmail(datosNuevos.getEmail());
        return usuarioRepository.save(existente);
    }

    /** Eliminar usuario */
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario con ID " + id + " no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
