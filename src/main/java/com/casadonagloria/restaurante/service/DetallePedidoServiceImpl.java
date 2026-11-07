
package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.DetallePedidoDAO;
import com.casadonagloria.restaurante.dao.PedidoDAO;
import com.casadonagloria.restaurante.dao.PlatoDAO;
import com.casadonagloria.restaurante.exception.RecursoNoEncontradoException;
import com.casadonagloria.restaurante.model.DetallePedido;
import com.casadonagloria.restaurante.model.Plato;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoDAO detallePedidoDAO;
    private final PlatoDAO platoDAO;
    private final PedidoDAO pedidoDAO;

    public DetallePedidoServiceImpl(
            DetallePedidoDAO detallePedidoDAO,
            PlatoDAO platoDAO,
            PedidoDAO pedidoDAO) {

        this.detallePedidoDAO = detallePedidoDAO;
        this.platoDAO = platoDAO;
        this.pedidoDAO = pedidoDAO;
    }

    @Transactional
    @Override
    public DetallePedido guardarDetalle(DetallePedido detallePedido) {

        int idPedido = validarYCalcular(detallePedido);

        DetallePedido creado = detallePedidoDAO.crear(detallePedido);

        recalcularTotal(idPedido);

        return creado;
    }

    @Override
    public List<DetallePedido> listarDetalles() {
        return detallePedidoDAO.listarTodos();
    }

    @Override
    public DetallePedido buscarPorId(int id) {
        return detallePedidoDAO.obtenerPorId(id);
    }

    @Override
    public List<DetallePedido> listarPorPedido(int idPedido) {
        return detallePedidoDAO.listarPorPedido(idPedido);
    }

    @Transactional
    @Override
    public boolean actualizarDetalle(DetallePedido detallePedido) {

        DetallePedido anterior =
                detallePedidoDAO.obtenerPorId(detallePedido.getIdDetalle());

        if (anterior == null) {
            return false;
        }

        int idAnterior = anterior.getPedido().getIdPedido();

        int idNuevo = validarYCalcular(detallePedido);

        if (!detallePedidoDAO.actualizar(detallePedido)) {
            return false;
        }

        recalcularTotal(idAnterior);

        if (idAnterior != idNuevo) {
            recalcularTotal(idNuevo);
        }

        return true;
    }

    @Transactional
    @Override
    public boolean eliminarDetalle(int id) {

        DetallePedido anterior = detallePedidoDAO.obtenerPorId(id);

        if (anterior == null) {
            return false;
        }

        int idPedido = anterior.getPedido().getIdPedido();

        if (!detallePedidoDAO.eliminar(id)) {
            return false;
        }

        recalcularTotal(idPedido);

        return true;
    }

    private int validarYCalcular(DetallePedido detalle) {

        // Validar que el pedido sea valido
        if (detalle.getPedido() == null ||
                detalle.getPedido().getIdPedido() <= 0) {

            throw new IllegalArgumentException(
                    "Debe seleccionar un pedido valido"
            );
        }

        int idPedido = detalle.getPedido().getIdPedido();

        // Comprobar que el pedido exista
        if (pedidoDAO.obtenerPorId(idPedido) == null) {

            throw new RecursoNoEncontradoException(
                    "No se encontro el pedido con ID: " + idPedido
            );
        }

        // Validar que se haya seleccionado un plato
        if (detalle.getPlato() == null ||
                detalle.getPlato().getIdPlato() <= 0) {

            throw new IllegalArgumentException(
                    "Debe seleccionar un plato valido"
            );
        }

        // Validar la cantidad
        if (detalle.getCantidad() <= 0) {

            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero"
            );
        }

        int idPlato = detalle.getPlato().getIdPlato();

        // Consultar el precio real del plato
        Plato plato = platoDAO.obtenerPorId(idPlato);

        if (plato == null) {

            throw new RecursoNoEncontradoException(
                    "No se encontro el plato con ID: " + idPlato
            );
        }

        // Comprobar que este disponible
        if (!plato.isEstaActivo()) {

            throw new IllegalArgumentException(
                    "El plato seleccionado no esta disponible"
            );
        }

        // Calcular el subtotal
        BigDecimal subtotal = BigDecimal.valueOf(plato.getPrecio())
                .multiply(BigDecimal.valueOf(detalle.getCantidad()))
                .setScale(0, RoundingMode.HALF_UP);

        try {
            detalle.setSubtotal(subtotal.intValueExact());
        } catch (ArithmeticException ex) {

            throw new IllegalArgumentException(
                    "El subtotal supera el valor permitido",
                    ex
            );
        }

        return idPedido;
    }

    private void recalcularTotal(int idPedido) {

        BigDecimal total = detallePedidoDAO.listarPorPedido(idPedido)
                .stream()
                .map(detalle ->
                        BigDecimal.valueOf(detalle.getSubtotal())
                )
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        pedidoDAO.actualizarTotal(idPedido, total);
    }
}
