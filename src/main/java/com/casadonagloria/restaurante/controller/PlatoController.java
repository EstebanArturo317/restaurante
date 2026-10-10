package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.model.Plato;
import com.casadonagloria.restaurante.service.PlatoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    // Obtener todos los platos
    @GetMapping
    public List<Plato> listarPlatos() {
        return platoService.listarPlatos();
    }
}