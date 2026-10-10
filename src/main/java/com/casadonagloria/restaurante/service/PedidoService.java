
package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.PedidoDAO;
import com.casadonagloria.restaurante.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoDAO pedidoDAO;

    public PedidoService(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public Pedido guardarPedido(Pedido pedido) {
        return pedidoDAO.crear(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoDAO.listarTodos();
    }

    public Pedido buscarPorId(int id) {
        return pedidoDAO.obtenerPorId(id);
    }

    public List<Pedido> listarPorCliente(int idCliente) {
        return pedidoDAO.listarPorCliente(idCliente);
    }

    public boolean actualizarPedido(Pedido pedido) {
        return pedidoDAO.actualizar(pedido);
    }

    public boolean eliminarPedido(int id) {
        return pedidoDAO.eliminar(id);
    }
}
