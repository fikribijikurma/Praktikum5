# Stage 1: Build the application using the included Maven Wrapper
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# 1. Copy Maven Wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Fix line endings for mvnw (in case of Windows) and make it executable
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# 2. Download dependencies
RUN ./mvnw dependency:go-offline -B

# 3. Copy source code and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final lightweight image
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/praktikum6-0.0.1-SNAPSHOT.jar app.jar

# Ensure the upload directory exists
RUN mkdir -p src/main/resources/static/img/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]