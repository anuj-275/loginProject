# Use an official Maven image with JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Build Spring Boot app
RUN mvn clean package -DskipTests

# Use lightweight image to run app
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
