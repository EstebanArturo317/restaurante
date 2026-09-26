
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.model.Cliente;
import java.util.List;

public interface ClienteDAO {

    List<Cliente> listarClientes();

    Cliente buscarPorId(int id);

    Cliente guardarCliente(Cliente cliente);

    Cliente actualizarCliente(int id, Cliente cliente);

    boolean eliminarCliente(int id);
}
