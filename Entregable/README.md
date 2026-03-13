API de Usuarios – Prueba Técnica

Esta es una API REST desarrollada con Spring Boot para la gestión de usuarios y autenticación.

Tecnologías utilizadas

Java 21

Spring Boot

Maven

Docker

Requisitos

Para ejecutar este proyecto es necesario tener instalado:

Java 21

Maven

Docker (opcional, para ejecutar la aplicación en contenedor)

Ejecutar el proyecto
1. Clonar el repositorio
git clone https://github.com/rafiki04/API_entrega.git

Entrar a la carpeta del proyecto:

cd API_entrega
Ejecutar con Maven

Construir el proyecto:

mvn clean package

Ejecutar la aplicación:

java -jar target/Entregable-0.0.1-SNAPSHOT.jar

La API iniciará en:

http://localhost:8080
Ejecutar con Docker

Construir la imagen de Docker:

docker build -t user-api .

Ejecutar el contenedor:

docker run -p 8080:8080 user-api

La API quedará disponible en:

http://localhost:8080
Endpoints principales
Usuarios

GET /users
Obtiene la lista de usuarios.

POST /users
Crea un nuevo usuario.

PATCH /users/{id}
Actualiza los datos de un usuario por su ID.

DELETE /users/{id}
Elimina un usuario por su ID.

Autenticación

POST /login

Permite autenticar un usuario utilizando:

tax_id (usuario)

password

Las contraseñas se almacenan utilizando cifrado AES256.

Funcionalidades adicionales

Validación del formato de tax_id (RFC)

Validación de número telefónico

Generación automática de fecha de creación

Soporte para ejecución mediante Docker

Repositorio

Código fuente disponible en:

https://github.com/rafiki04/API_entrega

Autor

Cristian Rafael