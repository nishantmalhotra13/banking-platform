package com.banking.platform.common.exception;

/**
 * Thrown when a business-level validation rule is violated (e.g., invalid product code, duplicate entry).
 * Maps to HTTP 422 Unprocessable Entity in the global exception handler.
 */
public class ValidationException extends ServiceException {

    public ValidationException(String message) {
        super("VALIDATION_ERROR", message);
    }
}

