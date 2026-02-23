package com.banking.platform.ccsl.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response containing the generated token")
public record TokenResponse(

        @Schema(description = "Opaque token mapped to the account number", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        String token
) {}

