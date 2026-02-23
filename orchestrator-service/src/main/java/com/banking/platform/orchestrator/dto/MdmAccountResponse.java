package com.banking.platform.orchestrator.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Account returned by MDM search")
public record MdmAccountResponse(
        @Schema(example = "ACC1001") String accountNumber,
        @Schema(example = "CC01") String productCode,
        @Schema(example = "John Doe") String name,
        @Schema(example = "4161234567") String phone,
        @Schema(example = "john.doe@example.com") String email,
        @Schema(example = "ACTIVE") String status,
        @Schema(example = "BR001") String branchCode
) {}

