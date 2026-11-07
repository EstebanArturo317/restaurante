
package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.model.Cliente;
import com.casadonagloria.restaurante.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/clientes")
@Tag(
        name = "Clientes",
        description = "Operaciones para gestionar los clientes del restaurante Casa Doña Gloria"
)
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @Operation(
            summary = "Listar clientes",
            description = "Obtiene todos los clientes registrados en el restaurante"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Clientes consultados correctamente"
    )

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Consulta la informacion de un cliente mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(
            @PathVariable int id) {

        Cliente cliente = clienteService.buscarPorId(id);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }


    @Operation(
            summary = "Registrar cliente",
            description = "Registra un nuevo cliente en la base de datos del restaurante"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del cliente invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping
    public ResponseEntity<Cliente> registrarCliente(
            @Valid @RequestBody Cliente cliente) {

        Cliente nuevoCliente =
                clienteService.guardarCliente(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }


    @Operation(
            summary = "Actualizar cliente",
            description = "Actualiza la informacion de un cliente existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del cliente invalidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable int id,
            @Valid @RequestBody Cliente cliente) {

        Cliente actualizado =
                clienteService.actualizarCliente(id, cliente);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }


    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente registrado mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

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
