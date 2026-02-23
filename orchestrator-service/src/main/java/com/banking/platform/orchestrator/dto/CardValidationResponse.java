package com.banking.platform.orchestrator.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Card validation result")
public record CardValidationResponse(
        boolean valid,
        CardResponse card
) {}

