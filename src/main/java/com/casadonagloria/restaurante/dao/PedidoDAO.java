
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.model.Pedido;
import java.math.BigDecimal;
import java.util.List;

public interface PedidoDAO {

    Pedido crear(Pedido pedido);

    List<Pedido> listarTodos();

    Pedido obtenerPorId(int id);

    List<Pedido> listarPorCliente(int idCliente);

    boolean actualizar(Pedido pedido);

    boolean actualizarTotal(int idPedido, BigDecimal total);

    boolean eliminar(int id);
}
