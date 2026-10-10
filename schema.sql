
CREATE DATABASE IF NOT EXISTS restaurante_db;
USE restaurante_db;

CREATE TABLE IF NOT EXISTS cliente (
                                       id_cliente INT AUTO_INCREMENT PRIMARY KEY,
                                       nombre VARCHAR(100) NOT NULL,
                                       telefono VARCHAR(20) NOT NULL UNIQUE,
                                       direccion VARCHAR(200),
                                       barrio VARCHAR(100),
                                       descripcion_dir VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS plato (
                                     id_plato INT AUTO_INCREMENT PRIMARY KEY,
                                     nombre VARCHAR(100) NOT NULL,
                                     descripcion VARCHAR(300),
                                     precio DOUBLE NOT NULL,
                                     esta_activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS pedido (
                                      id_pedido INT AUTO_INCREMENT PRIMARY KEY,
                                      id_cliente INT NOT NULL,
                                      fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      estado ENUM(
                                          'RECIBIDO',
                                          'EN_PREPARACION',
                                          'LISTO',
                                          'ENTREGADO'
                                          ) DEFAULT 'RECIBIDO',
                                      valor_total INT NOT NULL DEFAULT 0,
                                      FOREIGN KEY (id_cliente)
                                          REFERENCES cliente(id_cliente)
);

CREATE TABLE IF NOT EXISTS detalle_pedido (
                                              id_detalle INT AUTO_INCREMENT PRIMARY KEY,
                                              id_pedido INT NOT NULL,
                                              id_plato INT NOT NULL,
                                              cantidad INT NOT NULL,
                                              subtotal DECIMAL(10,2) NOT NULL,
                                              FOREIGN KEY (id_pedido)
                                                  REFERENCES pedido(id_pedido),
                                              FOREIGN KEY (id_plato)
                                                  REFERENCES plato(id_plato)
);

INSERT INTO cliente
(nombre, telefono, direccion, barrio, descripcion_dir)
VALUES
    ('Juan Pérez', '3011234567',
     'Calle 10 #20-30', 'Laureles', 'Apto 201'),
    ('Maria Gómez', '3007654321',
     'Carrera 45 #12-08', 'El Poblado', NULL)
ON DUPLICATE KEY UPDATE
    telefono = VALUES(telefono);

INSERT INTO plato
(nombre, descripcion, precio, esta_activo)
SELECT
    'Margarita Personal',
    'Queso mozzarella, tomate y pasta de albahaca',
    14000,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM plato
    WHERE nombre = 'Margarita Personal'
);

INSERT INTO plato
(nombre, descripcion, precio, esta_activo)
SELECT
    'Salchipapa con queso',
    '500 gramos de papas, salchicha, queso mozzarella y salsa BBQ',
    15000,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM plato
    WHERE nombre = 'Salchipapa con queso'
);

INSERT INTO plato
(nombre, descripcion, precio, esta_activo)
SELECT
    'Arepa con pollo desmechado',
    'Arepa de maiz, mantequilla, pollo desmechado y queso mozzarella',
    18000,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM plato
    WHERE nombre = 'Arepa con pollo desmechado'
);
INSERT INTO cliente
(nombre, telefono, direccion, barrio, descripcion_dir)
VALUES
    ('Carlos Ramírez', '3104567890',
     'Calle 45 #30-20', 'Belén', 'Casa blanca'),

    ('Laura Martínez', '3115678901',
     'Carrera 70 #25-40', 'Laureles', 'Apto 302'),

    ('Andrés Gómez', '3126789012',
     'Calle 80 #50-15', 'Robledo', 'Casa esquinera'),

    ('Valentina Torres', '3137890123',
     'Carrera 43 #10-25', 'El Poblado', 'Torre 2'),

    ('Santiago López', '3148901234',
     'Calle 55 #65-30', 'Estadio', 'Primer piso')

ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre);

INSERT INTO plato
(nombre, descripcion, precio, esta_activo)
SELECT
    'Hamburguesa Especial',
    'Carne de res, queso, lechuga, tomate y salsa especial',
    22000, TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM plato
    WHERE nombre = 'Hamburguesa Especial'
);

INSERT INTO plato
(nombre, descripcion, precio, esta_activo)
SELECT
    'Pizza Hawaiana',
    'Jamón, piña y queso mozzarella',
    20000, TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM plato
    WHERE nombre = 'Pizza Hawaiana'
);

INSERT INTO plato
(nombre, descripcion, precio, esta_activo)
SELECT
    'Perro Caliente Especial',
    'Salchicha, queso, papas trituradas y salsas',
    16000, TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM plato
    WHERE nombre = 'Perro Caliente Especial'
);

INSERT INTO plato
(nombre, descripcion, precio, esta_activo)
SELECT
    'Salchipapa Mixta',
    'Papas fritas, salchicha, pollo y queso',
    25000, TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM plato
    WHERE nombre = 'Salchipapa Mixta'
);

INSERT INTO plato
(nombre, descripcion, precio, esta_activo)
SELECT
    'Arepa con Carne',
    'Arepa de maíz con carne desmechada y queso',
    19000, TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM plato
    WHERE nombre = 'Arepa con Carne'
);

INSERT INTO pedido (id_cliente, estado, valor_total)
SELECT c.id_cliente, 'RECIBIDO', 29000
FROM cliente c
WHERE c.telefono = '3011234567'
  AND NOT EXISTS (
    SELECT 1 FROM pedido p
    WHERE p.id_cliente = c.id_cliente
      AND p.estado = 'RECIBIDO'
      AND p.valor_total = 29000
);

INSERT INTO pedido (id_cliente, estado, valor_total)
SELECT c.id_cliente, 'EN_PREPARACION', 22000
FROM cliente c
WHERE c.telefono = '3104567890'
  AND NOT EXISTS (
    SELECT 1 FROM pedido p
    WHERE p.id_cliente = c.id_cliente
      AND p.estado = 'EN_PREPARACION'
      AND p.valor_total = 22000
);

INSERT INTO pedido (id_cliente, estado, valor_total)
SELECT c.id_cliente, 'LISTO', 40000
FROM cliente c
WHERE c.telefono = '3115678901'
  AND NOT EXISTS (
    SELECT 1 FROM pedido p
    WHERE p.id_cliente = c.id_cliente
      AND p.estado = 'LISTO'
      AND p.valor_total = 40000
);

INSERT INTO pedido (id_cliente, estado, valor_total)
SELECT c.id_cliente, 'ENTREGADO', 32000
FROM cliente c
WHERE c.telefono = '3126789012'
  AND NOT EXISTS (
    SELECT 1 FROM pedido p
    WHERE p.id_cliente = c.id_cliente
      AND p.estado = 'ENTREGADO'
      AND p.valor_total = 32000
);

INSERT INTO pedido (id_cliente, estado, valor_total)
SELECT c.id_cliente, 'RECIBIDO', 25000
FROM cliente c
WHERE c.telefono = '3137890123'
  AND NOT EXISTS (
    SELECT 1 FROM pedido p
    WHERE p.id_cliente = c.id_cliente
      AND p.estado = 'RECIBIDO'
      AND p.valor_total = 25000
);

INSERT INTO detalle_pedido
(id_pedido, id_plato, cantidad, subtotal)
SELECT p.id_pedido, pl.id_plato, 1, 14000
FROM pedido p
         JOIN cliente c ON c.id_cliente = p.id_cliente
         CROSS JOIN plato pl
WHERE c.telefono = '3011234567'
  AND p.estado = 'RECIBIDO'
  AND p.valor_total = 29000
  AND pl.nombre = 'Margarita Personal'
  AND NOT EXISTS (
    SELECT 1 FROM detalle_pedido d
    WHERE d.id_pedido = p.id_pedido
      AND d.id_plato = pl.id_plato
);

INSERT INTO detalle_pedido
(id_pedido, id_plato, cantidad, subtotal)
SELECT p.id_pedido, pl.id_plato, 1, 15000
FROM pedido p
         JOIN cliente c ON c.id_cliente = p.id_cliente
         CROSS JOIN plato pl
WHERE c.telefono = '3011234567'
  AND p.estado = 'RECIBIDO'
  AND p.valor_total = 29000
  AND pl.nombre = 'Salchipapa con queso'
  AND NOT EXISTS (
    SELECT 1 FROM detalle_pedido d
    WHERE d.id_pedido = p.id_pedido
      AND d.id_plato = pl.id_plato
);

INSERT INTO detalle_pedido
(id_pedido, id_plato, cantidad, subtotal)
SELECT p.id_pedido, pl.id_plato, 1, 22000
FROM pedido p
         JOIN cliente c ON c.id_cliente = p.id_cliente
         CROSS JOIN plato pl
WHERE c.telefono = '3104567890'
  AND p.estado = 'EN_PREPARACION'
  AND p.valor_total = 22000
  AND pl.nombre = 'Hamburguesa Especial'
  AND NOT EXISTS (
    SELECT 1 FROM detalle_pedido d
    WHERE d.id_pedido = p.id_pedido
      AND d.id_plato = pl.id_plato
);

INSERT INTO detalle_pedido
(id_pedido, id_plato, cantidad, subtotal)
SELECT p.id_pedido, pl.id_plato, 2, 40000
FROM pedido p
         JOIN cliente c ON c.id_cliente = p.id_cliente
         CROSS JOIN plato pl
WHERE c.telefono = '3115678901'
  AND p.estado = 'LISTO'
  AND p.valor_total = 40000
  AND pl.nombre = 'Pizza Hawaiana'
  AND NOT EXISTS (
    SELECT 1 FROM detalle_pedido d
    WHERE d.id_pedido = p.id_pedido
      AND d.id_plato = pl.id_plato
);

INSERT INTO detalle_pedido
(id_pedido, id_plato, cantidad, subtotal)
SELECT p.id_pedido, pl.id_plato, 2, 32000
FROM pedido p
         JOIN cliente c ON c.id_cliente = p.id_cliente
         CROSS JOIN plato pl
WHERE c.telefono = '3126789012'
  AND p.estado = 'ENTREGADO'
  AND p.valor_total = 32000
  AND pl.nombre = 'Perro Caliente Especial'
  AND NOT EXISTS (
    SELECT 1 FROM detalle_pedido d
    WHERE d.id_pedido = p.id_pedido
      AND d.id_plato = pl.id_plato
);

INSERT INTO detalle_pedido
(id_pedido, id_plato, cantidad, subtotal)
SELECT p.id_pedido, pl.id_plato, 1, 25000
FROM pedido p
         JOIN cliente c ON c.id_cliente = p.id_cliente
         CROSS JOIN plato pl
WHERE c.telefono = '3137890123'
  AND p.estado = 'RECIBIDO'
  AND p.valor_total = 25000
  AND pl.nombre = 'Salchipapa Mixta'
  AND NOT EXISTS (
    SELECT 1 FROM detalle_pedido d
    WHERE d.id_pedido = p.id_pedido
      AND d.id_plato = pl.id_plato
);

SELECT * FROM cliente;
SELECT * FROM plato;
SELECT * FROM pedido;
SELECT * FROM detalle_pedido;
SHOW TABLES;