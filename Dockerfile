FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY build/libs/whatsapp-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
