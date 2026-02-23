package com.banking.platform.common.error;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    void factoryMethodCreatesResponseWithCurrentTimestamp() {
        Instant before = Instant.now();
        ErrorResponse response = ErrorResponse.of(404, "NOT_FOUND", "Resource not found", "corr-123");
        Instant after = Instant.now();

        assertThat(response.status()).isEqualTo(404);
        assertThat(response.code()).isEqualTo("NOT_FOUND");
        assertThat(response.message()).isEqualTo("Resource not found");
        assertThat(response.correlationId()).isEqualTo("corr-123");
        assertThat(response.timestamp()).isBetween(before, after);
    }

    @Test
    void factoryMethodHandlesNullCorrelationId() {
        ErrorResponse response = ErrorResponse.of(500, "ERROR", "Something failed", null);
        assertThat(response.correlationId()).isNull();
    }
}

