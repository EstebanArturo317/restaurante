
package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.model.Plato;
import com.casadonagloria.restaurante.service.PlatoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    // Listar todos los platos
    @GetMapping
    public List<Plato> listarPlatos() {
        return platoService.listarPlatos();
    }

    // Buscar un plato por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Plato> buscarPlato(
            @PathVariable int id) {

        Plato plato = platoService.buscarPorId(id);

        if (plato == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(plato);
    }

    // Registrar un nuevo plato
    @PostMapping
    public ResponseEntity<Plato> registrarPlato(
            @RequestBody Plato plato) {

        Plato nuevoPlato = platoService.guardarPlato(plato);

        return ResponseEntity.ok(nuevoPlato);
    }

    // Actualizar un plato existente
    @PutMapping("/{id}")
    public ResponseEntity<Plato> actualizarPlato(
            @PathVariable int id,
            @RequestBody Plato plato) {

        Plato existente = platoService.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        plato.setIdPlato(id);

        boolean actualizado = platoService.actualizarPlato(plato);

        if (!actualizado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                platoService.buscarPorId(id)
        );
    }

    // Eliminar un plato por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlato(
            @PathVariable int id) {

        boolean eliminado = platoService.eliminarPlato(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
