CREATE DATABASE IF NOT EXISTS restaurante_db;
USE restaurante_db;



CREATE TABLE costo_envio (
                             id_costo_envio INT AUTO_INCREMENT PRIMARY KEY,
                             distancia_destino VARCHAR(100),
                             costo DECIMAL(10,2) NOT NULL,
                             tiempo_estimado INT
);



CREATE TABLE cliente (
                         id_cliente INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         telefono VARCHAR(20) NOT NULL UNIQUE,
                         direccion VARCHAR(200),
                         barrio VARCHAR(100),
                         descripcion_dir VARCHAR(200)
);




CREATE TABLE producto (
                          id_producto INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          descripcion VARCHAR(200),
                          precio DECIMAL(10,2) NOT NULL,
                          disponible BOOLEAN DEFAULT TRUE
);



CREATE TABLE pedido (
                        id_pedido INT AUTO_INCREMENT PRIMARY KEY,
                        descripcion VARCHAR(200),

                        fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,

                        estado ENUM(
                            'PENDIENTE',
                            'EN_PREPARACION',
                            'EN_CAMINO',
                            'ENTREGADO',
                            'CANCELADO'
                            ) DEFAULT 'PENDIENTE',

                        costo DECIMAL(10,2) NOT NULL,

                        id_cliente INT NOT NULL,
                        id_costo_envio INT,

                        FOREIGN KEY (id_cliente)
                            REFERENCES cliente(id_cliente),

                        FOREIGN KEY (id_costo_envio)
                            REFERENCES costo_envio(id_costo_envio)
);



CREATE TABLE detalle_pedido (
                                id_detalle INT AUTO_INCREMENT PRIMARY KEY,

                                id_pedido INT NOT NULL,
                                id_producto INT NOT NULL,

                                cantidad INT NOT NULL,
                                precio_unitario DECIMAL(10,2) NOT NULL,

                                FOREIGN KEY (id_pedido)
                                    REFERENCES pedido(id_pedido),

                                FOREIGN KEY (id_producto)
                                    REFERENCES producto(id_producto)
);



INSERT INTO cliente
(nombre, telefono, direccion, barrio, descripcion_dir)
VALUES
    (
        'Juan Pérez',
        '3011234567',
        'Calle 10 #20-30',
        'Laureles',
        'apto 201'
    ),
    (
        'Maria Gómez',
        '3007654321',
        NULL,
        NULL,
        NULL
    ),
    (
        'Pablito Montoya',
        '3147454311',
        NULL,
        NULL,
        NULL
    ),
    (
        'Camila Espinoza',
        '3158665321',
        'Calle 10 #20-30',
        'El Picacho',
        NULL
    );




INSERT INTO costo_envio
(distancia_destino, costo, tiempo_estimado)
VALUES
    ('2km', 5500, 5),
    ('5km', 7000, 15),
    ('7km', 8500, 20),
    ('10km', 10000, 30);



INSERT INTO producto
(nombre, descripcion, precio, disponible)
VALUES
    (
        'Hamburguesa especial triple carne',
        'Hamburguesa con tres carnes',
        35000,
        TRUE
    ),
    (
        'Picada familiar',
        'Picada para cuatro personas',
        60000,
        TRUE
    ),
    (
        'Chuzo de pollo',
        'Chuzo de pollo acompañado',
        25000,
        TRUE
    ),
    (
        'Sazon austriaco',
        'Plato especial de la casa',
        40000,
        TRUE
    ),
    (
        'Gaseosa',
        'Bebida gaseosa personal',
        5000,
        TRUE
    );




INSERT INTO pedido
(descripcion, costo, id_cliente, id_costo_envio)
VALUES
    (
        'Hamburguesa especial triple carne',
        35000,
        1,
        1
    ),
    (
        'Picada familiar',
        60000,
        2,
        2
    ),
    (
        'Chuzo de pollo',
        25000,
        3,
        3
    ),
    (
        'Sazon austriaco',
        40000,
        4,
        4
    );



INSERT INTO detalle_pedido
(id_pedido, id_producto, cantidad, precio_unitario)
VALUES
    (1, 1, 1, 35000),
    (2, 2, 1, 60000),
    (3, 3, 1, 25000),
    (4, 4, 1, 40000);



SELECT * FROM cliente;




SELECT * FROM producto;




SELECT * FROM pedido;



SELECT
    p.id_pedido,
    c.nombre AS cliente,
    pr.nombre AS producto,
    dp.cantidad,
    dp.precio_unitario,
    dp.cantidad * dp.precio_unitario AS subtotal
FROM pedido p
         INNER JOIN cliente c
                    ON p.id_cliente = c.id_cliente
         INNER JOIN detalle_pedido dp
                    ON p.id_pedido = dp.id_pedido
         INNER JOIN producto pr
                    ON dp.id_producto = pr.id_producto;


SELECT
    p.id_pedido,
    c.nombre AS cliente,
    p.descripcion,
    p.estado,
    p.fecha_pedido,
    p.costo AS subtotal,
    COALESCE(ce.costo, 0) AS costo_envio,
    p.costo + COALESCE(ce.costo, 0) AS total
FROM pedido p
         INNER JOIN cliente c
                    ON p.id_cliente = c.id_cliente
         LEFT JOIN costo_envio ce
                   ON p.id_costo_envio = ce.id_costo_envio;
