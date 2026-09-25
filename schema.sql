CREATE DATABASE IF NOT EXISTS restaurante_db;
USE restaurante_db;

CREATE TABLE IF NOT EXISTS cliente (
                                       id_cliente INT AUTO_INCREMENT PRIMARY KEY,
                                       nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(200) NULL
    );

INSERT INTO cliente (nombre, telefono, direccion) VALUES
                                                      ('Juan Pérez', '3011234567', 'Calle 10 #20-30'),
                                                      ('Maria Gómez', '3007654321', NULL);

