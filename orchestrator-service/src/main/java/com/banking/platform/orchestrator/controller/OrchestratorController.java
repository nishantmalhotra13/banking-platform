package com.banking.platform.orchestrator.controller;

import com.banking.platform.orchestrator.dto.*;
import com.banking.platform.orchestrator.service.OrchestrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for the Orchestrator service.
 * Each endpoint builds its own step pipeline via {@link OrchestrationService}.
 */
@RestController
@RequestMapping("/api/v1")
public class OrchestratorController {

    private final OrchestrationService orchestrationService;

    public OrchestratorController(OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    // ---------------------------------------------------------------
    // Search endpoints
    // ---------------------------------------------------------------

    @Tag(name = "Search", description = "Search accounts and retrieve associated cards")
    @PostMapping("/search/phone")
    @Operation(summary = "Search by phone number — returns credit card accounts with cards")
    public List<FinalResponse> searchByPhone(@Valid @RequestBody PhoneSearchRequest request) throws Exception {
        return orchestrationService.searchByPhone(request.phoneNumber());
    }

    @Tag(name = "Search")
    @PostMapping("/search/name")
    @Operation(summary = "Search by name — returns credit card accounts with cards")
    public List<FinalResponse> searchByName(@Valid @RequestBody NameSearchRequest request) throws Exception {
        return orchestrationService.searchByName(request.fullName());
    }

    @Tag(name = "Search")
    @PostMapping("/search/email")
    @Operation(summary = "Search by email — returns credit card accounts with cards")
    public List<FinalResponse> searchByEmail(@Valid @RequestBody EmailSearchRequest request) throws Exception {
        return orchestrationService.searchByEmail(request.email());
    }

    // ---------------------------------------------------------------
    // Account card endpoints
    // ---------------------------------------------------------------

    @Tag(name = "Accounts", description = "Account-level card operations")
    @GetMapping("/accounts/{accountNumber}/cards")
    @Operation(summary = "Get all cards for an account")
    public List<CardResponse> getAllCards(@PathVariable String accountNumber) {
        return orchestrationService.getCardsByAccount(accountNumber);
    }

    @Tag(name = "Accounts")
    @GetMapping("/accounts/{accountNumber}/cards/credit")
    @Operation(summary = "Get credit cards only for an account")
    public List<CardResponse> getCreditCards(@PathVariable String accountNumber) {
        return orchestrationService.getCreditCardsByAccount(accountNumber);
    }

    @Tag(name = "Accounts")
    @GetMapping("/accounts/{accountNumber}/cards/debit")
    @Operation(summary = "Get debit cards only for an account")
    public List<CardResponse> getDebitCards(@PathVariable String accountNumber) {
        return orchestrationService.getDebitCardsByAccount(accountNumber);
    }

    @Tag(name = "Accounts")
    @GetMapping("/accounts/{accountNumber}/summary")
    @Operation(summary = "Get full account summary — details + all cards")
    public AccountSummaryResponse getAccountSummary(@PathVariable String accountNumber) {
        return orchestrationService.getAccountSummary(accountNumber);
    }

    // ---------------------------------------------------------------
    // Card validation
    // ---------------------------------------------------------------

    @Tag(name = "Cards", description = "Card validation")
    @PostMapping("/cards/validate")
    @Operation(summary = "Validate whether a card number exists")
    public CardValidationResponse validateCard(@Valid @RequestBody CardValidateRequest request) {
        return orchestrationService.validateCard(request.cardNumber());
    }

    // ---------------------------------------------------------------
    // Health
    // ---------------------------------------------------------------

    @Tag(name = "Health", description = "Downstream health check")
    @GetMapping("/health/downstream")
    @Operation(summary = "Check health of MDM and CCSL services")
    public HealthCheckResponse healthCheck() {
        return orchestrationService.healthCheck();
    }

    // ---------------------------------------------------------------
    // Request DTOs
    // ---------------------------------------------------------------

    public record PhoneSearchRequest(
            @NotBlank(message = "Phone number is required") String phoneNumber) {}

    public record NameSearchRequest(
            @NotBlank(message = "Full name is required") String fullName) {}

    public record EmailSearchRequest(
            @NotBlank(message = "Email is required") String email) {}

    public record CardValidateRequest(
            @NotBlank(message = "Card number is required") String cardNumber) {}
}

