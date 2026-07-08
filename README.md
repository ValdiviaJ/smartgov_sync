# 🚀 Smart-Gov Sync - API REST (Spring Boot)

Este es el backend para el **Sistema Móvil de Gestión de Trámite Documentario (Smart-Gov Sync)**, rediseñado en **Spring Boot 3** con **Java 21** y **SQLite** como base de datos centralizada, cumpliendo con los requerimientos del curso.

---

## 📂 Estructura del Proyecto

El backend está organizado de la siguiente manera bajo el paquete `com.smartgov.sync`:
*   `config.DatabaseInitializer`: Se ejecuta al iniciar el servidor. Crea y siembra datos iniciales de prueba si la base de datos está vacía.
*   `config.FilterConfig`: Registra el filtro JWT para interceptar peticiones de la API.
*   `security.JwtTokenUtil`: Utilidad para la creación, expiración y validación de tokens JWT.
*   `security.JwtAuthenticationFilter`: Validador de tokens `Bearer` para proteger todas las rutas CRUD y de sincronización.
*   `model`: Clases entidad JPA mapeadas idénticamente a las tablas del sistema:
    *   `Oficina`, `PersonalEspecialista`, `Usuario` (Login)
    *   `TipoDocumento`, `Administrado`, `AdministradoDireccion` (Geolocalización)
    *   `ExpedienteGeneral`, `DocumentoIngresado` (Multimedia)
    *   `HojaRutaDerivacion`, `ArchivoFisicoCentral`, `ActaArchivamiento`
*   `repository`: Interfaces Spring Data JPA para operaciones sobre la BD SQLite.
*   `controller`:
    *   `HomeController`: Health check en la ruta raíz (`/`).
    *   `AuthController`: Maneja el Login (`POST /api/auth/login`) con BCrypt y JWT.
    *   `CrudController`: Operaciones CRUD dinámicas sobre las 10 tablas (`/api/{tabla}`).
    *   `SyncController`: Endpoints de sincronización offline: Pull (`GET /api/sincronizacion`) y Push (`POST /api/sync-data`).

---

## ⚡ Requisitos previos

*   **Java Development Kit (JDK) 21** o superior instalado.
*   Un IDE compatible (IntelliJ IDEA recomendado, Eclipse, VS Code o NetBeans).

---

## 🔧 Configuración (`src/main/resources/application.properties`)

El proyecto está configurado para ejecutarse sobre una base de datos local SQLite:
```properties
spring.datasource.url=jdbc:sqlite:database.sqlite
spring.jpa.hibernate.ddl-auto=update
```
*La base de datos `database.sqlite` se creará automáticamente en la raíz del proyecto al iniciar el servidor.*

---

## ⚙️ Cómo ejecutar el proyecto

### Opción A: Desde tu IDE (IntelliJ / Eclipse)
1. Abre tu IDE y selecciona **Import Project** o **Open**.
2. Selecciona la carpeta `API-REST` (donde está el archivo `pom.xml`).
3. El IDE descargará automáticamente las dependencias declaradas en Maven.
4. Abre la clase `com.smartgov.sync.SyncApiApplication` y ejecútala (run).

### Opción B: Desde la Consola (si tienes Maven instalado)
1. Ejecuta:
   ```bash
   mvn spring-boot:run
   ```

---

## 🔑 Credenciales de Prueba creadas al iniciar
*   **Administrador:** usuario `admin` | contraseña `admin123`
*   **Especialista:** usuario `user` | contraseña `user123`
