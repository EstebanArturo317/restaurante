
package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.model.DetallePedido;
import com.casadonagloria.restaurante.service.DetallePedidoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(
            DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    public List<DetallePedido> listarDetalles() {
        return detallePedidoService.listarDetalles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> buscarDetalle(
            @PathVariable int id) {

        DetallePedido detalle =
                detallePedidoService.buscarPorId(id);

        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(detalle);
    }

    @GetMapping("/pedido/{idPedido}")
    public List<DetallePedido> listarPorPedido(
            @PathVariable int idPedido) {

        return detallePedidoService.listarPorPedido(idPedido);
    }

    @PostMapping
    public ResponseEntity<DetallePedido> registrarDetalle(
            @RequestBody DetallePedido detallePedido) {

        DetallePedido nuevoDetalle =
                detallePedidoService.guardarDetalle(detallePedido);

        return ResponseEntity.ok(nuevoDetalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizarDetalle(
            @PathVariable int id,
            @RequestBody DetallePedido detallePedido) {

        DetallePedido existente =
                detallePedidoService.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        detallePedido.setIdDetalle(id);

        boolean actualizado =
                detallePedidoService.actualizarDetalle(detallePedido);

        if (!actualizado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                detallePedidoService.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(
            @PathVariable int id) {

        boolean eliminado =
                detallePedidoService.eliminarDetalle(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
