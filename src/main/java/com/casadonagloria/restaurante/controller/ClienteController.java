
package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.model.Cliente;
import com.casadonagloria.restaurante.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Consultar todos los clientes
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    // Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(
            @PathVariable int id) {

        Cliente cliente = clienteService.buscarPorId(id);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    // Registrar un cliente
    @PostMapping
    public ResponseEntity<Cliente> registrarCliente(
            @RequestBody Cliente cliente) {

        Cliente nuevoCliente =
                clienteService.guardarCliente(cliente);

        return ResponseEntity.ok(nuevoCliente);
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable int id,
            @RequestBody Cliente cliente) {

        Cliente actualizado =
                clienteService.actualizarCliente(id, cliente);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(
            @PathVariable int id) {

        boolean eliminado =
                clienteService.eliminarCliente(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
