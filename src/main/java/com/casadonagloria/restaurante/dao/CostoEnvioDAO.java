
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.model.CostoEnvio;
import java.util.List;

public interface CostoEnvioDAO {

    List<CostoEnvio> listar();

    CostoEnvio buscarPorId(int idCostoEnvio);

    void guardar(CostoEnvio costoEnvio);

    void actualizar(CostoEnvio costoEnvio);

    void eliminar(int idCostoEnvio);
}
