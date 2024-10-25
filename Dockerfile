# Utiliser l'image de base OpenJDK 17
FROM openjdk:17-alpine

# Créer un répertoire pour l'application
WORKDIR /app

# Créer un volume pour les logs (optionnel)
VOLUME /tmp

# Copier le fichier JAR dans l'image Docker (assurez-vous que le JAR existe à cet emplacement)
COPY target/gestion-station-skii-0.0.1-SNAPSHOT.jar gestion-station-skii.jar

# Exposer le port utilisé par l'application
EXPOSE 8082

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "gestion-station-skii.jar"]
