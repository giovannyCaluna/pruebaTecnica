# Prueba Técnica (Banco demo): 

## Descripción del Proyecto

El siguiente proyecto simula la implementacion simple de un banco. El proyecto fue desarrollado para cumplir con los requisitos de una arquitectura de microservicios.
La solución se centra en aplicar buenas prácticas de diseño, manejar entidades con JPA / Entity Framework Core, implementar manejo de excepciones y realizar pruebas unitarias.
La aplicación está desplegada en Docker para facilitar su implementación.


### Herramientas y Tecnologías Utilizadas

- **Java 17 Spring Boot**
- **IDE Intellij idea**
- **Base de Datos Relacional**: PostgreSQL.
- **Postman**: Para la validación de APIs.

### Generación de API Rest

- **Verbos HTTP**: GET, POST, PUT, DELETE.
- **Entidades**:
  - **Cliente**: Extiende Persona, incluye información adicional.
  - **Cuenta**: Número de cuenta, tipo, saldo inicial.
  - **Movimiento**: Fecha, tipo, valor, saldo.

### Funcionalidades del API

- **CRUD**: Crear, editar, actualizar y eliminar registros para Cliente, Cuenta y Movimiento.
- **Registro de Movimientos**: Actualización de saldo y registro de transacciones.
- **Manejo de Errores**: Alertas cuando no haya saldo disponible.
- **Reportes**: Generación de reportes de estado de cuenta con rango de fechas y cliente.

### Instrucciones de Despliegue

1. **Descargar el Repositorio**: Clonar el repositorio desde Git.
2. **Ejecutar Docker Compose**: En el directorio principal del proyecto, ejecutar el siguiente comando:
   ```sh
   sudo docker-compose up --build
   ```

### Entregables

- **Repositorio Git**: Enlace al repositorio público con la solución.
- **Archivo Postman**: JSON con las pruebas realizadas.
