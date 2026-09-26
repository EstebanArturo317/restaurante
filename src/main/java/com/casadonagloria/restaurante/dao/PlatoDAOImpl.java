package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.config.ConexionBD;
import com.casadonagloria.restaurante.model.Plato;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlatoDAOImpl implements PlatoDAO {

    @Override
    public Plato crear(Plato plato) {
        String sql = "INSERT INTO plato (nombre, descripcion, precio, esta_activo) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, plato.getNombre());
            ps.setString(2, plato.getDescripcion());
            ps.setDouble(3, plato.getPrecio());
            ps.setBoolean(4, plato.isEstaActivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) plato.setIdPlato(rs.getInt(1));
            }
            return plato;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear plato: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Plato> listarTodos() {
        String sql = "SELECT id_plato, nombre, descripcion, precio, esta_activo FROM plato";
        List<Plato> platos = new ArrayList<>();
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) platos.add(mapearPlato(rs));
            return platos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar platos: " + e.getMessage(), e);
        }
    }

    @Override
    public Plato obtenerPorId(int id) {
        String sql = "SELECT id_plato, nombre, descripcion, precio, esta_activo FROM plato WHERE id_plato = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearPlato(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener plato: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizar(Plato plato) {
        String sql = "UPDATE plato SET nombre = ?, descripcion = ?, precio = ?, esta_activo = ? WHERE id_plato = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, plato.getNombre());
            ps.setString(2, plato.getDescripcion());
            ps.setDouble(3, plato.getPrecio());
            ps.setBoolean(4, plato.isEstaActivo());
            ps.setInt(5, plato.getIdPlato());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar plato: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM plato WHERE id_plato = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar plato: " + e.getMessage(), e);
        }
    }

    private Plato mapearPlato(ResultSet rs) throws SQLException {
        return new Plato(
                rs.getInt("id_plato"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                rs.getBoolean("esta_activo")
        );
    }
}
