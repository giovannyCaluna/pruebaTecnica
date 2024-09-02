# Prueba Técnica (Banco Demo) V2

## Descripción del Proyecto

El proyecto simula la implementación básica de un banco, utilizando una arquitectura de microservicios compuesta por dos artefactos: Cliente y Cuenta. El componente Cliente maneja las solicitudes externas y se comunica con el componente Cuenta a través de HTTP para acceder a recursos relacionados con cuentas bancarias. Además, algunos servicios requieren información de ambos componentes (por ejemplo, reportes), y en estos casos, los artefactos se comunican de manera asíncrona mediante RabbitMQ.

El proyecto aplica el patrón de diseño **Strategy** para definir las operaciones de débito y acreditación en una cuenta. Este patrón permite definir diferentes acciones para estas operaciones en el futuro sin afectar el funcionamiento actual del sistema.

La solución está diseñada siguiendo buenas prácticas, incluyendo los principios SOLID de diseño, el manejo de entidades con JPA, la implementación de manejo de excepciones, validaciones y estrategias de herencia en Spring, así como la realización de pruebas unitarias e integración.

Para esta versión, se ha añadido un servicio de transferencia que utiliza dos tipos de propagación de transacciones: `Propagation.REQUIRED` y `Propagation.REQUIRES_NEW`.

La aplicación está desplegada en Docker para facilitar su implementación.

### Herramientas y Tecnologías Utilizadas

- **Java 17 con Spring Boot**
- **IDE**: IntelliJ IDEA
- **Base de Datos Relacional**: PostgreSQL
- **Mensajería Asíncrona**: RabbitMQ
- **Pruebas de API**: Postman

### Generación de API REST

- **Verbos HTTP**: GET, POST, PUT, DELETE
- **Entidades**:
  - **Cliente**: Extiende la entidad Persona, incluyendo información adicional.
  - **Cuenta**: Número de cuenta, tipo, saldo inicial.
  - **Movimiento**: Fecha, tipo, valor, saldo.

### Funcionalidades del API

- **CRUD**: Crear, editar, actualizar y eliminar registros para Cliente, Cuenta y Movimiento.
- **Registro de Movimientos**: Actualización de saldo y registro de transacciones.
- **Manejo de Errores**: Alertas en caso de saldo insuficiente.
- **Reportes**: Generación de reportes del estado de cuenta con rango de fechas y cliente.

### Instrucciones de Despliegue

1. **Descargar el Repositorio**: Clonar el repositorio desde Git.
2. **Ejecutar Docker Compose**: En el directorio principal del proyecto, ejecutar el siguiente comando:
   ```sh
   sudo docker-compose up --build
   ```

### Entregables

- **Repositorio Git**: Enlace al repositorio público con la solución.
- **Archivo Postman**: JSON con las pruebas realizadas.


