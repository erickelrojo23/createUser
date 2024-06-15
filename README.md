# API RESTful de Gestión de Usuarios

Esta es una API RESTful desarrollada en Java con Spring Boot para la gestión de usuarios. La API permite crear usuarios, validarlos, y gestionarlos utilizando una base de datos en memoria H2. Además, se implementa autenticación con JWT y documentación con Swagger.

# Características

- Creación de usuarios con validación de correo y contraseña.
- Persistencia de datos en una base de datos H2.
- Generación de tokens JWT.
- Documentación interactiva con Swagger.
- Pruebas unitarias.

# Requisitos

- Java 22
- Maven 3.6+
- Git (opcional)

# Instalación

1. Clona el repositorio:
   ```sh
   git clone https://github.com/tu-usuario/nombre-del-repositorio.git
   cd nombre-del-repositorio
2. Compila y empaqueta el proyecto:
    ```sh
    mvn clean package


# Ejecución
1. Ejecuta la aplicación:
    ```sh
    mvn spring-boot:run

La aplicación estará disponible en http://localhost:8091.

# Endpoints
## Crear Usuario
- URL: /api/usuarios
- Método: POST
- Content-Type: application/json

- Request Body:
    ```sh
    {
        "name": "Juan Rodriguez",
        "email": "juan@rodriguez.org",
        "password": "hunter2",
        "phones": [
            {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
            }
        ]
    }

- Respuestas:
  - 201 Created: Usuario creado exitosamente.
      ```sh
      {
          "id": "uuid-del-usuario",
          "name": "Juan Rodriguez",
          "email": "juan@rodriguez.org",
          "password": "encrypted-password",
          "created": "fecha-de-creación",
          "modified": "fecha-de-modificación",
          "lastLogin": "fecha-de-último-ingreso",
          "token": "jwt-token",
          "isActive": true,
          "phones": [
                  {
                  "id": 1,
                  "number": "1234567",
                  "citycode": "1",
                  "contrycode": "57"
                  }
          ]
      }

  - 400 Bad Request: Error en la solicitud (por ejemplo, correo ya registrado).
      ```sh
      {
      "mensaje": "El correo ya registrado"
      }

# Documentación
- La documentación interactiva de la API está disponible en http://localhost:8091/swagger-ui.html después de iniciar la aplicación.

# Pruebas
- Ejecuta las pruebas unitarias:
    ```sh
    mvn test

# Configuración
## Expresiones Regulares
- La validación del correo se realiza utilizando la expresión regular aaaaaaa@dominio.cl.
- La validación de la contraseña se puede configurar en application.properties.

## Seguridad
La configuración de seguridad y el cifrado de contraseñas se realiza mediante BCryptPasswordEncoder.

# Base de Datos
- La aplicación utiliza una base de datos en memoria H2. La consola de H2 está disponible en http://localhost:8091/h2-console. Utiliza las siguientes credenciales para acceder:

    - URL: jdbc:h2:mem:testdb
    - Username: sa
    - Password: password
  
## Contribuciones
Si deseas contribuir a este proyecto, por favor sigue los pasos a continuación:

1. Haz un fork del proyecto.
2. Crea una rama nueva (git checkout -b feature/nueva-funcionalidad).
3. Realiza tus cambios y haz commit (git commit -am 'Agrega nueva funcionalidad').
4. Sube los cambios a tu fork (git push origin feature/nueva-funcionalidad).
5. Crea un Pull Request.

## Contacto
Para cualquier consulta o sugerencia, puedes contactarme a erick.vasquezm@dominio.com.