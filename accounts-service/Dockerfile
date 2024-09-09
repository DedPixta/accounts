FROM --platform=linux/arm64/v8 eclipse-temurin:21.0.4_7-jdk-alpine

LABEL org.opencontainers.image.authors="max.kossatyy@gmail.com"

COPY build/libs/*SNAPSHOT.jar /app/
RUN mv /app/*SNAPSHOT.jar /app/accounts.jar

ENTRYPOINT ["java", "-jar", "/app/accounts.jar"]