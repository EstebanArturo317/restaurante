package com.casadonagloria.restaurante.controller;

import com.casadonagloria.restaurante.dao.PlatoDAO;
import com.casadonagloria.restaurante.model.Plato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    @Autowired
    private PlatoDAO platoDAO;

    // Obtener todos los platos
    @GetMapping
    public List<Plato> listarPlatos() {
        return platoDAO.listarTodos();
    }
}