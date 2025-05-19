# Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# update packages and install unzip
RUN apt-get update && \
    apt-get install -y unzip curl && \
    rm -rf /var/lib/apt/lists/*

RUN mkdir -p /opt/oracle
RUN wget -O /tmp/instantclient-basic-linux.x64-19.27.0.0.0dbru.zip \
    https://download.oracle.com/otn_software/linux/instantclient/1927000/instantclient-basic-linux.x64-19.27.0.0.0dbru.zip
RUN unzip /tmp/instantclient-basic-linux.x64-19.27.0.0.0dbru.zip -d /opt/oracle/
RUN rm /tmp/instantclient-basic-linux.x64-19.27.0.0.0dbru.zip
RUN sh -c "echo /opt/oracle/instantclient_19_27 > \
        /etc/ld.so.conf.d/oracle-instantclient.conf"
RUN ldconfig

# Copy the Maven wrapper
COPY cnpjbrasil/mvnw .
COPY cnpjbrasil/.mvn ./.mvn

# Copy the pom.xml file
COPY cnpjbrasil/pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy dbwallet
COPY dbwallet ./dbwallet

# Copy the application source code
COPY cnpjbrasil/src ./src

# Package the application
RUN ./mvnw package -DskipTests

# Define the entrypoint to run the application in development mode with hot reload
ENTRYPOINT ["./mvnw", "spring-boot:run"]

# Expose the default Spring Boot port (adjust if your app uses a different port)
EXPOSE 8080