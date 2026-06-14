# 1. Use a Maven image to build the app
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Use a lightweight JRE to run the app
FROM eclipse-temurin:17-jre-jammy
# Copy the specific jar built by Maven
COPY --from=build /target/taxi-0.0.1-SNAPSHOT.jar app.jar
# Command to run the application
ENTRYPOINT ["java","-jar","/app.jar"]