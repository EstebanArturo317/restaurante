
package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.model.Cliente;
import com.casadonagloria.restaurante.dao.ClienteDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }

    public Cliente buscarPorId(int id) {
        return clienteDAO.buscarPorId(id);
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteDAO.guardarCliente(cliente);
    }

    public Cliente actualizarCliente(int id, Cliente cliente) {
        return clienteDAO.actualizarCliente(id, cliente);
    }

    public boolean eliminarCliente(int id) {
        return clienteDAO.eliminarCliente(id);
    }
}
