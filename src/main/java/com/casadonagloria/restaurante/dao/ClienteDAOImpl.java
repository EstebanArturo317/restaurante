
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.config.DatabaseConfig;
import com.casadonagloria.restaurante.model.Cliente;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteDAOImpl implements ClienteDAO {


    private Cliente mapearCliente(ResultSet rs)
            throws SQLException {

        Cliente cliente = new Cliente();

        cliente.setIdCliente(rs.getInt("id_cliente"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setBarrio(rs.getString("barrio"));
        cliente.setDescripcionDir(
                rs.getString("descripcion_dir")
        );

        return cliente;
    }

    @Override
    public List<Cliente> listarClientes() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = """
                SELECT id_cliente, nombre, telefono,
                       direccion, barrio, descripcion_dir
                FROM restaurante_db.cliente
                """;

        try (
                Connection conexion =
                        DatabaseConfig.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(sql);

                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los clientes", e
            );
        }

        return clientes;
    }


    @Override
    public Cliente buscarPorId(int id) {

        String sql = """
                SELECT id_cliente, nombre, telefono,
                       direccion, barrio, descripcion_dir
                FROM restaurante_db.cliente
                WHERE id_cliente = ?
                """;

        try (
                Connection conexion =
                        DatabaseConfig.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el cliente", e
            );
        }

        return null;
    }


    @Override
    public Cliente guardarCliente(Cliente cliente) {

        String sql = """
                INSERT INTO restaurante_db.cliente
                (nombre, telefono, direccion,
                 barrio, descripcion_dir)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection conexion =
                        DatabaseConfig.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getBarrio());
            ps.setString(5, cliente.getDescripcionDir());

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException(
                        "No se pudo registrar el cliente"
                );
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    cliente.setIdCliente(rs.getInt(1));
                }
            }

            return cliente;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar el cliente", e
            );
        }
    }

    @Override
    public Cliente actualizarCliente(
            int id, Cliente cliente) {

        String sql = """
                UPDATE restaurante_db.cliente
                SET nombre = ?,
                    telefono = ?,
                    direccion = ?,
                    barrio = ?,
                    descripcion_dir = ?
                WHERE id_cliente = ?
                """;

        try (
                Connection conexion =
                        DatabaseConfig.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(sql)
        ) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getBarrio());
            ps.setString(5, cliente.getDescripcionDir());
            ps.setInt(6, id);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                return null;
            }

            cliente.setIdCliente(id);

            return cliente;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el cliente", e
            );
        }
    }

    // 5. ELIMINAR UN CLIENTE
    @Override
    public boolean eliminarCliente(int id) {

        String sql = """
                DELETE FROM restaurante_db.cliente
                WHERE id_cliente = ?
                """;

        try (
                Connection conexion =
                        DatabaseConfig.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el cliente", e
            );
        }
    }
}
