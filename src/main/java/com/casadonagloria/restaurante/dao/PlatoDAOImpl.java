
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.config.DatabaseConfig;
import com.casadonagloria.restaurante.model.Plato;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlatoDAOImpl implements PlatoDAO {

    @Override
    public Plato crear(Plato plato) {

        String sql = """
                INSERT INTO restaurante_db.plato
                (nombre, descripcion, precio, esta_activo)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection con = DatabaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setString(1, plato.getNombre());
            ps.setString(2, plato.getDescripcion());
            ps.setDouble(3, plato.getPrecio());
            ps.setBoolean(4, plato.isEstaActivo());

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException(
                        "No se pudo registrar el plato"
                );
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    plato.setIdPlato(rs.getInt(1));
                }
            }

            return plato;

        } catch (SQLException e) {
            System.err.println(
                    "ERROR SQL AL CREAR PLATO: " + e.getMessage()
            );
            e.printStackTrace();

            throw new RuntimeException(
                    "Error al crear el plato", e
            );
        }
    }

    @Override
    public List<Plato> listarTodos() {

        List<Plato> platos = new ArrayList<>();

        String sql = """
                SELECT id_plato, nombre, descripcion,
                       precio, esta_activo
                FROM restaurante_db.plato
                """;

        try (
                Connection con = DatabaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                platos.add(mapearPlato(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los platos", e
            );
        }

        return platos;
    }

    @Override
    public Plato obtenerPorId(int id) {

        String sql = """
                SELECT id_plato, nombre, descripcion,
                       precio, esta_activo
                FROM restaurante_db.plato
                WHERE id_plato = ?
                """;

        try (
                Connection con = DatabaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPlato(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el plato", e
            );
        }

        return null;
    }

    @Override
    public boolean actualizar(Plato plato) {

        String sql = """
                UPDATE restaurante_db.plato
                SET nombre = ?,
                    descripcion = ?,
                    precio = ?,
                    esta_activo = ?
                WHERE id_plato = ?
                """;

        try (
                Connection con = DatabaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, plato.getNombre());
            ps.setString(2, plato.getDescripcion());
            ps.setDouble(3, plato.getPrecio());
            ps.setBoolean(4, plato.isEstaActivo());
            ps.setInt(5, plato.getIdPlato());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el plato", e
            );
        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = """
                DELETE FROM restaurante_db.plato
                WHERE id_plato = ?
                """;

        try (
                Connection con = DatabaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el plato", e
            );
        }
    }

    private Plato mapearPlato(ResultSet rs)
            throws SQLException {

        Plato plato = new Plato();

        plato.setIdPlato(rs.getInt("id_plato"));
        plato.setNombre(rs.getString("nombre"));
        plato.setDescripcion(rs.getString("descripcion"));
        plato.setPrecio(rs.getDouble("precio"));
        plato.setEstaActivo(rs.getBoolean("esta_activo"));

        return plato;
    }
}
