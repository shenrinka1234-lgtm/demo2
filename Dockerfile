FROM eclipse-temurin:17-jdk-jammy
COPY . .
RUN ./mvnw clean package -DskipTests
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
