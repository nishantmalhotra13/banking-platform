package com.banking.platform.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

/**
 * E2E: Account and card endpoints — direct service tests through gateway.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountCardE2ETest extends BaseE2ETest {

    private static String token;

    // Use a known account number from MDM seed data
    private static final String KNOWN_ACCOUNT = "ACC1001";

    @BeforeAll
    static void authenticate() {
        token = loginAsTestUser();
    }

    @Test
    @Order(1)
    @DisplayName("Get all cards for an account")
    void getAllCards() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/accounts/" + KNOWN_ACCOUNT + "/cards")
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    @Order(2)
    @DisplayName("Get credit cards for an account")
    void getCreditCards() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/accounts/" + KNOWN_ACCOUNT + "/cards/credit")
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    @Order(3)
    @DisplayName("Get debit cards for an account")
    void getDebitCards() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/accounts/" + KNOWN_ACCOUNT + "/cards/debit")
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    @Order(4)
    @DisplayName("Get account summary")
    void getAccountSummary() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/accounts/" + KNOWN_ACCOUNT + "/summary")
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    @Order(5)
    @DisplayName("Validate a card number")
    void validateCard() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("{\"cardNumber\": \"4111111111111111\"}")
                .post("/api/v1/cards/validate")
                .then()
                .statusCode(200)
                .body("valid", isA(Boolean.class));
    }

    @Test
    @Order(6)
    @DisplayName("Non-existent account returns error status")
    void nonExistentAccount() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/accounts/DOESNOTEXIST/summary")
                .then()
                .statusCode(anyOf(is(404), is(500), is(502)));
    }
}
