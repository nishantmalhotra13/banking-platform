package com.banking.platform.ccsl.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to tokenize an account number")
public record TokenRequest(

        @NotBlank(message = "Account number must not be blank")
        @Schema(description = "The account number to tokenize", example = "ACC1001")
        String accountNumber
) {}

