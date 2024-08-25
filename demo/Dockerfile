# Use Maven to build the application
FROM maven:3.8.5-openjdk-17 AS builder
COPY pom.xml .
COPY src ./src
# Build the application
RUN mvn clean install package -DskipTests

FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]



