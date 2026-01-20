FROM eclipse-temurin:25-jdk-ubi10-minimal
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]

# To build the Docker image, use the following command:
    # docker build -t config-server .
# To run the Docker container, use the following command:
    # docker run --env-file .env -d --name config-server -p 8888:8888 config-server