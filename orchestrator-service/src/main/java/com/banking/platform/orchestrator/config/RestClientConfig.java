package com.banking.platform.orchestrator.config;

import com.banking.platform.common.filter.CorrelationIdFilter;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

/**
 * Creates per-downstream RestClient beans with base URLs, timeouts,
 * and automatic X-Correlation-Id propagation.
 */
@Configuration
public class RestClientConfig {

    @Value("${downstream.mdm.base-url}")
    private String mdmBaseUrl;

    @Value("${downstream.ccsl.base-url}")
    private String ccslBaseUrl;

    @Bean("mdmRestClient")
    public RestClient mdmRestClient(RestClient.Builder builder) {
        return buildClient(builder, mdmBaseUrl);
    }

    @Bean("ccslRestClient")
    public RestClient ccslRestClient(RestClient.Builder builder) {
        return buildClient(builder, ccslBaseUrl);
    }

    private RestClient buildClient(RestClient.Builder builder, String baseUrl) {
        // JDK HttpClient with connect + read timeouts
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();

        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(Duration.ofSeconds(10));

        return builder
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .requestInterceptor(correlationIdInterceptor())
                .build();
    }

    /**
     * Interceptor that propagates the correlation ID from MDC to outgoing requests
     * so downstream services can continue the trace.
     */
    private ClientHttpRequestInterceptor correlationIdInterceptor() {
        return (request, body, execution) -> {
            String correlationId = MDC.get(CorrelationIdFilter.MDC_KEY);
            if (correlationId != null) {
                request.getHeaders().set(CorrelationIdFilter.HEADER, correlationId);
            }
            return execution.execute(request, body);
        };
    }
}

