package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.PedidoDAO;
import com.casadonagloria.restaurante.model.Cliente;
import com.casadonagloria.restaurante.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoDAO pedidoDAO;

    @InjectMocks
    private PedidoService pedidoService;

    private List<Pedido> pedidos;
    private Pedido pedidoPrueba;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);

        pedidoPrueba = new Pedido();
        pedidoPrueba.setIdPedido(1);
        pedidoPrueba.setCliente(cliente);
        pedidoPrueba.setEstado("RECIBIDO");
        pedidoPrueba.setValorTotal(new BigDecimal("29000"));

        Pedido pedido2 = new Pedido();
        pedido2.setIdPedido(2);
        pedido2.setCliente(cliente);
        pedido2.setEstado("EN_PREPARACION");
        pedido2.setValorTotal(new BigDecimal("22000"));

        pedidos = List.of(pedidoPrueba, pedido2);
    }

    @Test
    void guardarPedido() {
        when(pedidoDAO.crear(pedidoPrueba)).thenReturn(pedidoPrueba);

        Pedido resultado = pedidoService.guardarPedido(pedidoPrueba);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdPedido());
        assertEquals("RECIBIDO", resultado.getEstado());
        verify(pedidoDAO, times(1)).crear(pedidoPrueba);
    }

    @Test
    void listarPedidos() {
        when(pedidoDAO.listarTodos()).thenReturn(pedidos);

        List<Pedido> resultado = pedidoService.listarPedidos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pedidoDAO, times(1)).listarTodos();
    }

    @Test
    void buscarPorId() {
        when(pedidoDAO.obtenerPorId(1)).thenReturn(pedidoPrueba);

        Pedido resultado = pedidoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(new BigDecimal("29000"), resultado.getValorTotal());
        verify(pedidoDAO, times(1)).obtenerPorId(1);
    }

    @Test
    void listarPorCliente() {
        when(pedidoDAO.listarPorCliente(1)).thenReturn(pedidos);

        List<Pedido> resultado = pedidoService.listarPorCliente(1);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1, resultado.get(0).getCliente().getIdCliente());
        verify(pedidoDAO, times(1)).listarPorCliente(1);
    }

    @Test
    void actualizarPedido() {
        when(pedidoDAO.actualizar(pedidoPrueba)).thenReturn(true);

        boolean resultado = pedidoService.actualizarPedido(pedidoPrueba);

        assertTrue(resultado);
        verify(pedidoDAO, times(1)).actualizar(pedidoPrueba);
    }

    @Test
    void eliminarPedido() {
        when(pedidoDAO.eliminar(1)).thenReturn(true);

        boolean resultado = pedidoService.eliminarPedido(1);

        assertTrue(resultado);
        verify(pedidoDAO, times(1)).eliminar(1);
    }
}