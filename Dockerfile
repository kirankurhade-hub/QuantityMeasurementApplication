FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies (cached layer)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source
COPY src/ src/

# Build
RUN ./mvnw package -DskipTests -B

# Expose port
EXPOSE 5000

# Run
CMD ["java", "-jar", "target/QuantityMeasurementApp-0.0.1-SNAPSHOT.jar"]
