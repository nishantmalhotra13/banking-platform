package com.banking.platform.ccsl.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to validate a card number")
public record CardValidateRequest(

        @NotBlank(message = "Card number must not be blank")
        @Schema(description = "The card number to validate", example = "4111111111111111")
        String cardNumber
) {}

