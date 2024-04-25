# Start with a base image that includes both Java and Maven
FROM maven:3.8-openjdk-17-slim as build

# Copy the source code into the image
COPY src /home/app/src
COPY pom.xml /home/app

# Set the working directory
WORKDIR /home/app

# Include Cassandra JDBC driver in the pom.xml dependencies before building
# Run Maven build to package the application
RUN mvn clean package -DskipTests

# Start a new stage from the Java runtime for a smaller image size
FROM openjdk:17-jdk-slim

# Copy only the JAR from the build stage
COPY --from=build /home/app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
