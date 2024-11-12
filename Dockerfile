FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copier les fichiers de projet dans le conteneur
COPY pom.xml .
COPY src ./src
# Exposer le port par défaut de l'application Spring Boot (8080)
EXPOSE 8080

ADD target/gestion-station-ski-1.0.jar gestion-station-ski-1.0.jar
# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]