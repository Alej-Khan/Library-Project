# Library Project

Proyecto Final curso Spring Boot 3 con Maven

## Descripción

Este es un proyecto Spring Boot 3 que incluye las siguientes dependencias principales:

- **Spring Web**: Para crear APIs RESTful
- **Spring Data JPA**: Para persistencia de datos con JPA
- **H2 Database**: Base de datos en memoria para desarrollo
- **Lombok**: Para reducir código boilerplate

## Requisitos

- Java 17 o superior
- Maven 3.6 o superior

## Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/Alej-Khan/Library-Project.git
cd Library-Project
```

2. Compilar el proyecto:
```bash
mvn clean install
```

## Ejecución

Para ejecutar la aplicación:

```bash
mvn spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`

## Características

### Base de Datos H2

La aplicación utiliza una base de datos H2 en memoria. Puedes acceder a la consola H2 en:

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:librarydb`
- **Usuario**: `sa`
- **Contraseña**: (dejar en blanco)

### API REST Endpoints

El proyecto incluye un ejemplo de API REST para gestionar libros:

- `GET /api/books` - Obtener todos los libros
- `POST /api/books` - Crear un nuevo libro
- `GET /api/books/{id}` - Obtener un libro por ID

### Ejemplo de uso

Crear un libro:
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Don Quijote","author":"Miguel de Cervantes","isbn":"978-8491050384"}'
```

Obtener todos los libros:
```bash
curl http://localhost:8080/api/books
```

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/library/
│   │       ├── LibraryProjectApplication.java  # Clase principal
│   │       ├── controller/
│   │       │   └── BookController.java         # Controlador REST
│   │       ├── model/
│   │       │   └── Book.java                   # Entidad JPA
│   │       └── repository/
│   │           └── BookRepository.java         # Repositorio JPA
│   └── resources/
│       └── application.properties              # Configuración
└── test/
    └── java/
        └── com/library/
            └── LibraryProjectApplicationTests.java
```

## Tecnologías

- **Spring Boot**: 3.2.2
- **Java**: 17
- **Maven**: Gestión de dependencias
- **H2 Database**: Base de datos en memoria
- **Hibernate**: ORM para JPA
- **Lombok**: Generación automática de código

## Pruebas

Para ejecutar las pruebas:

```bash
mvn test
```

## Autor

Alej-Khan

## Licencia

Este proyecto es parte de un curso de Spring Boot.
