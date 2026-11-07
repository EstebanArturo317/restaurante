
package com.casadonagloria.restaurante.dao;

import com.casadonagloria.restaurante.model.Cliente;
import com.casadonagloria.restaurante.model.Pedido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class PedidoDAOImpl implements PedidoDAO {

    private static final String COLUMNAS =
            "id_pedido, id_cliente, fecha_hora, estado, valor_total";

    private final JdbcTemplate jdbc;

    public PedidoDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Pedido> mapper = (rs, numeroFila) -> {
        Pedido pedido = new Pedido();

        pedido.setIdPedido(rs.getInt("id_pedido"));

        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("id_cliente"));
        pedido.setCliente(cliente);

        Timestamp fecha = rs.getTimestamp("fecha_hora");

        if (fecha != null) {
            pedido.setFechaHora(fecha.toLocalDateTime());
        }

        pedido.setEstado(rs.getString("estado"));
        pedido.setValorTotal(rs.getBigDecimal("valor_total"));

        return pedido;
    };

    @Override
    public Pedido crear(Pedido pedido) {

        String sql = "INSERT INTO pedido " +
                "(id_cliente, estado, valor_total) VALUES (?, ?, ?)";

        KeyHolder llave = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setInt(1, pedido.getCliente().getIdCliente());
            ps.setString(2, pedido.getEstado());
            ps.setBigDecimal(3, pedido.getValorTotal());

            return ps;
        }, llave);

        Number id = llave.getKey();

        if (id == null) {
            throw new IllegalStateException(
                    "No se obtuvo el ID del pedido"
            );
        }

        return obtenerPorId(id.intValue());
    }

    @Override
    public List<Pedido> listarTodos() {
        return jdbc.query(
                "SELECT " + COLUMNAS + " FROM pedido",
                mapper
        );
    }

    @Override
    public Pedido obtenerPorId(int id) {
        return jdbc.query(
                "SELECT " + COLUMNAS +
                        " FROM pedido WHERE id_pedido = ?",
                mapper,
                id
        ).stream().findFirst().orElse(null);
    }

    @Override
    public List<Pedido> listarPorCliente(int idCliente) {
        return jdbc.query(
                "SELECT " + COLUMNAS +
                        " FROM pedido WHERE id_cliente = ?",
                mapper,
                idCliente
        );
    }

    @Override
    public boolean actualizar(Pedido pedido) {
        return jdbc.update(
                "UPDATE pedido SET id_cliente = ?, " +
                        "estado = ?, valor_total = ? " +
                        "WHERE id_pedido = ?",
                pedido.getCliente().getIdCliente(),
                pedido.getEstado(),
                pedido.getValorTotal(),
                pedido.getIdPedido()
        ) > 0;
    }

    @Override
    public boolean actualizarTotal(int idPedido, BigDecimal total) {
        return jdbc.update(
                "UPDATE pedido SET valor_total = ? " +
                        "WHERE id_pedido = ?",
                total,
                idPedido
        ) > 0;
    }

    @Override
    public boolean eliminar(int id) {
        return jdbc.update(
                "DELETE FROM pedido WHERE id_pedido = ?",
                id
        ) > 0;
    }
}
