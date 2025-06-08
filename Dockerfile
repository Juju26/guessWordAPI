# ---------- STAGE 1: Build ----------
FROM maven:3.9.4-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copy pom.xml and dependencies
COPY pom.xml .

# Try resolving dependencies (retry if failed)
RUN for i in 1 2 3; do mvn dependency:go-offline -B -U && break || sleep 5; done

# Copy the rest of the source
COPY src ./src

# Build the app (skip tests if needed)
RUN mvn clean package -DskipTests

# ---------- STAGE 2: Run ----------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar
COPY wait-for-it.sh .

RUN chmod +x wait-for-it.sh

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
