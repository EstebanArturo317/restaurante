
package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.ClienteDAO;
import com.casadonagloria.restaurante.dao.DetallePedidoDAO;
import com.casadonagloria.restaurante.dao.PedidoDAO;
import com.casadonagloria.restaurante.exception.RecursoNoEncontradoException;
import com.casadonagloria.restaurante.exception.ReglaNegocioException;
import com.casadonagloria.restaurante.model.Cliente;
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
class PedidoServiceTest {

    @Mock
    private PedidoDAO pedidoDAO;

    @Mock
    private ClienteDAO clienteDAO;

    @Mock
    private DetallePedidoDAO detallePedidoDAO;

    @Mock
    private DetallePedidoService detallePedidoService;

    private PedidoServiceImpl pedidoService;
    private Cliente cliente;
    private Pedido pedido;

    @BeforeEach
    void setUp() {

        pedidoService = new PedidoServiceImpl(
                pedidoDAO,
                clienteDAO,
                detallePedidoDAO,
                detallePedidoService
        );

        cliente = new Cliente();
        cliente.setIdCliente(1);

        pedido = new Pedido();
        pedido.setIdPedido(1);
        pedido.setCliente(cliente);
        pedido.setEstado("RECIBIDO");
        pedido.setValorTotal(new BigDecimal("29000"));
    }

    @Test
    void guardarPedidoIniciaTotalEnCero() {

        when(clienteDAO.buscarPorId(1))
                .thenReturn(cliente);

        when(pedidoDAO.crear(pedido))
                .thenReturn(pedido);

        Pedido resultado = pedidoService.guardarPedido(pedido);

        assertNotNull(resultado);
        assertEquals(BigDecimal.ZERO, resultado.getValorTotal());
        assertEquals("RECIBIDO", resultado.getEstado());

        verify(pedidoDAO).crear(pedido);
    }

    @Test
    void guardarPedidoSinClienteExistenteLanza404() {

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> pedidoService.guardarPedido(pedido)
        );

        verify(pedidoDAO, never()).crear(any());
    }

    @Test
    void listarPedidos() {

        when(pedidoDAO.listarTodos())
                .thenReturn(List.of(pedido));

        assertEquals(1, pedidoService.listarPedidos().size());

        verify(pedidoDAO).listarTodos();
    }

    @Test
    void buscarPorId() {

        when(pedidoDAO.obtenerPorId(1))
                .thenReturn(pedido);

        Pedido resultado = pedidoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdPedido());

        verify(pedidoDAO).obtenerPorId(1);
    }

    @Test
    void listarPorCliente() {

        when(pedidoDAO.listarPorCliente(1))
                .thenReturn(List.of(pedido));

        List<Pedido> resultado =
                pedidoService.listarPorCliente(1);

        assertEquals(1, resultado.size());

        verify(pedidoDAO).listarPorCliente(1);
    }

    @Test
    void actualizarPedidoPreservaTotalCalculado() {

        when(pedidoDAO.obtenerPorId(1))
                .thenReturn(pedido);

        when(clienteDAO.buscarPorId(1))
                .thenReturn(cliente);

        when(detallePedidoDAO.listarPorPedido(1))
                .thenReturn(List.of());

        when(pedidoDAO.actualizar(pedido))
                .thenReturn(true);

        boolean resultado =
                pedidoService.actualizarPedido(pedido);

        assertTrue(resultado);
        assertEquals(BigDecimal.ZERO, pedido.getValorTotal());

        verify(pedidoDAO).actualizar(pedido);
    }

    @Test
    void actualizarPedidoNoExistente() {

        boolean resultado =
                pedidoService.actualizarPedido(pedido);

        assertFalse(resultado);

        verify(pedidoDAO, never()).actualizar(any());
    }

    @Test
    void eliminarPedido() {

        when(pedidoDAO.obtenerPorId(1))
                .thenReturn(pedido);

        DetallePedido detalle = new DetallePedido();
        detalle.setIdDetalle(9);

        when(detallePedidoDAO.listarPorPedido(1))
                .thenReturn(List.of(detalle));

        when(pedidoDAO.eliminar(1))
                .thenReturn(true);

        boolean resultado =
                pedidoService.eliminarPedido(1);

        assertTrue(resultado);

        verify(detallePedidoDAO).eliminar(9);
        verify(pedidoDAO).eliminar(1);
    }

    @Test
    void eliminarPedidoNoExistente() {

        boolean resultado =
                pedidoService.eliminarPedido(22);

        assertFalse(resultado);

        verify(pedidoDAO, never()).eliminar(anyInt());
    }

    @Test
    void recalcularTotal() {

        when(pedidoDAO.obtenerPorId(1))
                .thenReturn(pedido);

        DetallePedido detalle1 = new DetallePedido();
        detalle1.setSubtotal(14000);

        DetallePedido detalle2 = new DetallePedido();
        detalle2.setSubtotal(22000);

        when(detallePedidoDAO.listarPorPedido(1))
                .thenReturn(List.of(detalle1, detalle2));

        BigDecimal resultado =
                pedidoService.recalcularTotal(1);

        assertEquals(
                new BigDecimal("36000"),
                resultado
        );

        verify(pedidoDAO).actualizarTotal(
                1,
                new BigDecimal("36000")
        );
    }

    @Test
    void cambiarEstadoAvanzaAlSiguientePaso() {

        when(pedidoDAO.obtenerPorId(1)).thenReturn(pedido);

        Pedido resultado =
                pedidoService.cambiarEstado(1, "EN_PREPARACION");

        assertEquals("EN_PREPARACION", resultado.getEstado());

        verify(pedidoDAO).actualizar(pedido);
    }

    @Test
    void cambiarEstadoConSaltoInvalidoLanzaReglaNegocio() {

        when(pedidoDAO.obtenerPorId(1)).thenReturn(pedido);

        assertThrows(
                ReglaNegocioException.class,
                () -> pedidoService.cambiarEstado(1, "LISTO")
        );

        verify(pedidoDAO, never()).actualizar(any());
    }

    @Test
    void cambiarEstadoConEstadoDesconocidoLanzaIllegalArgument() {

        when(pedidoDAO.obtenerPorId(1)).thenReturn(pedido);

        assertThrows(
                IllegalArgumentException.class,
                () -> pedidoService.cambiarEstado(1, "CANCELADO")
        );
    }

    @Test
    void cambiarEstadoDePedidoInexistenteLanzaNoEncontrado() {

        when(pedidoDAO.obtenerPorId(99)).thenReturn(null);

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> pedidoService.cambiarEstado(99, "EN_PREPARACION")
        );
    }

    @Test
    void listarPorEstadoDevuelveSoloLosDeEseEstado() {

        Pedido listo = new Pedido();
        listo.setIdPedido(2);
        listo.setEstado("LISTO");

        when(pedidoDAO.listarTodos()).thenReturn(List.of(pedido, listo));

        List<Pedido> resultado = pedidoService.listarPorEstado("LISTO");

        assertEquals(1, resultado.size());
        assertEquals(2, resultado.get(0).getIdPedido());
    }

    @Test
    void listarPorEstadoConEstadoDesconocidoLanzaIllegalArgument() {

        assertThrows(
                IllegalArgumentException.class,
                () -> pedidoService.listarPorEstado("CANCELADO")
        );
    }

    @Test
    void repetirPedidoCreaPedidoNuevoConLosMismosDetalles() {

        Plato plato = new Plato();
        plato.setIdPlato(5);

        DetallePedido original = new DetallePedido();
        original.setPlato(plato);
        original.setCantidad(2);

        Pedido nuevo = new Pedido();
        nuevo.setIdPedido(2);
        nuevo.setCliente(cliente);

        when(pedidoDAO.obtenerPorId(1)).thenReturn(pedido);
        when(clienteDAO.buscarPorId(1)).thenReturn(cliente);
        when(pedidoDAO.crear(any(Pedido.class))).thenReturn(nuevo);
        when(detallePedidoDAO.listarPorPedido(1))
                .thenReturn(List.of(original));
        when(pedidoDAO.obtenerPorId(2)).thenReturn(nuevo);
        when(detallePedidoDAO.listarPorPedido(2)).thenReturn(List.of());

        Pedido resultado = pedidoService.repetirPedido(1);

        assertEquals(2, resultado.getIdPedido());

        verify(detallePedidoService).guardarDetalle(
                argThat(detalle ->
                        detalle.getPedido() == nuevo
                                && detalle.getPlato() == plato
                                && detalle.getCantidad() == 2)
        );
    }

    @Test
    void repetirPedidoInexistenteLanzaNoEncontrado() {

        when(pedidoDAO.obtenerPorId(99)).thenReturn(null);

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> pedidoService.repetirPedido(99)
        );

        verify(pedidoDAO, never()).crear(any());
    }

    @Test
    void buscarPorIdIncluyeClienteCompletoYDetalles() {

        Cliente completo = new Cliente();
        completo.setIdCliente(1);
        completo.setNombre("Juan Perez");

        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(2);

        when(pedidoDAO.obtenerPorId(1)).thenReturn(pedido);
        when(clienteDAO.buscarPorId(1)).thenReturn(completo);
        when(detallePedidoDAO.listarPorPedido(1))
                .thenReturn(List.of(detalle));

        Pedido resultado = pedidoService.buscarPorId(1);

        assertEquals("Juan Perez", resultado.getCliente().getNombre());
        assertEquals(1, resultado.getDetalles().size());
    }

    @Test
    void buscarPorIdInexistenteRetornaNull() {

        when(pedidoDAO.obtenerPorId(99)).thenReturn(null);

        assertNull(pedidoService.buscarPorId(99));
    }
}
