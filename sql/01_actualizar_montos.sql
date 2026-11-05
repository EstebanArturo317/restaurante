USE restaurante_db;

-- se unifica el tipo monetario del precio de los platos
ALTER TABLE plato
    MODIFY COLUMN precio DECIMAL(12,2) NOT NULL;

-- se unifica el tipo monetario del total de los pedidos
ALTER TABLE pedido
    MODIFY COLUMN valor_total DECIMAL(12,2)
    NOT NULL DEFAULT 0.00;

-- se amplía la precisión del subtotal y agrega el precio aplicado
-- inicialmente permite NULL para completar los registros existentes
ALTER TABLE detalle_pedido
    MODIFY COLUMN subtotal DECIMAL(12,2) NOT NULL,
    ADD COLUMN precio_unitario DECIMAL(12,2) NULL
    AFTER cantidad;

-- se recupera el precio aplicado a partir del subtotal historico
-- el filtro utiliza la clave primaria de los detalles existente
UPDATE detalle_pedido
SET precio_unitario = ROUND(subtotal / cantidad, 2)
WHERE id_detalle > 0
  AND cantidad > 0
  AND precio_unitario IS NULL;

-- exige precio unitario para los detalles que se registren
ALTER TABLE detalle_pedido
    MODIFY COLUMN precio_unitario DECIMAL(12,2) NOT NULL;