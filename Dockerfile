FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY /app/target/*.jar globalShopper.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "globalShopper.jar --server.port=${PORT:-8080}"]
