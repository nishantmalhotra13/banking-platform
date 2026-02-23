FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle :mdm-service:bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/mdm-service/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8084
HEALTHCHECK --interval=30s --timeout=5s --retries=3 CMD wget -qO- http://localhost:8084/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]

