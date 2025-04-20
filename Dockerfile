# Use an official Maven image to build the app
FROM maven:3.8.6-openjdk-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and dependencies first to leverage Docker's cache
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application (this creates the JAR file)
RUN mvn clean package -DskipTests

# Use a smaller image to run the Spring Boot application
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application runs on (default 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
