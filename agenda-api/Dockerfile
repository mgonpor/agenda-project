# Multi-stage build optimizado
FROM maven:3.9.6-eclipse-temurin-21 as builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build JAR
RUN mvn clean package -DskipTests

# Runtime stage - imagen ligera
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]