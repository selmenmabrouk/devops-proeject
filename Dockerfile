FROM openjdk:17-alpine
EXPOSE 8082
ADD target/gestion-station-skii-1.0.jar gestion-station-skii-1.0.jar
ENTRYPOINT ["java","-jar","/gestion-station-skii-1.0.jar"]

