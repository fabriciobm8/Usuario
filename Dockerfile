FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

COPY --chown=spring:spring target/usuario-0.0.1-SNAPSHOT.jar app-v1.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app-v1.jar"]