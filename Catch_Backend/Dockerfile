FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/Catch.jar ./Catch.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Catch.jar"]