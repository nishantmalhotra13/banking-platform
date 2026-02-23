package com.banking.platform.orchestrator.client;

import com.banking.platform.common.exception.DownstreamException;
import com.banking.platform.orchestrator.dto.CardResponse;
import com.banking.platform.orchestrator.dto.CardValidationResponse;
import com.banking.platform.orchestrator.dto.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * HTTP client for the CCSL service.
 */
@Component
public class CcslClient {

    private static final Logger log = LoggerFactory.getLogger(CcslClient.class);

    private final RestClient restClient;

    public CcslClient(@Qualifier("ccslRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    /** Tokenize an account number — returns the opaque token. */
    public String tokenize(String accountNumber) {
        try {
            log.info("Tokenizing account via CCSL: {}", accountNumber);
            TokenResponse response = restClient.post()
                    .uri("/api/v1/tokenize")
                    .body(new TokenRequest(accountNumber))
                    .retrieve()
                    .body(TokenResponse.class);
            return response != null ? response.token() : null;
        } catch (RestClientException ex) {
            throw new DownstreamException("CCSL", "Tokenize failed for " + accountNumber, ex);
        }
    }

    /** Get all cards by token. */
    public List<CardResponse> getCards(String token) {
        try {
            return restClient.get()
                    .uri("/api/v1/cards/{token}", token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException ex) {
            throw new DownstreamException("CCSL", "Get cards failed for token " + token, ex);
        }
    }

    /** Get credit cards only. */
    public List<CardResponse> getCreditCards(String token) {
        try {
            return restClient.get()
                    .uri("/api/v1/cards/{token}/credit", token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException ex) {
            throw new DownstreamException("CCSL", "Get credit cards failed", ex);
        }
    }

    /** Get debit cards only. */
    public List<CardResponse> getDebitCards(String token) {
        try {
            return restClient.get()
                    .uri("/api/v1/cards/{token}/debit", token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException ex) {
            throw new DownstreamException("CCSL", "Get debit cards failed", ex);
        }
    }

    /** Validate a card number. */
    public CardResponse validateCard(String cardNumber) {
        try {
            return restClient.post()
                    .uri("/api/v1/cards/validate")
                    .body(new CardValidateRequest(cardNumber))
                    .retrieve()
                    .body(CardResponse.class);
        } catch (RestClientException ex) {
            throw new DownstreamException("CCSL", "Card validation failed for " + cardNumber, ex);
        }
    }

    /** Ping CCSL actuator health. */
    public String healthCheck() {
        try {
            return restClient.get()
                    .uri("/actuator/health")
                    .retrieve()
                    .body(String.class);
        } catch (Exception ex) {
            return "DOWN: " + ex.getMessage();
        }
    }

    record TokenRequest(String accountNumber) {}
    record CardValidateRequest(String cardNumber) {}
}

