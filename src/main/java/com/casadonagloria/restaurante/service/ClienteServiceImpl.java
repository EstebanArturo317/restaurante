
package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.model.Cliente;
import com.casadonagloria.restaurante.dao.ClienteDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteServiceImpl(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }

    @Override
    public Cliente buscarPorId(int id) {
        return clienteDAO.buscarPorId(id);
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteDAO.guardarCliente(cliente);
    }

    @Override
    public Cliente actualizarCliente(int id, Cliente cliente) {
        return clienteDAO.actualizarCliente(id, cliente);
    }

    @Override
    public boolean eliminarCliente(int id) {
        return clienteDAO.eliminarCliente(id);
    }
}
