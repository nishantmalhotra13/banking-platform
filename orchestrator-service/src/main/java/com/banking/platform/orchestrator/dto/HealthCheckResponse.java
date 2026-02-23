package com.banking.platform.orchestrator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "Downstream service health check")
public record HealthCheckResponse(
        @Schema(example = "UP") String overallStatus,
        Map<String, String> services
) {}

