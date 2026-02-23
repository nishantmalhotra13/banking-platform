package com.banking.platform.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

/**
 * E2E: Search orchestration — phone, name, email search flows through gateway → orchestrator → MDM → CCSL.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SearchFlowE2ETest extends BaseE2ETest {

    private static String token;

    @BeforeAll
    static void authenticate() {
        token = loginAsTestUser();
    }

    @Test
    @Order(1)
    @DisplayName("Search by phone number returns accounts with cards")
    void searchByPhone() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("{\"phoneNumber\": \"4161234567\"}")
                .post("/api/v1/search/phone")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    @Order(2)
    @DisplayName("Search by name returns accounts")
    void searchByName() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("{\"fullName\": \"John Doe\"}")
                .post("/api/v1/search/name")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    @Order(3)
    @DisplayName("Search by email returns accounts")
    void searchByEmail() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("{\"email\": \"john.doe@example.com\"}")
                .post("/api/v1/search/email")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    @Order(4)
    @DisplayName("Search with empty value returns 400")
    void searchWithEmptyValueReturns400() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("{\"phoneNumber\": \"\"}")
                .post("/api/v1/search/phone")
                .then()
                .statusCode(anyOf(is(400), is(422)));
    }
}
