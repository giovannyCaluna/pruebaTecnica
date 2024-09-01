# Use Maven to build the application
FROM maven:3.8.7-sapmachine-17 AS builder
WORKDIR /build

# Copy pom.xml and download dependencies
COPY pom.xml .

# Copy the source code and build the application
COPY src ./src
RUN mvn clean install package -DskipTests dependency:go-offline

# Use a smaller base image to run the application
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /build/target/cliente-0.0.1-SNAPSHOT.jar clienteapp.jar

ENTRYPOINT ["java", "-jar", "/app/clienteapp.jar"]
