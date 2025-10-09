FROM openjdk:17-jdk-slim

WORKDIR /app

COPY /app/target/*.jar globalShopper.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "globalShopper.jar"]
