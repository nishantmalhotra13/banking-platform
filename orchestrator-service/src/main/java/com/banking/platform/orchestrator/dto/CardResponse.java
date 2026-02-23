package com.banking.platform.orchestrator.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Card information from CCSL")
public record CardResponse(
        @Schema(example = "4111111111111111") String cardNumber,
        @Schema(example = "VISA") String cardType,
        @Schema(example = "CREDIT") String cardCategory,
        @Schema(example = "ACTIVE") String cardStatus,
        @Schema(example = "Royal Bank") String issuerBank
) {}

