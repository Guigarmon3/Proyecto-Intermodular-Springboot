package com.ieslavereda.ampa.repository;

import com.ieslavereda.ampa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNia(String nia);

    boolean existsByNia(String nia);

    List<Usuario> findByTipo(Usuario.TipoUsuario tipo);

    List<Usuario> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombre, String apellidos);
}
