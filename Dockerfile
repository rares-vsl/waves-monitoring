# Stage 1: Build with Gradle
FROM gradle:9.1.0-jdk21 as builder
WORKDIR /usr/src/app

COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle/ ./gradle/
COPY . .

RUN gradle build --no-daemon

# Stage 2: Runtime with slim JDK
FROM openjdk:25-jdk-slim
WORKDIR /app

COPY --from=builder /usr/src/app/build/libs/*.jar app.jar
EXPOSE 3000

ENTRYPOINT ["java", "-jar", "app.jar"]


