
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.config.ConexionBD;
import com.casadonagloria.restaurante.model.CostoEnvio;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CostoEnvioDAOImpl implements CostoEnvioDAO {

    // LISTAR TODOS LOS COSTOS DE ENVIO
    @Override
    public List<CostoEnvio> listar() {

        List<CostoEnvio> lista = new ArrayList<>();

        String sql = "SELECT * FROM costo_envio";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCostoEnvio(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los costos de envio", e);
        }

        return lista;
    }

    // BUSCAR COSTO DE ENVIO POR ID
    @Override
    public CostoEnvio buscarPorId(int idCostoEnvio) {

        String sql = "SELECT * FROM costo_envio "
                + "WHERE id_costo_envio = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCostoEnvio);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearCostoEnvio(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el costo de envio", e);
        }

        return null;
    }

    // GUARDAR COSTO DE ENVIO
    @Override
    public void guardar(CostoEnvio costoEnvio) {

        String sql = "INSERT INTO costo_envio "
                + "(distancia_destino, costo, tiempo_estimado) "
                + "VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1, costoEnvio.getDistanciaDestino());

            ps.setBigDecimal(
                    2, costoEnvio.getCosto());

            if (costoEnvio.getTiempoEstimado() != null) {
                ps.setInt(
                        3, costoEnvio.getTiempoEstimado());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar el costo de envio", e);
        }
    }

    // ACTUALIZAR COSTO DE ENVIO
    @Override
    public void actualizar(CostoEnvio costoEnvio) {

        String sql = "UPDATE costo_envio "
                + "SET distancia_destino = ?, "
                + "costo = ?, "
                + "tiempo_estimado = ? "
                + "WHERE id_costo_envio = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1, costoEnvio.getDistanciaDestino());

            ps.setBigDecimal(
                    2, costoEnvio.getCosto());

            if (costoEnvio.getTiempoEstimado() != null) {
                ps.setInt(
                        3, costoEnvio.getTiempoEstimado());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setInt(
                    4, costoEnvio.getIdCostoEnvio());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el costo de envio", e);
        }
    }

    // ELIMINAR COSTO DE ENVIO
    @Override
    public void eliminar(int idCostoEnvio) {

        String sql = "DELETE FROM costo_envio "
                + "WHERE id_costo_envio = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCostoEnvio);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el costo de envio", e);
        }
    }

    // CONVERTIR RESULTSET A OBJETO COSTOENVIO
    private CostoEnvio mapearCostoEnvio(ResultSet rs)
            throws SQLException {

        CostoEnvio costoEnvio = new CostoEnvio();

        costoEnvio.setIdCostoEnvio(
                rs.getInt("id_costo_envio"));

        costoEnvio.setDistanciaDestino(
                rs.getString("distancia_destino"));

        costoEnvio.setCosto(
                rs.getBigDecimal("costo"));

        int tiempo = rs.getInt("tiempo_estimado");

        if (rs.wasNull()) {
            costoEnvio.setTiempoEstimado(null);
        } else {
            costoEnvio.setTiempoEstimado(tiempo);
        }

        return costoEnvio;
    }
}
