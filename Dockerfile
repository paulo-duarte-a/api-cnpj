# Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper
COPY cnpjbrasil/mvnw .
COPY cnpjbrasil/.mvn ./.mvn

# Copy the pom.xml file
COPY cnpjbrasil/pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy the application source code
COPY cnpjbrasil/src ./src

# Package the application
RUN ./mvnw package -DskipTests

# Define the entrypoint to run the application in development mode with hot reload
ENTRYPOINT ["./mvnw", "spring-boot:run"]

# Expose the default Spring Boot port (adjust if your app uses a different port)
EXPOSE 8080