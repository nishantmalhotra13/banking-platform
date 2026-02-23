package com.banking.platform.orchestrator.pipeline;

import com.banking.platform.orchestrator.dto.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Shared mutable state carried through all steps of an orchestration pipeline.
 * <p>
 * Each pipeline creates a fresh context.  Steps read inputs they need and write
 * outputs for downstream steps.  The {@code attributes} map provides a type-safe
 * extension mechanism for ad-hoc data that doesn't warrant a dedicated field.
 * </p>
 */
@Getter
@Setter
public class OrchestrationContext {

    // --- inputs (set by the controller before pipeline execution) ---

    private String searchType;
    private String searchValue;
    private String accountNumber;
    private String cardNumber;

    // --- intermediate results (set by steps) ---

    private List<MdmAccountResponse> accounts;
    private List<MdmAccountResponse> filteredAccounts;

    // --- final output ---

    private List<FinalResponse> finalResponses;
    private AccountSummaryResponse accountSummary;
    private CardValidationResponse cardValidation;
    private HealthCheckResponse healthCheck;

    // --- generic bag for extensibility ---

    private final Map<String, Object> attributes = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }
}

