# --------- Build stage ---------
FROM maven:3.9.6-amazoncorretto-17 AS build

WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the Spring Boot JAR
RUN mvn clean package -DskipTests

# --------- Run stage ---------
FROM amazoncorretto:17-alpine

WORKDIR /app

# Copy the exact built jar by name
COPY --from=build /app/target/visit-log-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
