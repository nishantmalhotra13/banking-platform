package com.banking.platform.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Servlet filter that extracts or generates a correlation ID for every inbound HTTP request.
 * <p>
 * The correlation ID is read from the {@code X-Correlation-Id} header.  If the header is absent
 * a new UUID is generated.  The value is placed into the SLF4J MDC so that every log statement
 * emitted while processing the request automatically includes the correlation ID.  The ID is
 * also echoed back to the caller via the same response header.
 * </p>
 *
 * @author Banking Platform Team
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationIdFilter extends OncePerRequestFilter {

    /** HTTP header name used to propagate the correlation ID across services. */
    public static final String HEADER = "X-Correlation-Id";

    /** MDC key used for structured logging output. */
    public static final String MDC_KEY = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId = request.getHeader(HEADER);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put(MDC_KEY, correlationId);
        response.setHeader(HEADER, correlationId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}

