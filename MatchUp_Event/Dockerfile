FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY target/MatchUp_Event-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]
