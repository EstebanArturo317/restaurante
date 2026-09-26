package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.model.Plato;
import java.util.List;

public interface PlatoDAO {
    Plato crear(Plato plato);
    List<Plato> listarTodos();
    Plato obtenerPorId(int id);
    boolean actualizar(Plato plato);
    boolean eliminar(int id);
}