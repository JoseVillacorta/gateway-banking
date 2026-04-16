
# Banking System API Gateway

**Puerta de enlace única y servidor perimetral para el ecosistema bancario.**

Este proyecto actúa como el punto de entrada central para todas las solicitudes de clientes (Frontend, Apps Móviles, etc.). Se encarga de redirigir el tráfico al microservicio correspondiente de manera dinámica, además de centralizar la seguridad y las políticas de acceso (CORS).

## Descripción del Proyecto

El **Gateway** utiliza **Spring Cloud Gateway** para proporcionar un enrutamiento eficiente y resiliente basado en el registro de servicios de Eureka.

### Qué hace esta aplicación
- **Enrutamiento Dinámico:** Redirige peticiones a través de nombres de servicio (ej. `lb://ms-customer`).
- **Seguridad Centralizada:** Implementa validación de tokens JWT mediante OAuth2 como Resource Server.
- **Control de CORS:** Centraliza las reglas de intercambio de recursos de origen cruzado para todos los servicios.
- **Abstracción:** Oculta la complejidad de la infraestructura interna, exponiendo solo un puerto (`8080`).

### Tecnologías utilizadas
- **Java 21**
- **Spring Boot 3.x** / **WebFlux** (Arquitectura no bloqueante).
- **Spring Cloud Gateway**
- **Spring Security (OAuth2 / JWT)**
- **Eureka Client**

---

## Cómo instalar y ejecutar el proyecto

### Requisitos previos
1. **ms-config-server** corriendo.
2. **registry-service** corriendo.

### Pasos para ejecución local (Gradle)
1. Navega a la carpeta: `cd gateway`
2. Ejecuta:
   ```bash
   ./gradlew bootRun
   ```

### Pasos para ejecución con Docker
```bash
docker-compose up -d gateway
```

---

## Cómo utilizar el proyecto

Todas las peticiones externas deben apuntar a este servicio en el puerto `8080`.

### Rutas principales (Endpoint unificado)
- **Clientes:** `http://localhost:8080/api/v1/customers/**`
- **Cuentas:** `http://localhost:8080/api/v1/accounts/**`
- **Transacciones:** `http://localhost:8080/api/v1/transactions/**`

### Seguridad
El Gateway está configurado para requerir un **Bearer Token (JWT)** válido emitido por el `oauth-server`. Si una petición no incluye el token o este ha expirado, el Gateway responderá con un código `401 Unauthorized`.
