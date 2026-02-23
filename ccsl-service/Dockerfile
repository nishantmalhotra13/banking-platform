FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle :ccsl-service:bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/ccsl-service/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8082
HEALTHCHECK --interval=30s --timeout=5s --retries=3 CMD wget -qO- http://localhost:8082/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]

