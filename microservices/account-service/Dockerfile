# ===============================
# STAGE 1 - Build
# ===============================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiamos solo pom para cache de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Construimos el jar sin tests
RUN mvn clean package -DskipTests

# ===============================
# STAGE 2 - Runtime
# ===============================
FROM eclipse-temurin:17-jre-alpine

# Crear usuario no root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

# Copiamos el jar desde el stage de build
COPY --from=builder /app/target/*.jar app.jar
RUN chown spring:spring app.jar

USER spring

# Puerto de tu aplicación
EXPOSE 8082

# Healthcheck opcional (requiere Spring Actuator)
HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=3 \
  CMD wget -qO- http://localhost:8082/actuator/health || exit 1

# Variables de entorno por defecto (opcional)
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=prod
ENV CUSTOMER_SERVICE_URL=http://host.docker.internal:8081/api/v1/customers


# Ejecutamos el jar con optimización de contenedor
ENTRYPOINT ["sh","-c","java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC $JAVA_OPTS -jar app.jar"]
