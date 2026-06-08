package com.ieslavereda.ampa.controller;

import com.ieslavereda.ampa.model.Usuario;
import com.ieslavereda.ampa.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /** GET /api/usuarios → todos los usuarios */
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    /** GET /api/usuarios/{id} → usuario por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    /** GET /api/usuarios/nia/{nia} → usuario por NIA */
    @GetMapping("/nia/{nia}")
    public ResponseEntity<Usuario> getByNia(@PathVariable String nia) {
        return ResponseEntity.ok(usuarioService.findByNia(nia));
    }

    /** GET /api/usuarios/alumnos → solo alumnos */
    @GetMapping("/alumnos")
    public ResponseEntity<List<Usuario>> getAlumnos() {
        return ResponseEntity.ok(usuarioService.findAlumnos());
    }

    /** GET /api/usuarios/profesores → solo profesores */
    @GetMapping("/profesores")
    public ResponseEntity<List<Usuario>> getProfesores() {
        return ResponseEntity.ok(usuarioService.findProfesores());
    }

    /** GET /api/usuarios/buscar?q=nombre → búsqueda por nombre/apellidos */
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscar(@RequestParam String q) {
        return ResponseEntity.ok(usuarioService.buscar(q));
    }

    /** POST /api/usuarios → crear nuevo usuario */
    @PostMapping
    public ResponseEntity<Usuario> crear(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(usuario));
    }

    /** PUT /api/usuarios/{id} → actualizar usuario */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizar(id, usuario));
    }

    /** DELETE /api/usuarios/{id} → eliminar usuario */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
