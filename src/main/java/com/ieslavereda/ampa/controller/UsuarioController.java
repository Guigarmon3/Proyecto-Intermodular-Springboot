package com.ieslavereda.ampa.controller;

import com.ieslavereda.ampa.model.Usuario;
import com.ieslavereda.ampa.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /** GET /api/usuarios/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    /** GET /api/usuarios/nia/{nia} */
    @GetMapping("/nia/{nia}")
    public ResponseEntity<Usuario> getByNia(@PathVariable String nia) {
        return ResponseEntity.ok(usuarioService.findByNia(nia));
    }

    /** GET /api/usuarios/alumnos */
    @GetMapping("/alumnos")
    public ResponseEntity<List<Usuario>> getAlumnos() {
        return ResponseEntity.ok(usuarioService.findAlumnos());
    }

    /** GET /api/usuarios/profesores */
    @GetMapping("/profesores")
    public ResponseEntity<List<Usuario>> getProfesores() {
        return ResponseEntity.ok(usuarioService.findProfesores());
    }

    /** GET /api/usuarios/buscar?q=nombre */
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscar(@RequestParam String q) {
        return ResponseEntity.ok(usuarioService.buscar(q));
    }

    /** POST /api/usuarios → crear usuario genérico */
    @PostMapping
    public ResponseEntity<Usuario> crear(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(usuario));
    }

    /** PUT /api/usuarios/{id} → actualizar usuario */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizar(id, usuario));
    }

    /** DELETE /api/usuarios/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/usuarios/registro
     * Registrar un nuevo profesor.
     * Body JSON: { "nia": "...", "nombre": "...", "apellidos": "...",
     *              "cursoOCargo": "...", "email": "...",
     *              "username": "...", "password": "...", "tipo": "PROFESOR" }
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registrarProfesor(@RequestBody Usuario profesor) {
        try {
            profesor.setTipo(Usuario.TipoUsuario.PROFESOR);
            Usuario creado = usuarioService.registrarProfesor(profesor);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Profesor registrado correctamente",
                "id", creado.getId(),
                "nombre", creado.getNombre(),
                "username", creado.getUsername()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/usuarios/login
     * Login de un profesor por username o nombre + contraseña.
     * Body JSON: { "identificador": "username_o_nombre", "password": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String identificador = body.get("identificador");
            String password = body.get("password");
            if (identificador == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Identificador y contraseña requeridos"));
            }
            Map<String, Object> resultado = usuarioService.loginProfesor(identificador, password);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }
}
