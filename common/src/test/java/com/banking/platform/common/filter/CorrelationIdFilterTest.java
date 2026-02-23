package com.banking.platform.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CorrelationIdFilterTest {

    private final CorrelationIdFilter filter = new CorrelationIdFilter();

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void usesExistingCorrelationIdFromHeader() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("X-Correlation-Id")).thenReturn("test-id-123");

        filter.doFilterInternal(request, response, chain);

        verify(response).setHeader("X-Correlation-Id", "test-id-123");
        verify(chain).doFilter(request, response);
    }

    @Test
    void generatesCorrelationIdWhenHeaderMissing() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("X-Correlation-Id")).thenReturn(null);

        filter.doFilterInternal(request, response, chain);

        verify(response).setHeader(eq("X-Correlation-Id"), argThat(id -> id != null && !id.isBlank()));
        verify(chain).doFilter(request, response);
    }

    @Test
    void generatesCorrelationIdWhenHeaderBlank() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("X-Correlation-Id")).thenReturn("   ");

        filter.doFilterInternal(request, response, chain);

        verify(response).setHeader(eq("X-Correlation-Id"), argThat(id -> !id.isBlank()));
    }

    @Test
    void clearsMdcAfterFilter() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("X-Correlation-Id")).thenReturn("my-id");

        filter.doFilterInternal(request, response, chain);

        assertThat(MDC.get("correlationId")).isNull();
    }

    @Test
    void clearsMdcEvenOnException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("X-Correlation-Id")).thenReturn("my-id");
        doThrow(new RuntimeException("boom")).when(chain).doFilter(request, response);

        try {
            filter.doFilterInternal(request, response, chain);
        } catch (RuntimeException ignored) {}

        assertThat(MDC.get("correlationId")).isNull();
    }
}

