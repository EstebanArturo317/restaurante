package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.model.Plato;
import java.util.List;

public interface PlatoService {

    List<Plato> listarPlatos();
    Plato buscarPorId(int id);
    Plato guardarPlato(Plato plato);
    boolean actualizarPlato(Plato plato);
    boolean eliminarPlato(int id);
}
