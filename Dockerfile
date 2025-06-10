# ---------- STAGE 1: Build ----------
FROM maven:3.9.4-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copy pom.xml and dependencies
COPY . .

# Try resolving dependencies (retry if failed)
RUN for i in 1 2 3; do mvn dependency:go-offline -B -U && break || sleep 5; done

# Build the app (skip tests if needed)
RUN mvn clean package -DskipTests

# ---------- STAGE 2: Run ----------
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar
COPY wait-for-it.sh .

RUN chmod +x wait-for-it.sh