
package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.model.Plato;
import com.casadonagloria.restaurante.service.PlatoService;
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
@RequestMapping("/api/platos")
@Tag(
        name = "Platos",
        description = "Operaciones para gestionar los platos del restaurante Casa Doña Gloria"
)
public class PlatoController {


    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }


    @Operation(
            summary = "Listar platos",
            description = "Obtiene todos los platos registrados en el restaurante"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Platos consultados correctamente"
    )

    @GetMapping
    public List<Plato> listarPlatos() {
        return platoService.listarPlatos();
    }


    @Operation(
            summary = "Buscar plato por ID",
            description = "Consulta la informacion de un plato mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plato encontrado"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })

    @GetMapping("/{id}")
    public ResponseEntity<Plato> buscarPlato(
            @PathVariable int id) {

        Plato plato = platoService.buscarPorId(id);

        if (plato == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(plato);
    }


    @Operation(
            summary = "Registrar plato",
            description = "Registra un nuevo plato en el menu del restaurante"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Plato registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del plato invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })


    @PostMapping
    public ResponseEntity<Plato> registrarPlato(
            @Valid @RequestBody Plato plato) {

        Plato nuevoPlato = platoService.guardarPlato(plato);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPlato);
    }

    @Operation(
            summary = "Actualizar plato",
            description = "Actualiza la informacion de un plato existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plato actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del plato invalidos"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })

    @PutMapping("/{id}")
    public ResponseEntity<Plato> actualizarPlato(
            @PathVariable int id,
            @Valid @RequestBody Plato plato) {

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


    @Operation(
            summary = "Eliminar plato",
            description = "Elimina un plato del restaurante mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Plato eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

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
