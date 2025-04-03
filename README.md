DESK-MINI Microservicios
========================

Sistema de microservicios que incluye autenticación y frontend.

Servicios
---------

Auth Service (Puerto 8081)
* Servicio de autenticación y autorización que gestiona:
  - Usuarios, roles y permisos
  - Autenticación JWT
  - Auditoría de accesos

Frontend Service (Puerto 8082)
* Servicio de interfaz de usuario que proporciona:
  - Dashboard de aplicaciones
  - Gestión de tarjetas de aplicación
  - Interfaz de autenticación

Requisitos
----------
- Java 17+
- MongoDB 4.4+
- Maven 3.6+

Configuración
------------

Variables de Entorno (.env)
--------------------------
# Auth Service
AUTH_MONGODB_URI=mongodb://localhost:27017/auth_db
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000

# Frontend Service
FRONTEND_MONGODB_URI=mongodb://localhost:27017/frontend_db

Seguridad
---------
* Los secretos JWT deben ser de al menos 256 bits
* Las contraseñas se almacenan con BCrypt
* CORS configurado solo para dominios autorizados
* Headers de seguridad habilitados

Instalación y Ejecución
-----------------------

1. Auth Service:
   cd auth-service
   ./mvnw clean install
   ./mvnw spring-boot:run

2. Frontend Service:
   cd frontend-service
   ./mvnw clean install
   ./mvnw spring-boot:run

Desarrollo
---------

Convenciones:
* Usar Java 17 features
* Tests unitarios para toda nueva funcionalidad
* Documentar APIs con OpenAPI/Swagger

Testing:
./mvnw test

Seguridad
---------

Mejores Prácticas:
1. No commitear archivos .env o secretos
2. Usar variables de entorno en producción
3. Rotar secretos JWT periódicamente
4. Monitorear logs de auditoría

Headers de Seguridad:
* X-Content-Type-Options: nosniff
* X-Frame-Options: DENY
* X-XSS-Protection: 1; mode=block
* Content-Security-Policy: configurado apropiadamente

Contribución
-----------
1. Crear branch: feature/nombre-feature
2. Commitear cambios
3. Crear Pull Request
4. Esperar revisión

Licencia
--------
Propietario - Juanjo Martínez
