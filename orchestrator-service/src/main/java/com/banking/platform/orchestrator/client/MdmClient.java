package com.banking.platform.orchestrator.client;

import com.banking.platform.common.exception.DownstreamException;
import com.banking.platform.orchestrator.dto.MdmAccountResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * HTTP client for the MDM service.
 * Uses a dedicated RestClient bean with base URL and timeouts pre-configured.
 */
@Component
public class MdmClient {

    private static final Logger log = LoggerFactory.getLogger(MdmClient.class);

    private final RestClient restClient;

    public MdmClient(@Qualifier("mdmRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    /** Search MDM accounts by type (phone, name, email). */
    public List<MdmAccountResponse> search(String type, String value) {
        String uri = switch (type) {
            case "phone" -> "/api/v1/search/phone";
            case "name"  -> "/api/v1/search/name";
            case "email" -> "/api/v1/search/email";
            default -> throw new IllegalArgumentException("Invalid search type: " + type);
        };

        try {
            log.info("Calling MDM: {} with value={}", uri, value);
            return restClient.post()
                    .uri(uri)
                    .body(new SearchRequest(value))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException ex) {
            throw new DownstreamException("MDM", "Search failed for " + type + "=" + value, ex);
        }
    }

    /** Get a single account by account number. */
    public MdmAccountResponse getAccount(String accountNumber) {
        try {
            return restClient.get()
                    .uri("/api/v1/accounts/{accountNumber}", accountNumber)
                    .retrieve()
                    .body(MdmAccountResponse.class);
        } catch (RestClientException ex) {
            throw new DownstreamException("MDM", "Failed to fetch account " + accountNumber, ex);
        }
    }

    /** Ping MDM actuator health endpoint. */
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

    record SearchRequest(String value) {}
}

