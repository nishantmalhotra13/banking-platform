package com.banking.platform.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * E2E: Full user journey — login → search → view account → validate card.
 * This test simulates a real user session end-to-end.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FullJourneyE2ETest extends BaseE2ETest {

    private static String token;
    private static String foundAccountNumber;

    @Test
    @Order(1)
    @DisplayName("Step 1: User logs in")
    void step1_login() {
        token = loginAsTestUser();
        assertThat(token).isNotBlank();
        System.out.println("  → Logged in, token length: " + token.length());
    }

    @Test
    @Order(2)
    @DisplayName("Step 2: User searches by phone number")
    void step2_searchByPhone() {
        Response resp = RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("{\"phoneNumber\": \"4161234567\"}")
                .post("/api/v1/search/phone");

        resp.then().statusCode(200);

        int count = resp.jsonPath().getList("$").size();
        System.out.println("  → Search returned " + count + " account(s)");

        if (count > 0) {
            foundAccountNumber = resp.jsonPath().getString("[0].accountNumber");
            System.out.println("  → Using account: " + foundAccountNumber);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Step 3: User views account summary")
    void step3_viewAccountSummary() {
        Assumptions.assumeTrue(foundAccountNumber != null, "No account found from search");

        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/accounts/" + foundAccountNumber + "/summary")
                .then()
                .statusCode(200)
                .body("accountNumber", equalTo(foundAccountNumber));

        System.out.println("  → Account summary fetched for " + foundAccountNumber);
    }

    @Test
    @Order(4)
    @DisplayName("Step 4: User views credit cards")
    void step4_viewCreditCards() {
        Assumptions.assumeTrue(foundAccountNumber != null, "No account found from search");

        Response resp = RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/accounts/" + foundAccountNumber + "/cards/credit");

        resp.then().statusCode(200);
        int count = resp.jsonPath().getList("$").size();
        System.out.println("  → Found " + count + " credit card(s)");
    }

    @Test
    @Order(5)
    @DisplayName("Step 5: User views debit cards")
    void step5_viewDebitCards() {
        Assumptions.assumeTrue(foundAccountNumber != null, "No account found from search");

        Response resp = RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/accounts/" + foundAccountNumber + "/cards/debit");

        resp.then().statusCode(200);
        int count = resp.jsonPath().getList("$").size();
        System.out.println("  → Found " + count + " debit card(s)");
    }

    @Test
    @Order(6)
    @DisplayName("Step 6: User validates a card number")
    void step6_validateCard() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("{\"cardNumber\": \"9999999999999999\"}")
                .post("/api/v1/cards/validate")
                .then()
                .statusCode(200)
                .body("valid", isA(Boolean.class));

        System.out.println("  → Card validation completed");
    }

    @Test
    @Order(7)
    @DisplayName("Step 7: Unauthenticated request is rejected")
    void step7_unauthenticatedRejected() {
        RestAssured.given()
                .baseUri(GATEWAY_URL)
                .contentType(ContentType.JSON)
                .body("{\"phoneNumber\": \"4161234567\"}")
                .post("/api/v1/search/phone")
                .then()
                .statusCode(anyOf(is(401), is(403)));
    }
}
