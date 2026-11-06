package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.PlatoDAO;
import com.casadonagloria.restaurante.model.Plato;
import org.springframework.stereotype.Service;

import java.util.List;
import com.casadonagloria.restaurante.exception.RecursoNoEncontradoException;
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

        Plato plato = platoDAO.obtenerPorId(id);

        if (plato == null) {
            throw new RecursoNoEncontradoException(
                    "No se encontro el plato con ID: " + id
            );
        }

        return plato;
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