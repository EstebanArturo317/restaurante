
package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.DetallePedidoDAO;
import com.casadonagloria.restaurante.model.DetallePedido;
import org.springframework.stereotype.Service;
import java.util.List;
import com.casadonagloria.restaurante.dao.PlatoDAO;
import com.casadonagloria.restaurante.model.Plato;
import com.casadonagloria.restaurante.exception.RecursoNoEncontradoException;

@Service
public class DetallePedidoService {

    private final DetallePedidoDAO detallePedidoDAO;
    private final PlatoDAO platoDAO;


    public DetallePedidoService(
            DetallePedidoDAO detallePedidoDAO,
            PlatoDAO platoDAO) {

        this.detallePedidoDAO = detallePedidoDAO;
        this.platoDAO = platoDAO;
    }



    public DetallePedido guardarDetalle(DetallePedido detallePedido) {


        if (detallePedido.getPlato() == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un plato"
            );
        }


        if (detallePedido.getCantidad() <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero"
            );
        }

        int idPlato = detallePedido.getPlato().getIdPlato();

        Plato plato = platoDAO.obtenerPorId(idPlato);

        if (plato == null) {
            throw new RecursoNoEncontradoException(
                    "No se encontro el plato con ID: " + idPlato
            );
        }

        if (!plato.isEstaActivo()) {
            throw new IllegalArgumentException(
                    "El plato seleccionado no esta disponible"
            );
        }

        int subtotal = (int) Math.round(
                plato.getPrecio() * detallePedido.getCantidad()
        );


        detallePedido.setSubtotal(subtotal);


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
