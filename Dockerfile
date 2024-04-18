# Use official OpenJDK 17 image as base
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container at /app
COPY target/demo-0.0.1-SNAPSHOT.jar /app

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
