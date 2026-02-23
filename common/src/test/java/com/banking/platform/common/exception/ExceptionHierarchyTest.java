package com.banking.platform.common.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHierarchyTest {

    @Test
    void serviceExceptionHasCodeAndMessage() {
        ServiceException ex = new ServiceException("MY_CODE", "something failed");
        assertThat(ex.getCode()).isEqualTo("MY_CODE");
        assertThat(ex.getMessage()).isEqualTo("something failed");
    }

    @Test
    void serviceExceptionWithCause() {
        RuntimeException cause = new RuntimeException("root cause");
        ServiceException ex = new ServiceException("ERR", "wrapper", cause);
        assertThat(ex.getCause()).isEqualTo(cause);
    }

    @Test
    void resourceNotFoundExceptionWithMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("not here");
        assertThat(ex.getCode()).isEqualTo("NOT_FOUND");
        assertThat(ex.getMessage()).isEqualTo("not here");
    }

    @Test
    void resourceNotFoundExceptionWithResourceAndIdentifier() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Account", "ACC123");
        assertThat(ex.getCode()).isEqualTo("NOT_FOUND");
        assertThat(ex.getMessage()).isEqualTo("Account not found: ACC123");
    }

    @Test
    void validationExceptionHasValidationErrorCode() {
        ValidationException ex = new ValidationException("invalid input");
        assertThat(ex.getCode()).isEqualTo("VALIDATION_ERROR");
        assertThat(ex.getMessage()).isEqualTo("invalid input");
    }

    @Test
    void downstreamExceptionContainsServiceName() {
        DownstreamException ex = new DownstreamException("MDM", "timeout");
        assertThat(ex.getCode()).isEqualTo("DOWNSTREAM_ERROR");
        assertThat(ex.getDownstream()).isEqualTo("MDM");
        assertThat(ex.getMessage()).contains("MDM").contains("timeout");
    }

    @Test
    void downstreamExceptionWithCause() {
        RuntimeException cause = new RuntimeException("conn refused");
        DownstreamException ex = new DownstreamException("CCSL", "failed", cause);
        assertThat(ex.getDownstream()).isEqualTo("CCSL");
        assertThat(ex.getCause()).isEqualTo(cause);
    }
}

