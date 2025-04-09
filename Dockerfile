# Usa una imagen base oficial de OpenJDK 8
FROM openjdk:8-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY target/serviciousuarios-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que la API se ejecuta
EXPOSE 8093

# Define el comando para ejecutar la API
ENTRYPOINT ["java", "-jar", "/app/app.jar"]