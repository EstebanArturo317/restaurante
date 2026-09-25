package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.config.ConexionBD;
import com.casadonagloria.restaurante.model.Cliente;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public Cliente crear(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, telefono, direccion) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getDireccion());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) cliente.setIdCliente(rs.getInt(1));
            }
            return cliente;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        String sql = "SELECT id_cliente, nombre, telefono, direccion FROM cliente";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) clientes.add(mapearCliente(rs));
            return clientes;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes: " + e.getMessage(), e);
        }
    }

    @Override
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT id_cliente, nombre, telefono, direccion FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearCliente(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre = ?, telefono = ?, direccion = ? WHERE id_cliente = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getDireccion());
            ps.setInt(4, cliente.getIdCliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cliente: " + e.getMessage(), e);
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(rs.getInt("id_cliente"), rs.getString("nombre"),
                rs.getString("telefono"), rs.getString("direccion"));
    }
}
