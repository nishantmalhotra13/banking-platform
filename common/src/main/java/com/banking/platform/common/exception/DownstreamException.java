package com.banking.platform.common.exception;

/**
 * Thrown when a downstream service call fails (timeout, 5xx, connection refused).
 * Maps to HTTP 502 Bad Gateway in the global exception handler.
 */
public class DownstreamException extends ServiceException {

    private final String downstream;

    public DownstreamException(String downstream, String message) {
        super("DOWNSTREAM_ERROR", downstream + ": " + message);
        this.downstream = downstream;
    }

    public DownstreamException(String downstream, String message, Throwable cause) {
        super("DOWNSTREAM_ERROR", downstream + ": " + message, cause);
        this.downstream = downstream;
    }

    public String getDownstream() {
        return downstream;
    }
}

