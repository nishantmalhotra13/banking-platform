package com.banking.platform.orchestrator.service;

import com.banking.platform.orchestrator.client.CcslClient;
import com.banking.platform.orchestrator.client.MdmClient;
import com.banking.platform.orchestrator.dto.*;
import com.banking.platform.orchestrator.pipeline.OrchestrationContext;
import com.banking.platform.orchestrator.pipeline.StepExecutor;
import com.banking.platform.orchestrator.steps.*;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Central orchestration service.
 * <p>
 * Each public method builds a dedicated pipeline via {@link StepExecutor} and executes it.
 * Adding a new API flow is as simple as writing a new method that composes existing steps.
 * </p>
 */
@Service
public class OrchestrationService {

    private final MeterRegistry meterRegistry;

    // Steps
    private final FetchAccountsStep fetchAccountsStep;
    private final FilterCreditCardProductsStep filterCreditCardProductsStep;
    private final FilterDebitCardProductsStep filterDebitCardProductsStep;
    private final FanOutAccountsStep fanOutAccountsStep;

    // Direct clients (for simple flows that don't need a step abstraction)
    private final MdmClient mdmClient;
    private final CcslClient ccslClient;

    public OrchestrationService(MeterRegistry meterRegistry,
                                FetchAccountsStep fetchAccountsStep,
                                FilterCreditCardProductsStep filterCreditCardProductsStep,
                                FilterDebitCardProductsStep filterDebitCardProductsStep,
                                FanOutAccountsStep fanOutAccountsStep,
                                MdmClient mdmClient,
                                CcslClient ccslClient) {
        this.meterRegistry = meterRegistry;
        this.fetchAccountsStep = fetchAccountsStep;
        this.filterCreditCardProductsStep = filterCreditCardProductsStep;
        this.filterDebitCardProductsStep = filterDebitCardProductsStep;
        this.fanOutAccountsStep = fanOutAccountsStep;
        this.mdmClient = mdmClient;
        this.ccslClient = ccslClient;
    }

    // ---------------------------------------------------------------
    // Search flows — each builds: FetchAccounts → FilterByType → FanOut
    // ---------------------------------------------------------------

    public List<FinalResponse> searchByPhone(String phoneNumber) throws Exception {
        return executeSearchPipeline("phone", phoneNumber, "credit");
    }

    public List<FinalResponse> searchByName(String fullName) throws Exception {
        return executeSearchPipeline("name", fullName, "credit");
    }

    public List<FinalResponse> searchByEmail(String email) throws Exception {
        return executeSearchPipeline("email", email, "credit");
    }

    // ---------------------------------------------------------------
    // Card-type-specific search flows
    // ---------------------------------------------------------------

    public List<FinalResponse> searchCreditCardsByPhone(String phone) throws Exception {
        return executeSearchPipeline("phone", phone, "credit");
    }

    public List<FinalResponse> searchDebitCardsByPhone(String phone) throws Exception {
        return executeSearchPipeline("phone", phone, "debit");
    }

    // ---------------------------------------------------------------
    // Direct account-level flows (no MDM search needed)
    // ---------------------------------------------------------------

    /** Tokenize + get all cards for a single known account number. */
    public List<CardResponse> getCardsByAccount(String accountNumber) {
        String token = ccslClient.tokenize(accountNumber);
        return ccslClient.getCards(token);
    }

    /** Get only credit cards for an account. */
    public List<CardResponse> getCreditCardsByAccount(String accountNumber) {
        String token = ccslClient.tokenize(accountNumber);
        return ccslClient.getCreditCards(token);
    }

    /** Get only debit cards for an account. */
    public List<CardResponse> getDebitCardsByAccount(String accountNumber) {
        String token = ccslClient.tokenize(accountNumber);
        return ccslClient.getDebitCards(token);
    }

    /** Full account summary: MDM account detail + all cards. */
    public AccountSummaryResponse getAccountSummary(String accountNumber) {
        MdmAccountResponse account = mdmClient.getAccount(accountNumber);
        String token = ccslClient.tokenize(accountNumber);
        List<CardResponse> cards = ccslClient.getCards(token);

        return new AccountSummaryResponse(
                account.accountNumber(), account.productCode(),
                account.name(), account.phone(), account.email(),
                account.status(), account.branchCode(), cards
        );
    }

    /** Validate a card number via CCSL. */
    public CardValidationResponse validateCard(String cardNumber) {
        try {
            CardResponse card = ccslClient.validateCard(cardNumber);
            return new CardValidationResponse(true, card);
        } catch (Exception ex) {
            return new CardValidationResponse(false, null);
        }
    }

    /** Downstream health check. */
    public HealthCheckResponse healthCheck() {
        Map<String, String> services = new LinkedHashMap<>();
        services.put("mdm-service", mdmClient.healthCheck());
        services.put("ccsl-service", ccslClient.healthCheck());

        boolean allUp = services.values().stream().noneMatch(s -> s.contains("DOWN"));
        return new HealthCheckResponse(allUp ? "UP" : "DEGRADED", services);
    }

    // ---------------------------------------------------------------
    // Private pipeline builder
    // ---------------------------------------------------------------

    private List<FinalResponse> executeSearchPipeline(String type, String value, String cardCategory) throws Exception {
        OrchestrationContext context = new OrchestrationContext();
        context.setSearchType(type);
        context.setSearchValue(value);

        var filterStep = "debit".equals(cardCategory)
                ? filterDebitCardProductsStep
                : filterCreditCardProductsStep;

        StepExecutor.create(context, meterRegistry)
                .then(fetchAccountsStep)
                .then(filterStep)
                .thenIf(ctx -> ctx.getFilteredAccounts() != null && !ctx.getFilteredAccounts().isEmpty(),
                        fanOutAccountsStep)
                .execute();

        return context.getFinalResponses() != null ? context.getFinalResponses() : List.of();
    }
}

