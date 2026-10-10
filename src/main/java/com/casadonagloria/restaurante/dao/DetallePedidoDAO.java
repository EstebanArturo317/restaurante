
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.model.DetallePedido;
import java.util.List;

public interface DetallePedidoDAO {

    DetallePedido crear(DetallePedido detallePedido);

    List<DetallePedido> listarTodos();

    DetallePedido obtenerPorId(int id);

    List<DetallePedido> listarPorPedido(int idPedido);

    boolean actualizar(DetallePedido detallePedido);

    boolean eliminar(int id);
}
