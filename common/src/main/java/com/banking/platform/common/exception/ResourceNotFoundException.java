package com.banking.platform.common.exception;

/**
 * Thrown when a requested entity (account, card, token, user, etc.) cannot be found.
 * Maps to HTTP 404 in the global exception handler.
 */
public class ResourceNotFoundException extends ServiceException {

    public ResourceNotFoundException(String message) {
        super("NOT_FOUND", message);
    }

    public ResourceNotFoundException(String resource, String identifier) {
        super("NOT_FOUND", resource + " not found: " + identifier);
    }
}

