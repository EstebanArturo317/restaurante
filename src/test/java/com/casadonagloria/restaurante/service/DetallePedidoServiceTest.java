package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.DetallePedidoDAO;
import com.casadonagloria.restaurante.model.DetallePedido;
import com.casadonagloria.restaurante.model.Pedido;
import com.casadonagloria.restaurante.model.Plato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.casadonagloria.restaurante.dao.PlatoDAO;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class DetallePedidoServiceTest {

    @Mock
    private DetallePedidoDAO detallePedidoDAO;

    @Mock
    private PlatoDAO platoDAO;

    @InjectMocks
    private DetallePedidoService detallePedidoService;

    private List<DetallePedido> detalles;
    private DetallePedido detallePrueba;

    @BeforeEach
    void setUp() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);

        Plato plato = new Plato();
        plato.setIdPlato(1);

        detallePrueba = new DetallePedido();
        detallePrueba.setIdDetalle(1);
        detallePrueba.setPedido(pedido);
        detallePrueba.setPlato(plato);
        detallePrueba.setCantidad(2);
        detallePrueba.setSubtotal(28000);

        DetallePedido detalle2 = new DetallePedido();
        detalle2.setIdDetalle(2);
        detalle2.setPedido(pedido);
        detalle2.setPlato(plato);
        detalle2.setCantidad(1);
        detalle2.setSubtotal(15000);

        detalles = List.of(detallePrueba, detalle2);

        detallePedidoService = new DetallePedidoService(
                detallePedidoDAO,
                platoDAO
        );

    }


    @Test
    void guardarDetalle() {

        Plato platoRegistrado = new Plato();
        platoRegistrado.setIdPlato(1);
        platoRegistrado.setNombre("Hamburguesa Especial");
        platoRegistrado.setPrecio(14000);
        platoRegistrado.setEstaActivo(true);

        when(platoDAO.obtenerPorId(1))
                .thenReturn(platoRegistrado);


        when(detallePedidoDAO.crear(detallePrueba))
                .thenReturn(detallePrueba);


        DetallePedido resultado =
                detallePedidoService.guardarDetalle(detallePrueba);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdDetalle());


        assertEquals(28000, resultado.getSubtotal());


        verify(platoDAO, times(1)).obtenerPorId(1);

        verify(detallePedidoDAO, times(1)).crear(detallePrueba);
    }


    @Test
    void listarDetalles() {
        when(detallePedidoDAO.listarTodos()).thenReturn(detalles);

        List<DetallePedido> resultado = detallePedidoService.listarDetalles();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(detallePedidoDAO, times(1)).listarTodos();
    }

    @Test
    void buscarPorId() {
        when(detallePedidoDAO.obtenerPorId(1)).thenReturn(detallePrueba);

        DetallePedido resultado = detallePedidoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(2, resultado.getCantidad());
        verify(detallePedidoDAO, times(1)).obtenerPorId(1);
    }

    @Test
    void listarPorPedido() {
        when(detallePedidoDAO.listarPorPedido(1)).thenReturn(detalles);

        List<DetallePedido> resultado = detallePedidoService.listarPorPedido(1);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1, resultado.get(0).getPedido().getIdPedido());
        verify(detallePedidoDAO, times(1)).listarPorPedido(1);
    }

    @Test
    void actualizarDetalle() {
        when(detallePedidoDAO.actualizar(detallePrueba)).thenReturn(true);

        boolean resultado = detallePedidoService.actualizarDetalle(detallePrueba);

        assertTrue(resultado);
        verify(detallePedidoDAO, times(1)).actualizar(detallePrueba);
    }

    @Test
    void eliminarDetalle() {
        when(detallePedidoDAO.eliminar(1)).thenReturn(true);

        boolean resultado = detallePedidoService.eliminarDetalle(1);

        assertTrue(resultado);
        verify(detallePedidoDAO, times(1)).eliminar(1);
    }
}