package com.banking.platform.orchestrator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Full account summary: account details + all cards")
public record AccountSummaryResponse(
        String accountNumber,
        String productCode,
        String name,
        String phone,
        String email,
        String status,
        String branchCode,
        List<CardResponse> cards
) {}

