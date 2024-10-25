FROM openjdk:11-jre-slim
COPY target/*.jar gestion-station-skii-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar" ,"/gestion-station-skii-0.0.1-SNAPSHOT.jar"]
EXPOSE 8082

