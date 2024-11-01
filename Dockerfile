<<<<<<< HEAD
FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
=======
FROM openjdk:11-jre-slim
COPY target/*.jar gestion-station-skii-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar" ,"/gestion-station-skii-0.0.1-SNAPSHOT.jar"]
EXPOSE 8082

>>>>>>> origin/Raefet_jlidi
