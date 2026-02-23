package com.banking.platform.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * E2E: Authentication flow — register, login, user info, role management.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthFlowE2ETest extends BaseE2ETest {

    private static String userToken;
    private static String adminToken;

    @Test
    @Order(1)
    @DisplayName("Register a new admin test user")
    void registerTestAdmin() {
        // Register a fresh admin-to-be user
        Response resp = RestAssured.given()
                .baseUri(GATEWAY_URL)
                .contentType(ContentType.JSON)
                .body("""
                    {
                      "username": "e2e_admin",
                      "password": "testpass123",
                      "email": "e2e_admin@test.com",
                      "fullName": "E2E Admin"
                    }
                    """)
                .post("/auth/register");

        // 200 = success, 422 = already exists from a previous run
        assertThat(resp.statusCode()).isIn(200, 422);
    }

    @Test
    @Order(2)
    @DisplayName("Register a new regular test user")
    void registerTestUser() {
        Response resp = RestAssured.given()
                .baseUri(GATEWAY_URL)
                .contentType(ContentType.JSON)
                .body("""
                    {
                      "username": "e2e_user",
                      "password": "testpass123",
                      "email": "e2e_user@test.com",
                      "fullName": "E2E User"
                    }
                    """)
                .post("/auth/register");

        assertThat(resp.statusCode()).isIn(200, 422);
    }

    @Test
    @Order(3)
    @DisplayName("Login as registered user")
    void loginAsUser() {
        userToken = loginAndGetToken("e2e_user", "testpass123");
        assertThat(userToken).isNotBlank();
    }

    @Test
    @Order(4)
    @DisplayName("Login as registered admin")
    void loginAsAdmin() {
        adminToken = loginAndGetToken("e2e_admin", "testpass123");
        assertThat(adminToken).isNotBlank();
    }

    @Test
    @Order(5)
    @DisplayName("Get current user info (/auth/me)")
    void getCurrentUser() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + userToken)
                .get("/auth/me")
                .then()
                .statusCode(200)
                .body("username", equalTo("e2e_user"))
                .body("role", equalTo("USER"));
    }

    @Test
    @Order(6)
    @DisplayName("List users endpoint works (any authenticated user)")
    void listUsers() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + adminToken)
                .get("/auth/users")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2));
    }
}
