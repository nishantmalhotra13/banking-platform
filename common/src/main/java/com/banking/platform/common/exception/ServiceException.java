package com.banking.platform.common.exception;

/**
 * Base exception for all business-level errors in the banking platform.
 * Subclasses map to specific HTTP status codes via {@link com.banking.platform.common.error.GlobalExceptionHandler}.
 */
public class ServiceException extends RuntimeException {

    private final String code;

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

