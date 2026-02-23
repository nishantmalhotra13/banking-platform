package com.banking.platform.ccsl.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Card information returned by CCSL")
public record CardResponse(

        @Schema(description = "Card number", example = "4111111111111111")
        String cardNumber,

        @Schema(description = "Card type (VISA, MASTERCARD, AMEX, INTERAC)", example = "VISA")
        String cardType,

        @Schema(description = "Card category (CREDIT or DEBIT)", example = "CREDIT")
        String cardCategory,

        @Schema(description = "Card status (ACTIVE, BLOCKED, EXPIRED)", example = "ACTIVE")
        String cardStatus,

        @Schema(description = "Issuing bank name", example = "Royal Bank")
        String issuerBank
) {}

