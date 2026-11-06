
package com.casadonagloria.restaurante.repository;

import com.casadonagloria.restaurante.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository
        extends JpaRepository<DetallePedido, Integer> {

    // Buscar los detalles asociados a un pedido
    List<DetallePedido> findByPedido_IdPedido(int idPedido);

}
