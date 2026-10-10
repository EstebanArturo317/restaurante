
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.model.Pedido;
import java.util.List;

public interface PedidoDAO {

    Pedido crear(Pedido pedido);

    List<Pedido> listarTodos();

    Pedido obtenerPorId(int id);

    List<Pedido> listarPorCliente(int idCliente);

    boolean actualizar(Pedido pedido);

    boolean eliminar(int id);
}
