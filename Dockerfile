# ---------- STAGE 1: Build ----------
FROM maven:3.9.4-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copy pom.xml and dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source
COPY src ./src

# Build the app (skip tests if needed)
RUN mvn clean package -DskipTests

# ---------- STAGE 2: Run ----------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
