package com.banking.platform.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Developer portal — returns all useful URLs for the banking platform.
 */
@RestController
public class PortalController {

    @Value("${portal.ui-url:http://localhost:4200}")
    private String uiUrl;

    @Value("${portal.gateway-url:http://localhost:8080}")
    private String gatewayUrl;

    @Value("${portal.orchestrator-url:http://localhost:8086}")
    private String orchestratorUrl;

    @Value("${portal.mdm-url:http://localhost:8084}")
    private String mdmUrl;

    @Value("${portal.ccsl-url:http://localhost:8082}")
    private String ccslUrl;

    @Value("${portal.auth-url:http://localhost:8085}")
    private String authUrl;

    @Value("${portal.kibana-url:http://localhost:5601}")
    private String kibanaUrl;

    @GetMapping("/api/v1/portal/links")
    public Map<String, String> links() {
        Map<String, String> links = new LinkedHashMap<>();
        links.put("ui", uiUrl);
        links.put("gateway_swagger", gatewayUrl + "/swagger-ui.html");
        links.put("orchestrator_swagger", orchestratorUrl + "/swagger-ui.html");
        links.put("mdm_swagger", mdmUrl + "/swagger-ui.html");
        links.put("ccsl_swagger", ccslUrl + "/swagger-ui.html");
        links.put("auth_swagger", authUrl + "/swagger-ui.html");
        links.put("kibana", kibanaUrl);
        links.put("health", gatewayUrl + "/actuator/health");
        links.put("prometheus", gatewayUrl + "/actuator/prometheus");
        return links;
    }
}

