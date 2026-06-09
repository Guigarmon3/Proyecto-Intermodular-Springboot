package com.ieslavereda.ampa.controller;

import com.ieslavereda.ampa.model.Prestamo;
import com.ieslavereda.ampa.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PrestamoController {

    private final PrestamoService prestamoService;

    /** GET /api/prestamos → todos los préstamos */
    @GetMapping
    public ResponseEntity<List<Prestamo>> getAll() {
        return ResponseEntity.ok(prestamoService.findAll());
    }

    /** GET /api/prestamos/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.findById(id));
    }

    /** GET /api/prestamos/usuario/{usuarioId} → historial de un usuario */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Prestamo>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(prestamoService.findByUsuario(usuarioId));
    }

    /** GET /api/prestamos/nia/{nia} → historial de un usuario por su NIA */
    @GetMapping("/nia/{nia}")
    public ResponseEntity<List<Prestamo>> getByNia(@PathVariable String nia) {
        return ResponseEntity.ok(prestamoService.findByNia(nia));
    }

    /** GET /api/prestamos/activos → préstamos pendientes de devolución */
    @GetMapping("/activos")
    public ResponseEntity<List<Prestamo>> getActivos() {
        return ResponseEntity.ok(prestamoService.findActivos());
    }

    /** GET /api/prestamos/vencidos → préstamos vencidos */
    @GetMapping("/vencidos")
    public ResponseEntity<List<Prestamo>> getVencidos() {
        return ResponseEntity.ok(prestamoService.findVencidos());
    }

    /**
     * POST /api/prestamos → realizar un préstamo
     * Body JSON: { "usuarioId": 1, "libroId": 2, "dias": 30 }
     */
    @PostMapping
    public ResponseEntity<Prestamo> realizarPrestamo(@RequestBody Map<String, Object> body) {
        Long usuarioId = Long.valueOf(body.get("usuarioId").toString());
        Long libroId = Long.valueOf(body.get("libroId").toString());
        int dias = body.containsKey("dias") ? Integer.parseInt(body.get("dias").toString()) : 30;

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prestamoService.realizarPrestamo(usuarioId, libroId, dias));
    }

    /**
     * PUT /api/prestamos/{id}/devolver → marcar libro como devuelto
     * Body JSON (opcional): { "observaciones": "texto" }
     */
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolverLibro(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {
        String obs = (body != null) ? body.get("observaciones") : null;
        return ResponseEntity.ok(prestamoService.devolverLibro(id, obs));
    }

    /**
     * PUT /api/prestamos/{id}/recuperar → devolver un préstamo DEVUELTO a estado PENDIENTE
     */
    @PutMapping("/{id}/recuperar")
    public ResponseEntity<?> recuperarPrestamo(@PathVariable Long id) {
        try {
            Prestamo prestamo = prestamoService.recuperarPrestamo(id);
            return ResponseEntity.ok(prestamo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * PUT /api/prestamos/actualizar-vencidos → marcar automáticamente los vencidos
     * Devuelve cuántos fueron actualizados.
     */
    @PutMapping("/actualizar-vencidos")
    public ResponseEntity<Map<String, Object>> actualizarVencidos() {
        int actualizados = prestamoService.actualizarVencidos();
        return ResponseEntity.ok(Map.of(
                "mensaje", "Préstamos vencidos actualizados",
                "cantidad", actualizados
        ));
    }
}
