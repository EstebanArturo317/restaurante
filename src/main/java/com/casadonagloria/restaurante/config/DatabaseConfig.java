
package com.casadonagloria.restaurante.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String URL =
            "jdbc:mysql://localhost:3306/restaurante_db";

    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // Obtener conexión a MySQL
    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    // Probar conexión
    public static void main(String[] args) {

        try (Connection conexion = getConnection()) {

            if (conexion.isValid(5)) {

                System.out.println(
                        "Conexión exitosa a MySQL"
                );

                System.out.println(
                        "Base de datos: " +
                                conexion.getCatalog()
                );

            } else {

                System.out.println(
                        "No se pudo validar la conexión"
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al conectar con MySQL: "
                            + e.getMessage()
            );
        }
    }
}
