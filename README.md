# 📚 IES La Vereda — AMPA Libros · Spring Boot API

API REST para la gestión de la biblioteca del AMPA y el inventario de libros de texto del IES La Vereda.

---

## 🗄️ Base de datos

El proyecto usa **MySQL**. Asegúrate de tener MySQL corriendo y edita `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/IesLaVereda?createDatabaseIfNotExist=true&...
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

Hibernate creará las tablas automáticamente (`ddl-auto=update`).  
La primera vez que arranques, el `DataLoader` insertará todos los datos de la web.

---

## 🗂️ Tablas que se crean

| Tabla        | Descripción                                              |
|-------------|----------------------------------------------------------|
| `usuarios`   | Alumnos y profesores (NIA, nombre, apellidos, tipo...)   |
| `libros`     | Libros AMPA + libros de texto (título, ISBN, stock...)   |
| `prestamos`  | Quién tiene cada libro, fechas y estado                  |

---

## 🚀 Arrancar el proyecto

```bash
# Desde la carpeta del proyecto
mvn spring-boot:run
```

El servidor arranca en `http://localhost:8080`

---

## 📡 Endpoints disponibles

### Usuarios (`/api/usuarios`)
| Método | URL                           | Descripción                     |
|--------|-------------------------------|---------------------------------|
| GET    | `/api/usuarios`               | Todos los usuarios              |
| GET    | `/api/usuarios/{id}`          | Usuario por ID                  |
| GET    | `/api/usuarios/nia/{nia}`     | Usuario por NIA (ej: 202601)    |
| GET    | `/api/usuarios/alumnos`       | Solo alumnos                    |
| GET    | `/api/usuarios/profesores`    | Solo profesores                 |
| GET    | `/api/usuarios/buscar?q=pau`  | Buscar por nombre/apellidos     |
| POST   | `/api/usuarios`               | Crear usuario                   |
| PUT    | `/api/usuarios/{id}`          | Actualizar usuario              |
| DELETE | `/api/usuarios/{id}`          | Eliminar usuario                |

### Libros (`/api/libros`)
| Método | URL                                        | Descripción                         |
|--------|-------------------------------------------|-------------------------------------|
| GET    | `/api/libros`                             | Todos los libros                    |
| GET    | `/api/libros/{id}`                        | Libro por ID                        |
| GET    | `/api/libros/isbn/{isbn}`                 | Libro por ISBN                      |
| GET    | `/api/libros/categoria/COMEDIA`           | Por categoría (COMEDIA, TERROR...)  |
| GET    | `/api/libros/disponibles`                 | Solo libros con stock disponible    |
| GET    | `/api/libros/buscar?q=cosmic`             | Buscar por título o autor           |
| GET    | `/api/libros/texto?asignatura=Matemáticas&nivel=1 ESO` | Libros de texto |
| POST   | `/api/libros`                             | Crear libro                         |
| PUT    | `/api/libros/{id}`                        | Actualizar libro                    |
| DELETE | `/api/libros/{id}`                        | Eliminar libro                      |

### Préstamos (`/api/prestamos`)
| Método | URL                                   | Descripción                          |
|--------|---------------------------------------|--------------------------------------|
| GET    | `/api/prestamos`                      | Todos los préstamos                  |
| GET    | `/api/prestamos/{id}`                 | Préstamo por ID                      |
| GET    | `/api/prestamos/usuario/{id}`         | Historial de un usuario              |
| GET    | `/api/prestamos/nia/202601`           | Historial por NIA                    |
| GET    | `/api/prestamos/activos`              | Préstamos pendientes de devolución   |
| GET    | `/api/prestamos/vencidos`             | Préstamos vencidos                   |
| POST   | `/api/prestamos`                      | Realizar préstamo                    |
| PUT    | `/api/prestamos/{id}/devolver`        | Devolver un libro                    |
| PUT    | `/api/prestamos/actualizar-vencidos`  | Marcar vencidos automáticamente      |

---

## 📝 Ejemplos de uso

### Realizar un préstamo
```json
POST /api/prestamos
{
  "usuarioId": 1,
  "libroId": 3,
  "dias": 30
}
```

### Devolver un libro
```json
PUT /api/prestamos/1/devolver
{
  "observaciones": "Devuelto en buen estado"
}
```

### Crear un alumno
```json
POST /api/usuarios
{
  "nia": "202610",
  "nombre": "Laura",
  "apellidos": "Fernández",
  "tipo": "ALUMNO",
  "cursoOCargo": "2º ESO"
}
```

---

## 🛠️ Tecnologías
- Java 17
- Spring Boot 3.2.5
- Spring Data JPA + Hibernate
- MySQL
- Lombok
- Maven
