FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY target/MatchUp_discovery_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
