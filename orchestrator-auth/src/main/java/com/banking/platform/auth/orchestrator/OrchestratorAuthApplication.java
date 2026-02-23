package com.banking.platform.auth.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Orchestrator Auth Server — user-facing authentication with RBAC.
 * Issues JWTs containing user roles (ADMIN, USER).
 */
@SpringBootApplication(scanBasePackages = {
        "com.banking.platform.auth.orchestrator",
        "com.banking.platform.common"
})
public class OrchestratorAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrchestratorAuthApplication.class, args);
    }
}

