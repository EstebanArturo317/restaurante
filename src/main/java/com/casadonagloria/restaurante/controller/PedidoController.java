
package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.model.Pedido;
import com.casadonagloria.restaurante.service.PedidoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(
            @PathVariable int id) {

        Pedido pedido = pedidoService.buscarPorId(id);

        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Pedido> listarPorCliente(
            @PathVariable int idCliente) {

        return pedidoService.listarPorCliente(idCliente);
    }

    @PostMapping
    public ResponseEntity<Pedido> registrarPedido(
            @RequestBody Pedido pedido) {

        Pedido nuevoPedido =
                pedidoService.guardarPedido(pedido);

        return ResponseEntity.ok(nuevoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(
            @PathVariable int id,
            @RequestBody Pedido pedido) {

        Pedido existente = pedidoService.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        pedido.setIdPedido(id);

        boolean actualizado =
                pedidoService.actualizarPedido(pedido);

        if (!actualizado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                pedidoService.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(
            @PathVariable int id) {

        boolean eliminado = pedidoService.eliminarPedido(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
