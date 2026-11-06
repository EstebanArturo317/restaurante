package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.PlatoDAO;
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

@ExtendWith(MockitoExtension.class)
class PlatoServiceTest {

    @Mock
    private PlatoDAO platoDAO;

    @InjectMocks
    private PlatoService platoService;

    private List<Plato> platos;
    private Plato platoPrueba;

    @BeforeEach
    void setUp() {
        platoPrueba = new Plato();
        platoPrueba.setIdPlato(1);
        platoPrueba.setNombre("Margarita Personal");
        platoPrueba.setDescripcion("Queso mozzarella, tomate y albahaca");
        platoPrueba.setPrecio(14000.0);
        platoPrueba.setEstaActivo(true);

        Plato plato2 = new Plato();
        plato2.setIdPlato(2);
        plato2.setNombre("Salchipapa con queso");
        plato2.setDescripcion("Papas, salchicha y queso");
        plato2.setPrecio(15000.0);
        plato2.setEstaActivo(true);

        platos = List.of(platoPrueba, plato2);
    }

    @Test
    void listarPlatos() {
        when(platoDAO.listarTodos()).thenReturn(platos);

        List<Plato> resultado = platoService.listarPlatos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Margarita Personal", resultado.get(0).getNombre());
        verify(platoDAO, times(1)).listarTodos();
    }

    @Test
    void buscarPorId() {
        when(platoDAO.obtenerPorId(1)).thenReturn(platoPrueba);

        Plato resultado = platoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdPlato());
        assertEquals(14000.0, resultado.getPrecio());
        verify(platoDAO, times(1)).obtenerPorId(1);
    }

    @Test
    void guardarPlato() {
        when(platoDAO.crear(platoPrueba)).thenReturn(platoPrueba);

        Plato resultado = platoService.guardarPlato(platoPrueba);

        assertNotNull(resultado);
        assertEquals("Margarita Personal", resultado.getNombre());
        verify(platoDAO, times(1)).crear(platoPrueba);
    }

    @Test
    void actualizarPlato() {
        when(platoDAO.actualizar(platoPrueba)).thenReturn(true);

        boolean resultado = platoService.actualizarPlato(platoPrueba);

        assertTrue(resultado);
        verify(platoDAO, times(1)).actualizar(platoPrueba);
    }

    @Test
    void eliminarPlato() {
        when(platoDAO.eliminar(1)).thenReturn(true);

        boolean resultado = platoService.eliminarPlato(1);

        assertTrue(resultado);
        verify(platoDAO, times(1)).eliminar(1);
    }
}