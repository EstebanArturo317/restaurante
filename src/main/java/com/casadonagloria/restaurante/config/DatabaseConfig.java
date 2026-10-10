
package com.casadonagloria.restaurante.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "No se encontró application.properties"
                );
            }

            properties.load(input);

        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection()
            throws SQLException {

        String url = properties.getProperty(
                "spring.datasource.url");

        String usuario = properties.getProperty(
                "spring.datasource.username");

        String password = properties.getProperty(
                "spring.datasource.password", "");

        if (url == null || usuario == null) {
            throw new SQLException(
                    "Falta configurar la conexión a MySQL"
            );
        }

        return DriverManager.getConnection(
                url,
                usuario,
                password
        );
    }

    public static void main(String[] args) {

        try (Connection conexion = getConnection()) {

            if (conexion.isValid(5)) {
                System.out.println(
                        "Conexión exitosa a MySQL"
                );

                System.out.println(
                        "Base de datos: "
                                + conexion.getCatalog()
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
