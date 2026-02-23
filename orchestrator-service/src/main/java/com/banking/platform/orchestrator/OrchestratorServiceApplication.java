package com.banking.platform.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Orchestrator Service.
 * <p>
 * Central orchestration layer that coordinates calls between MDM and CCSL services.
 * Uses a fluent {@code StepExecutor} pipeline pattern for composable request flows.
 * </p>
 */
@SpringBootApplication(scanBasePackages = {
        "com.banking.platform.orchestrator",
        "com.banking.platform.common"
})
public class OrchestratorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrchestratorServiceApplication.class, args);
    }
}

