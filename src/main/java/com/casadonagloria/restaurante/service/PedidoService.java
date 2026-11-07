package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.model.Pedido;
import java.math.BigDecimal;
import java.util.List;

public interface PedidoService {

    Pedido guardarPedido(Pedido pedido);
    List<Pedido> listarPedidos();
    Pedido buscarPorId(int id);
    List<Pedido> listarPorCliente(int idCliente);
    boolean actualizarPedido(Pedido pedido);
    boolean eliminarPedido(int id);
    BigDecimal recalcularTotal(int idPedido);
}
