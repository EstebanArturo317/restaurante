
package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.model.CostoEnvio;
import com.casadonagloria.restaurante.service.CostoEnvioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/costos-envio")
public class CostoEnvioController {

    private final CostoEnvioService costoEnvioService;

    public CostoEnvioController(
            CostoEnvioService costoEnvioService) {
        this.costoEnvioService = costoEnvioService;
    }

    @GetMapping
    public List<CostoEnvio> listar() {
        return costoEnvioService.listar();
    }


    @GetMapping("/{id}")
    public ResponseEntity<CostoEnvio> buscarPorId(
            @PathVariable int id) {

        CostoEnvio costoEnvio =
                costoEnvioService.buscarPorId(id);

        if (costoEnvio == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(costoEnvio);
    }

    @PostMapping
    public ResponseEntity<String> guardar(
            @RequestBody CostoEnvio costoEnvio) {

        costoEnvioService.guardar(costoEnvio);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Costo de envío registrado correctamente");
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(
            @PathVariable int id,
            @RequestBody CostoEnvio costoEnvio) {

        if (costoEnvioService.buscarPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }

        costoEnvio.setIdCostoEnvio(id);
        costoEnvioService.actualizar(costoEnvio);

        return ResponseEntity.ok(
                "Costo de envío actualizado correctamente");
    }

    // Eliminar un costo de envío
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable int id) {

        if (costoEnvioService.buscarPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }

        costoEnvioService.eliminar(id);

        return ResponseEntity.ok(
                "Costo de envío eliminado correctamente");
    }
}
