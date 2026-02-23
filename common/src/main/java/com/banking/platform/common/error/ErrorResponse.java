package com.banking.platform.common.error;

import java.time.Instant;

/**
 * Standard error response envelope returned by every service in the banking platform.
 * Consumers can rely on this shape for consistent error handling across all APIs.
 *
 * @param timestamp     ISO-8601 timestamp of when the error occurred
 * @param status        HTTP status code
 * @param code          Machine-readable error code (e.g., {@code NOT_FOUND}, {@code VALIDATION_ERROR})
 * @param message       Human-readable error description
 * @param correlationId The correlation ID from the request for tracing in logs / Kibana
 */
public record ErrorResponse(
        Instant timestamp,
        int status,
        String code,
        String message,
        String correlationId
) {
    public static ErrorResponse of(int status, String code, String message, String correlationId) {
        return new ErrorResponse(Instant.now(), status, code, message, correlationId);
    }
}

