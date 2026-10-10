package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.PlatoDAO;
import com.casadonagloria.restaurante.model.Plato;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatoService {

    private final PlatoDAO platoDAO;

    public PlatoService(PlatoDAO platoDAO) {
        this.platoDAO = platoDAO;
    }

    public List<Plato> listarPlatos() {
        return platoDAO.listarTodos();
    }

    public Plato buscarPorId(int id) {
        return platoDAO.obtenerPorId(id);
    }

    public Plato guardarPlato(Plato plato) {
        return platoDAO.crear(plato);
    }

    public boolean actualizarPlato(Plato plato) {
        return platoDAO.actualizar(plato);
    }

    public boolean eliminarPlato(int id) {
        return platoDAO.eliminar(id);
    }
}