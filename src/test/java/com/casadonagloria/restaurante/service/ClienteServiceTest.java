package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.ClienteDAO;
import com.casadonagloria.restaurante.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteDAO clienteDAO;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private List<Cliente> clientes;
    private Cliente clientePrueba;

    @BeforeEach
    void setUp() {
        clientePrueba = new Cliente();
        clientePrueba.setIdCliente(1);
        clientePrueba.setNombre("Juan Pérez");
        clientePrueba.setTelefono("3011234567");
        clientePrueba.setDireccion("Calle 10 #20-30");
        clientePrueba.setBarrio("Laureles");
        clientePrueba.setDescripcionDir("Apto 201");

        Cliente cliente2 = new Cliente();
        cliente2.setIdCliente(2);
        cliente2.setNombre("Maria Gómez");
        cliente2.setTelefono("3007654321");
        cliente2.setDireccion("Carrera 45 #12-08");
        cliente2.setBarrio("El Poblado");

        clientes = List.of(clientePrueba, cliente2);
    }

    @Test
    void listarClientes() {
        when(clienteDAO.listarClientes()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.listarClientes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
        verify(clienteDAO, times(1)).listarClientes();
    }

    @Test
    void buscarPorId() {
        when(clienteDAO.buscarPorId(1)).thenReturn(clientePrueba);

        Cliente resultado = clienteService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdCliente());
        assertEquals("3011234567", resultado.getTelefono());
        verify(clienteDAO, times(1)).buscarPorId(1);
    }

    @Test
    void guardarCliente() {
        when(clienteDAO.guardarCliente(clientePrueba)).thenReturn(clientePrueba);

        Cliente resultado = clienteService.guardarCliente(clientePrueba);

        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombre());
        verify(clienteDAO, times(1)).guardarCliente(clientePrueba);
    }

    @Test
    void actualizarCliente() {
        when(clienteDAO.actualizarCliente(1, clientePrueba)).thenReturn(clientePrueba);

        Cliente resultado = clienteService.actualizarCliente(1, clientePrueba);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdCliente());
        verify(clienteDAO, times(1)).actualizarCliente(1, clientePrueba);
    }

    @Test
    void eliminarCliente() {
        when(clienteDAO.eliminarCliente(1)).thenReturn(true);

        boolean resultado = clienteService.eliminarCliente(1);

        assertTrue(resultado);
        verify(clienteDAO, times(1)).eliminarCliente(1);
    }
}