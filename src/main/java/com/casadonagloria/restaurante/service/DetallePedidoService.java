
package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.DetallePedidoDAO;
import com.casadonagloria.restaurante.model.DetallePedido;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService {

    private final DetallePedidoDAO detallePedidoDAO;

    public DetallePedidoService(DetallePedidoDAO detallePedidoDAO) {
        this.detallePedidoDAO = detallePedidoDAO;
    }

    public DetallePedido guardarDetalle(DetallePedido detallePedido) {
        return detallePedidoDAO.crear(detallePedido);
    }

    public List<DetallePedido> listarDetalles() {
        return detallePedidoDAO.listarTodos();
    }

    public DetallePedido buscarPorId(int id) {
        return detallePedidoDAO.obtenerPorId(id);
    }

    public List<DetallePedido> listarPorPedido(int idPedido) {
        return detallePedidoDAO.listarPorPedido(idPedido);
    }

    public boolean actualizarDetalle(DetallePedido detallePedido) {
        return detallePedidoDAO.actualizar(detallePedido);
    }

    public boolean eliminarDetalle(int id) {
        return detallePedidoDAO.eliminar(id);
    }
}
