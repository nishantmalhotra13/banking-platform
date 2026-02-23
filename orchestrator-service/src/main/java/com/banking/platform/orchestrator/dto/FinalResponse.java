package com.banking.platform.orchestrator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Aggregated result: account + its cards")
public record FinalResponse(
        @Schema(example = "ACC1001") String accountNumber,
        @Schema(example = "CC01") String productCode,
        List<CardResponse> cards
) {}

