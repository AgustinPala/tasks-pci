# Utiliza una imagen base de Java 17
FROM openjdk:17-slim-buster

# Agrega un usuario no root
RUN groupadd -r myapp && useradd -r -g myapp myapp

# Establece el directorio de trabajo
WORKDIR /usr/app

# Copia el archivo JAR de tu aplicación al contenedor
COPY target/Task-Management-0.0.1-SNAPSHOT.jar app.jar

# Establece el usuario no root como el usuario por defecto
USER myapp

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "app.jar"]
