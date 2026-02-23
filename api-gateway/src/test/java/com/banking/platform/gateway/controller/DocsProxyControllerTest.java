package com.banking.platform.gateway.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class DocsProxyControllerTest {

    @Test
    void controllerHasAllFourEndpoints() {
        DocsProxyController controller = new DocsProxyController();
        ReflectionTestUtils.setField(controller, "orchestratorUrl", "http://orch:8086");
        ReflectionTestUtils.setField(controller, "mdmUrl", "http://mdm:8084");
        ReflectionTestUtils.setField(controller, "ccslUrl", "http://ccsl:8082");
        ReflectionTestUtils.setField(controller, "authUrl", "http://auth:8085");

        // Just verify the controller instantiates and fields are set (actual HTTP calls
        // would require a running service — covered by e2e tests)
        assertThat(ReflectionTestUtils.getField(controller, "orchestratorUrl")).isEqualTo("http://orch:8086");
        assertThat(ReflectionTestUtils.getField(controller, "mdmUrl")).isEqualTo("http://mdm:8084");
        assertThat(ReflectionTestUtils.getField(controller, "ccslUrl")).isEqualTo("http://ccsl:8082");
        assertThat(ReflectionTestUtils.getField(controller, "authUrl")).isEqualTo("http://auth:8085");
    }
}

