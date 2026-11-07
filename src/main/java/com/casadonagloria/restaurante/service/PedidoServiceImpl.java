
package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.ClienteDAO;
import com.casadonagloria.restaurante.dao.DetallePedidoDAO;
import com.casadonagloria.restaurante.dao.PedidoDAO;
import com.casadonagloria.restaurante.exception.RecursoNoEncontradoException;
import com.casadonagloria.restaurante.model.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoDAO pedidoDAO;
    private final ClienteDAO clienteDAO;
    private final DetallePedidoDAO detallePedidoDAO;

    public PedidoServiceImpl(
            PedidoDAO pedidoDAO,
            ClienteDAO clienteDAO,
            DetallePedidoDAO detallePedidoDAO) {

        this.pedidoDAO = pedidoDAO;
        this.clienteDAO = clienteDAO;
        this.detallePedidoDAO = detallePedidoDAO;
    }

    @Transactional
    @Override
    public Pedido guardarPedido(Pedido pedido) {

        validarCliente(pedido);

        // Todo pedido nuevo comienza en estado RECIBIDO
        pedido.setEstado("RECIBIDO");

        // El total inicialmente es cero
        pedido.setValorTotal(BigDecimal.ZERO);

        return pedidoDAO.crear(pedido);
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoDAO.listarTodos();
    }

    @Override
    public Pedido buscarPorId(int id) {
        return pedidoDAO.obtenerPorId(id);
    }

    @Override
    public List<Pedido> listarPorCliente(int idCliente) {
        return pedidoDAO.listarPorCliente(idCliente);
    }

    @Transactional
    @Override
    public boolean actualizarPedido(Pedido pedido) {

        if (pedidoDAO.obtenerPorId(pedido.getIdPedido()) == null) {
            return false;
        }

        validarCliente(pedido);

        // El total se calcula desde los detalles del pedido
        pedido.setValorTotal(
                calcularTotal(pedido.getIdPedido())
        );

        return pedidoDAO.actualizar(pedido);
    }

    @Transactional
    @Override
    public boolean eliminarPedido(int id) {

        if (pedidoDAO.obtenerPorId(id) == null) {
            return false;
        }

        // Eliminar primero los detalles asociados
        detallePedidoDAO.listarPorPedido(id).forEach(
                detalle -> detallePedidoDAO.eliminar(
                        detalle.getIdDetalle()
                )
        );

        return pedidoDAO.eliminar(id);
    }

    private void validarCliente(Pedido pedido) {

        if (pedido.getCliente() == null ||
                pedido.getCliente().getIdCliente() <= 0) {

            throw new IllegalArgumentException(
                    "Debe seleccionar un cliente valido"
            );
        }

        int idCliente = pedido.getCliente().getIdCliente();

        if (clienteDAO.buscarPorId(idCliente) == null) {

            throw new RecursoNoEncontradoException(
                    "No se encontro el cliente con ID: " + idCliente
            );
        }
    }

    @Transactional
    @Override
    public BigDecimal recalcularTotal(int idPedido) {

        if (pedidoDAO.obtenerPorId(idPedido) == null) {

            throw new RecursoNoEncontradoException(
                    "No se encontro el pedido con ID: " + idPedido
            );
        }

        BigDecimal total = calcularTotal(idPedido);

        pedidoDAO.actualizarTotal(idPedido, total);

        return total;
    }

    private BigDecimal calcularTotal(int idPedido) {

        return detallePedidoDAO.listarPorPedido(idPedido)
                .stream()
                .map(detalle ->
                        BigDecimal.valueOf(
                                detalle.getSubtotal()
                        )
                )
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }
}
