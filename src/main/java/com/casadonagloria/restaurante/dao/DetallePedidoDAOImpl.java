
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.model.DetallePedido;
import com.casadonagloria.restaurante.repository.DetallePedidoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DetallePedidoDAOImpl implements DetallePedidoDAO {

    private final DetallePedidoRepository detallePedidoRepository;

    // Inyeccion de dependencias por constructor
    public DetallePedidoDAOImpl(
            DetallePedidoRepository detallePedidoRepository) {

        this.detallePedidoRepository = detallePedidoRepository;
    }

    // Registrar un detalle de pedido
    @Override
    @Transactional
    public DetallePedido crear(DetallePedido detallePedido) {
        return detallePedidoRepository.saveAndFlush(detallePedido);
    }

    // Listar todos los detalles
    @Override
    @Transactional(readOnly = true)
    public List<DetallePedido> listarTodos() {
        return detallePedidoRepository.findAll();
    }

    // Buscar un detalle por su ID
    @Override
    @Transactional(readOnly = true)
    public DetallePedido obtenerPorId(int id) {
        return detallePedidoRepository.findById(id).orElse(null);
    }

    // Buscar los detalles de un pedido
    @Override
    @Transactional(readOnly = true)
    public List<DetallePedido> listarPorPedido(int idPedido) {
        return detallePedidoRepository.findByPedido_IdPedido(idPedido);
    }

    // Actualizar un detalle existente
    @Override
    @Transactional
    public boolean actualizar(DetallePedido detallePedido) {

        if (!detallePedidoRepository.existsById(
                detallePedido.getIdDetalle())) {
            return false;
        }

        detallePedidoRepository.saveAndFlush(detallePedido);
        return true;
    }

    // Eliminar un detalle por su ID
    @Override
    @Transactional
    public boolean eliminar(int id) {

        if (!detallePedidoRepository.existsById(id)) {
            return false;
        }

        detallePedidoRepository.deleteById(id);
        detallePedidoRepository.flush();
        return true;
    }
}
