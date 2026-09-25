package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.model.Cliente;
import java.util.List;

public interface ClienteDAO {
    Cliente crear(Cliente cliente);
    List<Cliente> listarTodos();
    Cliente obtenerPorId(int id);
    boolean actualizar(Cliente cliente);
    boolean eliminar(int id);
}