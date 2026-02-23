package com.banking.platform.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

/**
 * Server-side proxy for downstream OpenAPI docs.
 * <p>
 * Swagger UI runs in the browser at {@code :8080} and needs to fetch api-docs for each
 * service.  Direct cross-origin requests fail with CORS errors.  Gateway routes with
 * {@code StripPrefix} don't work because the gateway resolves {@code /v3/api-docs}
 * against its own springdoc endpoint.
 * <p>
 * This controller explicitly fetches api-docs from each downstream service via RestClient
 * and returns the JSON to the browser — all same-origin, no CORS, no routing conflicts.
 */
@RestController
public class DocsProxyController {

    private final RestClient restClient;

    @Value("${services.orchestrator-url}")
    private String orchestratorUrl;

    @Value("${services.mdm-url}")
    private String mdmUrl;

    @Value("${services.ccsl-url}")
    private String ccslUrl;

    @Value("${services.auth-url}")
    private String authUrl;

    public DocsProxyController() {
        this.restClient = RestClient.create();
    }

    @GetMapping(value = "/services/orchestrator/v3/api-docs", produces = MediaType.APPLICATION_JSON_VALUE)
    public String orchestratorDocs() {
        return fetchDocs(orchestratorUrl);
    }

    @GetMapping(value = "/services/mdm/v3/api-docs", produces = MediaType.APPLICATION_JSON_VALUE)
    public String mdmDocs() {
        return fetchDocs(mdmUrl);
    }

    @GetMapping(value = "/services/ccsl/v3/api-docs", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ccslDocs() {
        return fetchDocs(ccslUrl);
    }

    @GetMapping(value = "/services/auth/v3/api-docs", produces = MediaType.APPLICATION_JSON_VALUE)
    public String authDocs() {
        return fetchDocs(authUrl);
    }

    private String fetchDocs(String baseUrl) {
        return restClient.get()
                .uri(baseUrl + "/v3/api-docs")
                .retrieve()
                .body(String.class);
    }
}

