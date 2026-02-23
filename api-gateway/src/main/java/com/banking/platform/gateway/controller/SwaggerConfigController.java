package com.banking.platform.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Custom swagger-config endpoint that returns correct, un-prefixed URLs.
 * <p>
 * Spring Cloud Gateway MVC rewrites the default springdoc swagger-config URLs,
 * prepending route prefixes. This controller bypasses that by returning a
 * static, correct config.
 * </p>
 */
@RestController
public class SwaggerConfigController {

    @GetMapping("/api/v1/swagger-config")
    public Map<String, Object> swaggerConfig() {
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("urls", List.of(
                Map.of("name", "Orchestrator", "url", "/services/orchestrator/v3/api-docs"),
                Map.of("name", "MDM", "url", "/services/mdm/v3/api-docs"),
                Map.of("name", "CCSL", "url", "/services/ccsl/v3/api-docs"),
                Map.of("name", "Auth", "url", "/services/auth/v3/api-docs")
        ));
        config.put("validatorUrl", "");
        return config;
    }
}

