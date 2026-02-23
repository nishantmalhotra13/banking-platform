package com.banking.platform.mdm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Search request containing the lookup value")
public record SearchRequest(

        @NotBlank(message = "Search value must not be blank")
        @Schema(description = "The value to search for", example = "4161234567")
        String value
) {}

