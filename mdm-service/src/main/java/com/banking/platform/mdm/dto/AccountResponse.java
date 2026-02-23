package com.banking.platform.mdm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO for account search results.
 * <p>
 * Intentionally excludes internal fields like {@code id} to prevent leaking
 * database internals to consumers.
 * </p>
 */
@Schema(description = "Account information returned by MDM search endpoints")
public record AccountResponse(

        @Schema(description = "Unique account number", example = "ACC1001")
        String accountNumber,

        @Schema(description = "Product code identifying the account type", example = "CC01")
        String productCode,

        @Schema(description = "Customer name", example = "John Doe")
        String name,

        @Schema(description = "Customer phone number", example = "4161234567")
        String phone,

        @Schema(description = "Customer email", example = "john@example.com")
        String email,

        @Schema(description = "Account status", example = "ACTIVE")
        String status,

        @Schema(description = "Branch code", example = "BR001")
        String branchCode
) {}

