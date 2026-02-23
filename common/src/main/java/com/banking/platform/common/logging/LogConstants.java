package com.banking.platform.common.logging;

/**
 * Constants for MDC keys used across the banking platform.
 * Using consistent keys ensures structured log fields are uniform across all services,
 * making Kibana queries and dashboards reliable.
 */
public final class LogConstants {

    private LogConstants() {}

    /** Correlation ID propagated across service boundaries via HTTP header. */
    public static final String CORRELATION_ID = "correlationId";

    /** Spring application name injected from configuration. */
    public static final String SERVICE = "service";

    /** OpenTelemetry trace ID (auto-populated by Micrometer tracing bridge). */
    public static final String TRACE_ID = "traceId";

    /** OpenTelemetry span ID (auto-populated by Micrometer tracing bridge). */
    public static final String SPAN_ID = "spanId";
}

