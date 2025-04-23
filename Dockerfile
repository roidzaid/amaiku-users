# Usa una imagen base oficial de OpenJDK 8
FROM openjdk:8-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY target/amaiku-users-1.0.0.jar app.jar

# Expone puertos
EXPOSE 8090
EXPOSE 5005

# Comando de inicio, utilizando JAVA_TOOL_OPTIONS
CMD java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005 -jar /app/app.jar