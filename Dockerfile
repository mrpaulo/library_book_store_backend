# Use the openjdk 11 image as the base
FROM openjdk:11-jdk-slim
# Define a directory for the application
WORKDIR /app
# Copy the pom.xml and Maven configuration file to the application directory
COPY pom.xml mvnw ./
COPY .mvn .mvn
# Copy the source code of the project to the application directory
COPY src src
# Set permissions for the mvnw file to make it executable inside the container
RUN chmod +x mvnw
# Run the 'mvn clean package' command inside the container to compile and build the .jar package
RUN ./mvnw clean package -DskipTests
# Copy the project's jar file to the application directory
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# Expose port 4088 to the host
EXPOSE 4088
# Define the command to run the application
CMD ["java", "-jar", "/app/app.jar"]

