FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/app.jar app.jar
COPY wait-for-it.sh wait-for-it.sh

RUN chmod +x wait-for-it.sh