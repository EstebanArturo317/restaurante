
package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.model.Pedido;
import com.casadonagloria.restaurante.service.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/pedidos")
@Tag(
        name = "Pedidos",
        description = "Operaciones para gestionar los pedidos del restaurante Casa Doña Gloria"
)
public class PedidoController {


    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(
            summary = "Listar pedidos",
            description = "Obtiene todos los pedidos registrados en el restaurante"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pedidos consultados correctamente"
    )

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @Operation(
            summary = "Buscar pedido por ID",
            description = "Consulta un pedido mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(
            @PathVariable int id) {

        Pedido pedido = pedidoService.buscarPorId(id);

        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pedido);
    }

    @Operation(
            summary = "Listar pedidos de un cliente",
            description = "Obtiene los pedidos asociados al identificador de un cliente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Referencia no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto de integridad")
    })

    @GetMapping("/cliente/{idCliente}")
    public List<Pedido> listarPorCliente(
            @PathVariable int idCliente) {

        return pedidoService.listarPorCliente(idCliente);
    }

    @Operation(
            summary = "Registrar pedido",
            description = "Registra un pedido asociado a un cliente existente. El total inicia en cero y se calcula con los detalles"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del pedido invalidos"),
            @ApiResponse(responseCode = "404", description = "Referencia no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto de integridad")
    })

    @PostMapping
    public ResponseEntity<Pedido> registrarPedido(
            @Valid @RequestBody Pedido pedido) {

        Pedido nuevoPedido =
                pedidoService.guardarPedido(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }


    @Operation(
            summary = "Actualizar pedido",
            description = "Actualiza la informacion de un pedido. El total se calcula desde sus detalles"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del pedido invalidos"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "404", description = "Referencia no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto de integridad")
    })

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(
            @PathVariable int id,
            @Valid @RequestBody Pedido pedido) {

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

    @Operation(
            summary = "Eliminar pedido",
            description = "Elimina un pedido mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "404", description = "Referencia no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto de integridad")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(
            @PathVariable int id) {

        boolean eliminado = pedidoService.eliminarPedido(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Cambiar estado del pedido",
            description = "Avanza el pedido al siguiente estado: RECIBIDO, EN_PREPARACION, LISTO, ENTREGADO"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado no valido"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "409", description = "Cambio de estado no permitido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> cambiarEstado(
            @PathVariable int id,
            @RequestParam String nuevoEstado) {

        return ResponseEntity.ok(
                pedidoService.cambiarEstado(id, nuevoEstado)
        );
    }

    @Operation(
            summary = "Listar pedidos por estado",
            description = "Obtiene los pedidos que estan en un estado: RECIBIDO, EN_PREPARACION, LISTO o ENTREGADO"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos consultados correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado no valido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estado/{estado}")
    public List<Pedido> listarPorEstado(@PathVariable String estado) {
        return pedidoService.listarPorEstado(estado);
    }

    @Operation(
            summary = "Repetir un pedido anterior",
            description = "Crea un pedido nuevo para el mismo cliente con los mismos platos y cantidades, usando los precios actuales"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Un plato del pedido original ya no esta disponible"),
            @ApiResponse(responseCode = "404", description = "Pedido original no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/{id}/repetir")
    public ResponseEntity<Pedido> repetirPedido(@PathVariable int id) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedidoService.repetirPedido(id));
    }
}
