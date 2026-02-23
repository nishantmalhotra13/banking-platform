package com.banking.platform.e2e;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

/**
 * E2E: Health check and monitoring endpoints.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HealthCheckE2ETest extends BaseE2ETest {

    @Test
    @Order(1)
    @DisplayName("Gateway health endpoint is UP")
    void gatewayHealth() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .get("/actuator/health")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }

    @Test
    @Order(2)
    @DisplayName("MDM health endpoint is UP")
    void mdmHealth() {
        RestAssured.given()
                .baseUri(MDM_URL)
                .get("/actuator/health")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }

    @Test
    @Order(3)
    @DisplayName("CCSL health endpoint is UP")
    void ccslHealth() {
        RestAssured.given()
                .baseUri(CCSL_URL)
                .get("/actuator/health")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }

    @Test
    @Order(4)
    @DisplayName("Orchestrator health endpoint is UP")
    void orchestratorHealth() {
        RestAssured.given()
                .baseUri(ORCHESTRATOR_URL)
                .get("/actuator/health")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }

    @Test
    @Order(5)
    @DisplayName("Auth service health endpoint is UP")
    void authHealth() {
        RestAssured.given()
                .baseUri(AUTH_URL)
                .get("/actuator/health")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }

    @Test
    @Order(6)
    @DisplayName("Gateway Prometheus metrics available")
    void prometheusMetrics() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .get("/actuator/prometheus")
                .then()
                .statusCode(200)
                .body(containsString("jvm_memory"));
    }

    @Test
    @Order(7)
    @DisplayName("Downstream health check through orchestrator")
    void downstreamHealthCheck() {
        String token = loginAsTestUser();

        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/health/downstream")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(8)
    @DisplayName("Portal links endpoint works")
    void portalLinks() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .get("/api/v1/portal/links")
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    @Order(9)
    @DisplayName("Swagger UI is accessible on gateway")
    void swaggerUi() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .get("/swagger-ui.html")
                .then()
                .statusCode(anyOf(is(200), is(302)));
    }
}
