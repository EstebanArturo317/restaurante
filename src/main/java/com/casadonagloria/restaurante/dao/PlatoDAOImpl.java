
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.model.Plato;
import com.casadonagloria.restaurante.repository.PlatoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PlatoDAOImpl implements PlatoDAO {

    private final PlatoRepository platoRepository;

    // Inyeccion de dependencias por constructor
    public PlatoDAOImpl(PlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }

    // Registrar un nuevo plato
    @Override
    @Transactional
    public Plato crear(Plato plato) {
        return platoRepository.save(plato);
    }

    // Listar todos los platos
    @Override
    @Transactional(readOnly = true)
    public List<Plato> listarTodos() {
        return platoRepository.findAll();
    }

    // Buscar un plato por su ID
    @Override
    @Transactional(readOnly = true)
    public Plato obtenerPorId(int id) {
        return platoRepository.findById(id).orElse(null);
    }

    // Actualizar un plato existente
    @Override
    @Transactional
    public boolean actualizar(Plato plato) {

        if (!platoRepository.existsById(plato.getIdPlato())) {
            return false;
        }

        platoRepository.save(plato);
        return true;
    }

    // Eliminar un plato por su ID
    @Override
    @Transactional
    public boolean eliminar(int id) {

        if (!platoRepository.existsById(id)) {
            return false;
        }

        platoRepository.deleteById(id);
        return true;
    }
}
