# 1. Use a Maven image to build the app
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Use a lightweight JRE to run the app (Updated for stability)
FROM eclipse-temurin:17-jre-jammy
# Copy the built jar from the build stage to the final image
COPY --from=build /target/*.jar app.jar
# Command to run the application
ENTRYPOINT ["java","-jar","/app.jar"]