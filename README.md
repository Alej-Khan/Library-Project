# Library Management System

Sistema de gestión de biblioteca desarrollado con Spring Boot que implementa autenticación JWT, caché con Redis, y tareas programadas.

## Características

- **Autenticación JWT**: Sistema seguro de autenticación basado en tokens
- **Control de acceso basado en roles**: BIBLIOTECARIO y MIEMBRO
- **Gestión de libros**: CRUD completo de libros
- **Sistema de préstamos**: Registro y devolución de libros
- **Caché**: Optimización con Redis (o caché simple como fallback)
- **Tareas programadas**: Alertas diarias para préstamos vencidos
- **Base de datos H2**: Base de datos en memoria para desarrollo

## Tecnologías

- Java 17
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- JWT (jjwt 0.12.3)
- H2 Database
- Redis (opcional)
- Maven

## Requisitos

- Java 17 o superior
- Maven 3.6 o superior
- Redis (opcional, para caché)

## Instalación y Ejecución

1. Clonar el repositorio:
```bash
git clone https://github.com/Alej-Khan/Library-Project.git
cd Library-Project
```

2. Compilar el proyecto:
```bash
mvn clean package
```

3. Ejecutar la aplicación:
```bash
java -jar target/library-project-1.0.0.jar
```

La aplicación estará disponible en `http://localhost:8080`

## Usuarios Predefinidos

El sistema crea automáticamente dos usuarios al inicio:

- **Bibliotecario**:
  - Username: `bibliotecario`
  - Password: `password123`
  - Rol: BIBLIOTECARIO (acceso completo)

- **Miembro**:
  - Username: `miembro`
  - Password: `password123`
  - Rol: MIEMBRO (acceso limitado)

## API Endpoints

### Autenticación

#### Login
```
POST /api/auth/login
Content-Type: application/json

Body:
{
  "username": "bibliotecario",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "bibliotecario",
  "rol": "BIBLIOTECARIO"
}
```

### Libros

#### Listar todos los libros (Público)
```
GET /api/libros
```

#### Obtener libro por ID (Público)
```
GET /api/libros/{id}
```

#### Crear libro (BIBLIOTECARIO)
```
POST /api/libros
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "titulo": "Don Quijote",
  "autor": "Miguel de Cervantes",
  "isbn": "978-0-06-093434-1",
  "disponible": true
}
```

#### Actualizar libro (BIBLIOTECARIO)
```
PUT /api/libros/{id}
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "titulo": "Don Quijote de la Mancha",
  "autor": "Miguel de Cervantes",
  "isbn": "978-0-06-093434-1",
  "disponible": true
}
```

### Usuarios

#### Registrar nuevo usuario (BIBLIOTECARIO)
```
POST /api/usuarios/registrar
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "username": "juan",
  "password": "password123"
}
```

### Préstamos

#### Prestar libro (BIBLIOTECARIO)
```
POST /api/prestamos/prestar/libro/{libroId}/usuario/{usuarioId}
Authorization: Bearer {token}
```

#### Devolver libro (BIBLIOTECARIO)
```
PUT /api/prestamos/{prestamoId}/devolver
Authorization: Bearer {token}
```

#### Ver mis préstamos (MIEMBRO/BIBLIOTECARIO)
```
GET /api/prestamos/mis-prestamos
Authorization: Bearer {token}
```

## Ejemplos de Uso

### 1. Login y crear un libro

```bash
# Login
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"bibliotecario","password":"password123"}' \
  | jq -r '.token')

# Crear libro
curl -X POST http://localhost:8080/api/libros \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "titulo": "Cien años de soledad",
    "autor": "Gabriel García Márquez",
    "isbn": "978-0-06-088328-7",
    "disponible": true
  }'
```

### 2. Prestar un libro

```bash
# Prestar libro ID 1 al usuario ID 2
curl -X POST http://localhost:8080/api/prestamos/prestar/libro/1/usuario/2 \
  -H "Authorization: Bearer $TOKEN"
```

### 3. Ver mis préstamos como miembro

```bash
# Login como miembro
MEMBER_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"miembro","password":"password123"}' \
  | jq -r '.token')

# Ver mis préstamos
curl -X GET http://localhost:8080/api/prestamos/mis-prestamos \
  -H "Authorization: Bearer $MEMBER_TOKEN"
```

## Lógica de Negocio

### Préstamos
- Al prestar un libro, su estado `disponible` cambia a `false`
- No se puede prestar un libro que ya está prestado
- Al devolver un libro, su estado `disponible` cambia a `true` y se registra la `fechaDevolucion`

### Seguridad
- Los miembros solo pueden ver sus propios préstamos
- Los bibliotecarios tienen acceso completo a todos los recursos
- Los endpoints públicos son: login y listado de libros

### Caché
- Las consultas de libros por ID están cacheadas
- La caché se invalida automáticamente al actualizar un libro

### Tareas Programadas
- Cada día a medianoche, el sistema busca préstamos vencidos (más de 14 días sin devolver)
- Se imprime una alerta en consola por cada préstamo vencido

## Configuración

El archivo `application.properties` contiene las configuraciones:

```properties
# Base de datos H2
spring.datasource.url=jdbc:h2:mem:librarydb

# JWT
jwt.secret.key=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration.ms=86400000

# Redis (opcional)
spring.cache.type=simple
# Descomentar para usar Redis:
# spring.cache.type=redis
```

## Consola H2

La consola H2 está disponible en: `http://localhost:8080/h2-console`

- **JDBC URL**: `jdbc:h2:mem:librarydb`
- **Username**: `sa`
- **Password**: (vacío)

## Estructura del Proyecto

```
src/main/java/com/library/
├── config/              # Configuraciones (Security, Cache, Data)
├── controller/          # Controladores REST
├── dto/                 # Data Transfer Objects
├── entity/              # Entidades JPA
├── repository/          # Repositorios Spring Data
├── security/            # Componentes de seguridad (JWT, Filters)
├── service/             # Lógica de negocio
├── scheduled/           # Tareas programadas
└── LibraryProjectApplication.java
```

## Testing

Para ejecutar las pruebas:

```bash
mvn test
```

## Contribuir

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## Licencia

Este proyecto es un proyecto educativo para el curso de Spring Boot.

## Autor

Alej-Khan
