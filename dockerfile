# Use the official OpenJDK image for a base image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build file into the image
COPY target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
