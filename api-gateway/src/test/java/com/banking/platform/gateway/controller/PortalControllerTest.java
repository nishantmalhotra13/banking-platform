package com.banking.platform.gateway.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PortalControllerTest {

    @Test
    void links_returnsAllExpectedKeys() {
        PortalController controller = new PortalController();
        ReflectionTestUtils.setField(controller, "uiUrl", "http://localhost:4200");
        ReflectionTestUtils.setField(controller, "gatewayUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(controller, "orchestratorUrl", "http://localhost:8086");
        ReflectionTestUtils.setField(controller, "mdmUrl", "http://localhost:8084");
        ReflectionTestUtils.setField(controller, "ccslUrl", "http://localhost:8082");
        ReflectionTestUtils.setField(controller, "authUrl", "http://localhost:8085");
        ReflectionTestUtils.setField(controller, "kibanaUrl", "http://localhost:5601");

        Map<String, String> links = controller.links();

        assertThat(links).containsKeys(
                "ui", "gateway_swagger", "orchestrator_swagger",
                "mdm_swagger", "ccsl_swagger", "auth_swagger",
                "kibana", "health", "prometheus");
    }

    @Test
    void links_containsCorrectSwaggerUrls() {
        PortalController controller = new PortalController();
        ReflectionTestUtils.setField(controller, "uiUrl", "http://ui:4200");
        ReflectionTestUtils.setField(controller, "gatewayUrl", "http://gw:8080");
        ReflectionTestUtils.setField(controller, "orchestratorUrl", "http://orch:8086");
        ReflectionTestUtils.setField(controller, "mdmUrl", "http://mdm:8084");
        ReflectionTestUtils.setField(controller, "ccslUrl", "http://ccsl:8082");
        ReflectionTestUtils.setField(controller, "authUrl", "http://auth:8085");
        ReflectionTestUtils.setField(controller, "kibanaUrl", "http://kibana:5601");

        Map<String, String> links = controller.links();

        assertThat(links.get("gateway_swagger")).isEqualTo("http://gw:8080/swagger-ui.html");
        assertThat(links.get("health")).isEqualTo("http://gw:8080/actuator/health");
        assertThat(links.get("kibana")).isEqualTo("http://kibana:5601");
    }

    @Test
    void links_returnsNineEntries() {
        PortalController controller = new PortalController();
        ReflectionTestUtils.setField(controller, "uiUrl", "a");
        ReflectionTestUtils.setField(controller, "gatewayUrl", "b");
        ReflectionTestUtils.setField(controller, "orchestratorUrl", "c");
        ReflectionTestUtils.setField(controller, "mdmUrl", "d");
        ReflectionTestUtils.setField(controller, "ccslUrl", "e");
        ReflectionTestUtils.setField(controller, "authUrl", "f");
        ReflectionTestUtils.setField(controller, "kibanaUrl", "g");

        assertThat(controller.links()).hasSize(9);
    }
}

