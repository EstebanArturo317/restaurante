
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.config.DatabaseConfig;
import com.casadonagloria.restaurante.model.DetallePedido;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DetallePedidoDAOImpl implements DetallePedidoDAO {

    private DetallePedido mapearDetalle(ResultSet rs)
            throws SQLException {

        DetallePedido detalle = new DetallePedido();

        detalle.setIdDetalle(rs.getInt("id_detalle"));
        detalle.setIdPedido(rs.getInt("id_pedido"));
        detalle.setIdPlato(rs.getInt("id_plato"));
        detalle.setCantidad(rs.getInt("cantidad"));
        detalle.setSubtotal(rs.getInt("subtotal"));

        return detalle;
    }

    @Override
    public DetallePedido crear(DetallePedido detallePedido) {

        String sql = """
                INSERT INTO restaurante_db.detalle_pedido
                (id_pedido, id_plato, cantidad, subtotal)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps = conexion.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setInt(1, detallePedido.getIdPedido());
            ps.setInt(2, detallePedido.getIdPlato());
            ps.setInt(3, detallePedido.getCantidad());
            ps.setInt(4, detallePedido.getSubtotal());

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException(
                        "No se pudo registrar el detalle");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    detallePedido.setIdDetalle(rs.getInt(1));
                }
            }

            return detallePedido;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al crear el detalle del pedido", e);
        }
    }

    @Override
    public List<DetallePedido> listarTodos() {

        List<DetallePedido> detalles = new ArrayList<>();

        String sql = """
                SELECT id_detalle, id_pedido, id_plato,
                       cantidad, subtotal
                FROM restaurante_db.detalle_pedido
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                detalles.add(mapearDetalle(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los detalles", e);
        }

        return detalles;
    }

    @Override
    public DetallePedido obtenerPorId(int id) {

        String sql = """
                SELECT id_detalle, id_pedido, id_plato,
                       cantidad, subtotal
                FROM restaurante_db.detalle_pedido
                WHERE id_detalle = ?
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearDetalle(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el detalle", e);
        }

        return null;
    }

    @Override
    public List<DetallePedido> listarPorPedido(int idPedido) {

        List<DetallePedido> detalles = new ArrayList<>();

        String sql = """
                SELECT id_detalle, id_pedido, id_plato,
                       cantidad, subtotal
                FROM restaurante_db.detalle_pedido
                WHERE id_pedido = ?
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    detalles.add(mapearDetalle(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los detalles del pedido", e);
        }

        return detalles;
    }

    @Override
    public boolean actualizar(DetallePedido detallePedido) {

        String sql = """
                UPDATE restaurante_db.detalle_pedido
                SET id_pedido = ?,
                    id_plato = ?,
                    cantidad = ?,
                    subtotal = ?
                WHERE id_detalle = ?
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, detallePedido.getIdPedido());
            ps.setInt(2, detallePedido.getIdPlato());
            ps.setInt(3, detallePedido.getCantidad());
            ps.setInt(4, detallePedido.getSubtotal());
            ps.setInt(5, detallePedido.getIdDetalle());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el detalle", e);
        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = """
                DELETE FROM restaurante_db.detalle_pedido
                WHERE id_detalle = ?
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el detalle", e);
        }
    }
}
