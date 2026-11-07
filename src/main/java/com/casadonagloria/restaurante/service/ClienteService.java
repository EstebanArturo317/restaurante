package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.model.Cliente;
import java.util.List;

public interface ClienteService {

    List<Cliente> listarClientes();
    Cliente buscarPorId(int id);
    Cliente guardarCliente(Cliente cliente);
    Cliente actualizarCliente(int id, Cliente cliente);
    boolean eliminarCliente(int id);
}
