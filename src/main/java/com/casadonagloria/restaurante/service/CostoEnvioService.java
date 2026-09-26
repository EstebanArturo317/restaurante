
package com.casadonagloria.restaurante.service;

import com.casadonagloria.restaurante.dao.CostoEnvioDAO;
import com.casadonagloria.restaurante.model.CostoEnvio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostoEnvioService {

    private final CostoEnvioDAO costoEnvioDAO;

    public CostoEnvioService(CostoEnvioDAO costoEnvioDAO) {
        this.costoEnvioDAO = costoEnvioDAO;
    }

    // Listar todos los costos de envío
    public List<CostoEnvio> listar() {
        return costoEnvioDAO.listar();
    }

    // Buscar un costo de envío por ID
    public CostoEnvio buscarPorId(int id) {
        return costoEnvioDAO.buscarPorId(id);
    }

    // Guardar un nuevo costo de envío
    public void guardar(CostoEnvio costoEnvio) {
        costoEnvioDAO.guardar(costoEnvio);
    }

    // Actualizar un costo de envío
    public void actualizar(CostoEnvio costoEnvio) {
        costoEnvioDAO.actualizar(costoEnvio);
    }

    // Eliminar un costo de envío
    public void eliminar(int id) {
        costoEnvioDAO.eliminar(id);
    }
}
