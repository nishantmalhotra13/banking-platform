package com.banking.platform.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Base class for all E2E tests.
 * Configures base URLs and waits for services to be healthy before tests run.
 */
public abstract class BaseE2ETest {

    protected static final String GATEWAY_URL = System.getProperty("e2e.gateway.url", "http://localhost:8080");
    protected static final String AUTH_URL = System.getProperty("e2e.auth.url", "http://localhost:8085");
    protected static final String ORCHESTRATOR_URL = System.getProperty("e2e.orchestrator.url", "http://localhost:8086");
    protected static final String MDM_URL = System.getProperty("e2e.mdm.url", "http://localhost:8084");
    protected static final String CCSL_URL = System.getProperty("e2e.ccsl.url", "http://localhost:8082");

    /** Default test credentials — registered at test startup */
    protected static final String TEST_USERNAME = "e2e_testuser";
    protected static final String TEST_PASSWORD = "e2eTestPass123";

    @BeforeAll
    static void waitForCluster() {
        System.out.println("⏳ Waiting for cluster to be healthy...");
        waitForHealth(GATEWAY_URL, "API Gateway");
        waitForHealth(AUTH_URL, "Orchestrator Auth");
        waitForHealth(ORCHESTRATOR_URL, "Orchestrator Service");
        waitForHealth(MDM_URL, "MDM Service");
        waitForHealth(CCSL_URL, "CCSL Service");
        System.out.println("✅ All services healthy — starting E2E tests");

        // Ensure default test user exists
        ensureTestUserExists();
    }

    private static void waitForHealth(String baseUrl, String serviceName) {
        await()
            .atMost(120, TimeUnit.SECONDS)
            .pollInterval(3, TimeUnit.SECONDS)
            .ignoreExceptions()
            .until(() -> {
                Response resp = RestAssured.given()
                        .baseUri(baseUrl)
                        .get("/actuator/health");
                return resp.statusCode() == 200;
            });
        System.out.println("  ✓ " + serviceName + " is UP at " + baseUrl);
    }

    /**
     * Register the default test user if not already present.
     */
    private static void ensureTestUserExists() {
        Response resp = RestAssured.given()
                .baseUri(GATEWAY_URL)
                .contentType(ContentType.JSON)
                .body("{\"username\":\"" + TEST_USERNAME + "\","
                    + "\"password\":\"" + TEST_PASSWORD + "\","
                    + "\"email\":\"" + TEST_USERNAME + "@e2e.test\","
                    + "\"fullName\":\"E2E Default User\"}")
                .post("/auth/register");
        // 200 = registered, 422 = already exists — both OK
        if (resp.statusCode() == 200) {
            System.out.println("  ✓ Test user '" + TEST_USERNAME + "' registered");
        } else {
            System.out.println("  ✓ Test user '" + TEST_USERNAME + "' already exists");
        }
    }

    /**
     * Login as user and return JWT token.
     */
    protected static String loginAndGetToken(String username, String password) {
        Response resp = RestAssured.given()
                .baseUri(GATEWAY_URL)
                .contentType(ContentType.JSON)
                .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
                .post("/auth/login");

        if (resp.statusCode() != 200) {
            throw new RuntimeException("Login failed for " + username + ": " + resp.statusCode() + " — " + resp.body().asString());
        }
        return resp.jsonPath().getString("accessToken");
    }

    /**
     * Convenience: login with default test user.
     */
    protected static String loginAsTestUser() {
        return loginAndGetToken(TEST_USERNAME, TEST_PASSWORD);
    }
}
