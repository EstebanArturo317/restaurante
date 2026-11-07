package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.model.DetallePedido;
import java.util.List;

public interface DetallePedidoService {

    DetallePedido guardarDetalle(DetallePedido detallePedido);
    List<DetallePedido> listarDetalles();
    DetallePedido buscarPorId(int id);
    List<DetallePedido> listarPorPedido(int idPedido);
    boolean actualizarDetalle(DetallePedido detallePedido);
    boolean eliminarDetalle(int id);
}
