# Stage 1: Build
FROM eclipse-temurin:25-jdk-alpine AS builder
WORKDIR /app

# Copy Maven Wrapper files and module configurations first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY core/pom.xml core/
COPY application/pom.xml application/
COPY framework/pom.xml framework/

# Grant execution permission to Maven Wrapper script and cache dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source code for all modules
COPY core/src ./core/src
COPY application/src ./application/src
COPY framework/src ./framework/src

# Compile project and package the application, skipping unit tests during image build
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Create a non-root system group and user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copy only the final runnable JAR from the framework module
COPY --from=builder /app/framework/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
