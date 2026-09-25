package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.ClienteDAO;
import com.casadonagloria.restaurante.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteDAO clienteDAO;

    public Cliente crearCliente(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        if (cliente.getTelefono() == null || cliente.getTelefono().isBlank())
            throw new IllegalArgumentException("El teléfono del cliente es obligatorio");
        return clienteDAO.crear(cliente);
    }

    public List<Cliente> listarClientes() { return clienteDAO.listarTodos(); }

    public Cliente obtenerCliente(int id) {
        Cliente cliente = clienteDAO.obtenerPorId(id);
        if (cliente == null) throw new RuntimeException("No existe un cliente con id " + id);
        return cliente;
    }

    public Cliente actualizarCliente(int id, Cliente cliente) {
        cliente.setIdCliente(id);
        if (!clienteDAO.actualizar(cliente)) throw new RuntimeException("No existe un cliente con id " + id);
        return cliente;
    }

    public void eliminarCliente(int id) {
        if (!clienteDAO.eliminar(id)) throw new RuntimeException("No existe un cliente con id " + id);
    }
}