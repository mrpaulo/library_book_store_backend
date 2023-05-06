# Use a imagem do openjdk 11 como base
FROM openjdk:11-jdk-slim

# Define um diretório para a aplicação
WORKDIR /app

# Copia o pom.xml e o arquivo de configuração do Maven para o diretório da aplicação
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Copia o código-fonte do projeto para o diretório da aplicação
COPY src src

# Define as permissões do arquivo mvnw para que ele possa ser executado dentro do container
RUN chmod +x mvnw

# Executa o comando 'mvn clean package' dentro do container para compilar e construir o pacote .jar
RUN ./mvnw clean package -DskipTests

# Copia o jar do projeto para o diretório da aplicação
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expõe a porta 4080 para o host
EXPOSE 4080

# Define o comando para rodar a aplicação
CMD ["java", "-jar", "/app/app.jar"]

