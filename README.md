# Casa Doña Gloria - API REST de pedidos

Backend en Spring Boot para gestionar clientes, platos y pedidos de un restaurante.

## Requisitos
- Java 17
- Maven (o el wrapper `mvnw`)
- MySQL 8 con una base de datos llamada `restaurante_db`

## Como ejecutarlo
1. Ejecutar `schema.sql` en MySQL Workbench para crear las tablas y los datos de prueba.
2. Ajustar usuario y contraseña en `src/main/resources/application.properties`.
3. Iniciar la aplicacion: `mvn spring-boot:run`
4. Abrir Swagger: http://localhost:8080/swagger-ui/index.html

## Arquitectura
- `controller`: endpoints REST documentados con Swagger.
- `service`: interfaces y su implementacion con las reglas de negocio.
- `dao` y `repository`: acceso a datos. Cliente y Pedido usan SQL (JDBC); Plato y DetallePedido usan JPA.
- `model`: entidades y relaciones (`@OneToMany`, `@ManyToOne`).
- `exception`: excepciones propias y manejador global de errores.

## Reglas de negocio
- Todo pedido nuevo inicia en estado RECIBIDO con total en cero.
- El estado de un pedido solo avanza en orden: RECIBIDO, EN_PREPARACION, LISTO, ENTREGADO (`PATCH /api/pedidos/{id}/estado`).
- El subtotal de cada detalle y el total del pedido se calculan en el servidor.
- No se pueden agregar platos inactivos.

## Pruebas
`mvn test` ejecuta las pruebas unitarias de los services (JUnit y Mockito).

## Flujo de ramas
`feature/*` -> Pull Request a `develop` -> `release/*` -> `master`.
