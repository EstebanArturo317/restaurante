
package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.model.DetallePedido;
import com.casadonagloria.restaurante.service.DetallePedidoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/detalles-pedido")
@Tag(
        name = "Detalles de pedidos",
        description = "Operaciones para gestionar los platos asociados a los pedidos del restaurante Casa Doña Gloria"
)
public class DetallePedidoController {


    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(
            DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @Operation(
            summary = "Listar detalles de pedidos",
            description = "Obtiene todos los detalles de pedidos registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Detalles consultados correctamente"
    )

    @GetMapping
    public List<DetallePedido> listarDetalles() {
        return detallePedidoService.listarDetalles();
    }

    @Operation(
            summary = "Buscar detalle por ID",
            description = "Consulta un detalle de pedido mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })

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

    @Operation(
            summary = "Listar detalles de un pedido",
            description = "Obtiene todos los platos y cantidades asociados a un pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Referencia no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto de integridad")
    })

    @GetMapping("/pedido/{idPedido}")
    public List<DetallePedido> listarPorPedido(
            @PathVariable int idPedido) {

        return detallePedidoService.listarPorPedido(idPedido);
    }

    @Operation(
            summary = "Registrar detalle de pedido",
            description = "Agrega un plato y calcula automaticamente su subtotal y el total del pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del detalle invalidos"),
            @ApiResponse(responseCode = "404", description = "Referencia no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto de integridad")
    })

    @PostMapping
    public ResponseEntity<DetallePedido> registrarDetalle(
            @Valid @RequestBody DetallePedido detallePedido) {

        DetallePedido nuevoDetalle =
                detallePedidoService.guardarDetalle(detallePedido);

        return ResponseEntity.ok(nuevoDetalle);
    }

    @Operation(
            summary = "Actualizar detalle de pedido",
            description = "Modifica un detalle y recalcula el subtotal y el total del pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del detalle invalidos"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "404", description = "Referencia no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto de integridad")
    })

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizarDetalle(
            @PathVariable int id,
            @Valid @RequestBody DetallePedido detallePedido) {

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

    @Operation(
            summary = "Eliminar detalle de pedido",
            description = "Elimina un detalle mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Detalle eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "404", description = "Referencia no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto de integridad")
    })

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
