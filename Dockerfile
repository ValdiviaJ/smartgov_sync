# --- Fase 1: Compilación ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar el archivo pom.xml y descargar dependencias (se cachea esta capa)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y empaquetar en un JAR ejecutable
COPY src ./src
RUN mvn clean package -DskipTests

# --- Fase 2: Ejecución ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR compilado desde la fase anterior
COPY --from=build /app/target/sync-api-1.0.0.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
