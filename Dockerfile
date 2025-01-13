# Stage 1: Build the application
FROM gradle:7.6.0-jdk17 AS builder
WORKDIR /app

# Set the Gradle user home directory
ENV GRADLE_USER_HOME=/app/.gradle

# Ensure proper permissions for Gradle cache
RUN mkdir -p /app/.gradle && chmod -R 777 /app/.gradle

# Copy Gradle configuration and source files
COPY gradlew /app/
COPY gradle /app/gradle
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Use the Gradle wrapper to build the application
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Package the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
