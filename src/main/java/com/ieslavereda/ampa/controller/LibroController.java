package com.ieslavereda.ampa.controller;

import com.ieslavereda.ampa.model.Libro;
import com.ieslavereda.ampa.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LibroController {

    private final LibroService libroService;

    /** GET /api/libros → todos los libros */
    @GetMapping
    public ResponseEntity<List<Libro>> getAll() {
        return ResponseEntity.ok(libroService.findAll());
    }

    /** GET /api/libros/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getById(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.findById(id));
    }

    /** GET /api/libros/isbn/{isbn} */
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Libro> getByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(libroService.findByIsbn(isbn));
    }

    /** GET /api/libros/categoria/{categoria} → COMEDIA, TERROR, FICCION, HISTORIA, LIBRO_TEXTO */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Libro>> getByCategoria(@PathVariable Libro.CategoriaLibro categoria) {
        return ResponseEntity.ok(libroService.findByCategoria(categoria));
    }

    /** GET /api/libros/disponibles → libros con stock > 0 */
    @GetMapping("/disponibles")
    public ResponseEntity<List<Libro>> getDisponibles() {
        return ResponseEntity.ok(libroService.findDisponibles());
    }

    /** GET /api/libros/buscar?q=texto → búsqueda por título o autor */
    @GetMapping("/buscar")
    public ResponseEntity<List<Libro>> buscar(@RequestParam String q) {
        return ResponseEntity.ok(libroService.buscar(q));
    }

    /**
     * GET /api/libros/texto → libros de texto
     * Parámetros opcionales: asignatura, nivel (ej: "Matematicas", "1 ESO")
     */
    @GetMapping("/texto")
    public ResponseEntity<List<Libro>> getLibrosTexto(
            @RequestParam(required = false) String asignatura,
            @RequestParam(required = false) String nivel) {
        return ResponseEntity.ok(libroService.findLibrosTexto(asignatura, nivel));
    }

    /** POST /api/libros → crear nuevo libro */
    @PostMapping
    public ResponseEntity<Libro> crear(@Valid @RequestBody Libro libro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crear(libro));
    }

    /** PUT /api/libros/{id} → actualizar libro */
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id, @Valid @RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.actualizar(id, libro));
    }

    /** DELETE /api/libros/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
