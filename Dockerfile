# Etapa 1: Construção do projeto
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretório de trabalho para o build
WORKDIR /app

# Copia os arquivos do projeto Maven para o container
COPY . .

# Executa o Maven para compilar o projeto e gerar o .jar
RUN mvn clean package -DskipTests

# Etapa 2: Construção da imagem final
FROM eclipse-temurin:21-jdk-alpine

# Criação do grupo e usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Define o diretório de trabalho no container final
WORKDIR /app

# Copia o .jar gerado na etapa anterior para o container final
COPY --chown=spring:spring --from=build /app/target/usuario-0.0.1-SNAPSHOT.jar app-v1.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Define o comando de entrada
ENTRYPOINT ["java", "-jar", "app-v1.jar"]
