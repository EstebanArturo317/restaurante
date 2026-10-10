CREATE DATABASE IF NOT EXISTS restaurante_db;
USE restaurante_db;

-- 1. Tabla cliente
CREATE TABLE IF NOT EXISTS cliente (
                                       id_cliente INT AUTO_INCREMENT PRIMARY KEY,
                                       nombre VARCHAR(100) NOT NULL,
                                       telefono VARCHAR(20) NOT NULL UNIQUE,
                                       direccion VARCHAR(200),
                                       barrio VARCHAR(100),
                                       descripcion_dir VARCHAR(200)
);

-- 2. Tabla plato
CREATE TABLE IF NOT EXISTS plato (
                                     id_plato INT AUTO_INCREMENT PRIMARY KEY,
                                     nombre VARCHAR(100) NOT NULL,
                                     descripcion VARCHAR(300) NULL,
                                     precio DOUBLE NOT NULL,
                                     esta_activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- 3. Tabla pedido
CREATE TABLE IF NOT EXISTS pedido (
                                      id_pedido INT AUTO_INCREMENT PRIMARY KEY,
                                      id_cliente INT NOT NULL,
                                      fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      estado ENUM('RECIBIDO', 'EN_PREPARACION', 'LISTO', 'ENTREGADO') DEFAULT 'RECIBIDO',
                                      valor_total DOUBLE NOT NULL DEFAULT 0.0,
                                      FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

-- 4. Tabla detalle_pedido
CREATE TABLE IF NOT EXISTS detalle_pedido (
                                              id_detalle INT AUTO_INCREMENT PRIMARY KEY,
                                              id_pedido INT NOT NULL,
                                              id_plato INT NOT NULL,
                                              cantidad INT NOT NULL,
                                              subtotal DOUBLE NOT NULL,
                                              FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
                                              FOREIGN KEY (id_plato) REFERENCES plato(id_plato)
);

-- Registros de prueba iniciales
INSERT INTO cliente (nombre, telefono, direccion, barrio, descripcion_dir)
VALUES
    ('Juan Pérez', '3011234567', 'Calle 10 #20-30', 'Laureles', 'Apto 201'),
    ('Maria Gómez', '3007654321', 'Carrera 45 #12-08', 'El Poblado', NULL)
ON DUPLICATE KEY UPDATE telefono = telefono;

INSERT INTO plato (nombre, descripcion, precio, esta_activo)
VALUES
    ('Margarita Personal', 'Queso mozzarella, tomate y pasta de albahaca', 14000, TRUE),
    ('Salchipapa con queso', '500 gramos de papas, salchicha, queso mozzarella y salsa BBQ', 15000, TRUE),
    ('Arepa con pollo desmechado', 'Arepa de maiz, mantequilla, pollo desmechado y queso mozzarella', 18000, TRUE);

INSERT INTO pedido (id_cliente, estado, valor_total)
VALUES
    (1, 'RECIBIDO', 29000);

INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, subtotal)
VALUES
    (1, 1, 1, 14000), -- 1x Margarita Personal
    (1, 2, 1, 15000); -- 1x Salchipapa