
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.config.DatabaseConfig;
import com.casadonagloria.restaurante.model.Pedido;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PedidoDAOImpl implements PedidoDAO {

    private Pedido mapearPedido(ResultSet rs)
            throws SQLException {

        Pedido pedido = new Pedido();

        pedido.setIdPedido(rs.getInt("id_pedido"));
        pedido.setIdCliente(rs.getInt("id_cliente"));

        Timestamp fecha = rs.getTimestamp("fecha_hora");

        if (fecha != null) {
            pedido.setFechaHora(fecha.toLocalDateTime());
        }

        pedido.setEstado(rs.getString("estado"));
        pedido.setValorTotal(rs.getBigDecimal("valor_total"));

        return pedido;
    }

    @Override
    public Pedido crear(Pedido pedido) {

        String sql = """
                INSERT INTO restaurante_db.pedido
                (id_cliente, estado, valor_total)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps = conexion.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setInt(1, pedido.getIdCliente());

            String estado = pedido.getEstado();

            ps.setString(2,
                    estado != null ? estado : "RECIBIDO");

            BigDecimal total = pedido.getValorTotal();

            ps.setBigDecimal(3,
                    total != null ? total : BigDecimal.ZERO);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException(
                        "No se pudo registrar el pedido");
            }

            int idGenerado;

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (!rs.next()) {
                    throw new SQLException(
                            "No se obtuvo el ID del pedido");
                }

                idGenerado = rs.getInt(1);
            }

            String consultaSql = """
                    SELECT *
                    FROM restaurante_db.pedido
                    WHERE id_pedido = ?
                    """;

            try (
                    PreparedStatement consulta =
                            conexion.prepareStatement(consultaSql)
            ) {

                consulta.setInt(1, idGenerado);

                try (ResultSet rs = consulta.executeQuery()) {

                    if (rs.next()) {
                        return mapearPedido(rs);
                    }
                }
            }

            throw new SQLException(
                    "No se pudo recuperar el pedido creado");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar el pedido", e);
        }
    }

    @Override
    public List<Pedido> listarTodos() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
                SELECT *
                FROM restaurante_db.pedido
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                pedidos.add(mapearPedido(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los pedidos", e);
        }

        return pedidos;
    }

    @Override
    public Pedido obtenerPorId(int id) {

        String sql = """
                SELECT *
                FROM restaurante_db.pedido
                WHERE id_pedido = ?
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearPedido(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el pedido", e);
        }

        return null;
    }

    @Override
    public List<Pedido> listarPorCliente(int idCliente) {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
                SELECT *
                FROM restaurante_db.pedido
                WHERE id_cliente = ?
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    pedidos.add(mapearPedido(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los pedidos del cliente", e);
        }

        return pedidos;
    }

    @Override
    public boolean actualizar(Pedido pedido) {

        String sql = """
                UPDATE restaurante_db.pedido
                SET id_cliente = ?,
                    estado = ?,
                    valor_total = ?
                WHERE id_pedido = ?
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, pedido.getIdCliente());
            ps.setString(2, pedido.getEstado());

            BigDecimal total = pedido.getValorTotal();

            ps.setBigDecimal(3,
                    total != null ? total : BigDecimal.ZERO);

            ps.setInt(4, pedido.getIdPedido());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el pedido", e);
        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = """
                DELETE FROM restaurante_db.pedido
                WHERE id_pedido = ?
                """;

        try (
                Connection conexion = DatabaseConfig.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el pedido", e);
        }
    }
}
