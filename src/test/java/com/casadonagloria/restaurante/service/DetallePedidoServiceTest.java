
package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.DetallePedidoDAO;
import com.casadonagloria.restaurante.dao.PedidoDAO;
import com.casadonagloria.restaurante.dao.PlatoDAO;
import com.casadonagloria.restaurante.exception.RecursoNoEncontradoException;
import com.casadonagloria.restaurante.model.DetallePedido;
import com.casadonagloria.restaurante.model.Pedido;
import com.casadonagloria.restaurante.model.Plato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetallePedidoServiceTest {

    @Mock
    private DetallePedidoDAO detallePedidoDAO;

    @Mock
    private PlatoDAO platoDAO;

    @Mock
    private PedidoDAO pedidoDAO;

    private DetallePedidoService service;
    private Pedido pedido;
    private Plato plato;
    private DetallePedido detalle;

    @BeforeEach
    void preparar() {

        service = new DetallePedidoService(
                detallePedidoDAO,
                platoDAO,
                pedidoDAO
        );

        pedido = new Pedido();
        pedido.setIdPedido(1);

        plato = new Plato();
        plato.setIdPlato(1);
        plato.setPrecio(14000);
        plato.setEstaActivo(true);

        detalle = new DetallePedido();
        detalle.setIdDetalle(3);
        detalle.setPedido(pedido);
        detalle.setPlato(plato);
        detalle.setCantidad(2);
    }

    private void simularReferenciasValidas() {

        when(pedidoDAO.obtenerPorId(1))
                .thenReturn(pedido);

        when(platoDAO.obtenerPorId(1))
                .thenReturn(plato);
    }

    @Test
    void guardarDetalleCalculaSubtotalYTotal() {

        simularReferenciasValidas();

        when(detallePedidoDAO.crear(detalle))
                .thenReturn(detalle);

        when(detallePedidoDAO.listarPorPedido(1))
                .thenReturn(List.of(detalle));

        DetallePedido resultado = service.guardarDetalle(detalle);

        assertEquals(28000, resultado.getSubtotal());

        verify(pedidoDAO).actualizarTotal(
                1,
                new BigDecimal("28000")
        );
    }

    @Test
    void guardarDetalleConPedidoInexistente() {

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.guardarDetalle(detalle)
        );

        verify(detallePedidoDAO, never()).crear(any());
    }

    @Test
    void guardarDetalleConPlatoInactivo() {

        when(pedidoDAO.obtenerPorId(1))
                .thenReturn(pedido);

        plato.setEstaActivo(false);

        when(platoDAO.obtenerPorId(1))
                .thenReturn(plato);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.guardarDetalle(detalle)
        );
    }

    @Test
    void guardarDetalleConCantidadInvalida() {

        detalle.setCantidad(0);

        when(pedidoDAO.obtenerPorId(1))
                .thenReturn(pedido);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.guardarDetalle(detalle)
        );
    }

    @Test
    void listarDetalles() {

        when(detallePedidoDAO.listarTodos())
                .thenReturn(List.of(detalle));

        assertEquals(1, service.listarDetalles().size());
    }

    @Test
    void buscarPorId() {

        when(detallePedidoDAO.obtenerPorId(3))
                .thenReturn(detalle);

        assertSame(detalle, service.buscarPorId(3));
    }

    @Test
    void listarPorPedido() {

        when(detallePedidoDAO.listarPorPedido(1))
                .thenReturn(List.of(detalle));

        assertEquals(1, service.listarPorPedido(1).size());
    }

    @Test
    void actualizarDetalleCalculaSubtotalYTotal() {

        when(detallePedidoDAO.obtenerPorId(3))
                .thenReturn(detalle);

        simularReferenciasValidas();

        when(detallePedidoDAO.actualizar(detalle))
                .thenReturn(true);

        when(detallePedidoDAO.listarPorPedido(1))
                .thenReturn(List.of(detalle));

        assertTrue(service.actualizarDetalle(detalle));

        assertEquals(28000, detalle.getSubtotal());

        verify(pedidoDAO).actualizarTotal(
                1,
                new BigDecimal("28000")
        );
    }

    @Test
    void actualizarDetalleInexistente() {

        assertFalse(service.actualizarDetalle(detalle));
    }

    @Test
    void eliminarDetalleRecalculaTotal() {

        when(detallePedidoDAO.obtenerPorId(3))
                .thenReturn(detalle);

        when(detallePedidoDAO.eliminar(3))
                .thenReturn(true);

        when(detallePedidoDAO.listarPorPedido(1))
                .thenReturn(List.of());

        assertTrue(service.eliminarDetalle(3));

        verify(pedidoDAO).actualizarTotal(
                1,
                BigDecimal.ZERO
        );
    }

    @Test
    void eliminarDetalleInexistente() {

        assertFalse(service.eliminarDetalle(3));
    }
}
