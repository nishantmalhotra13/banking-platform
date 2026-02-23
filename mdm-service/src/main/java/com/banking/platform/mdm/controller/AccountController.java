package com.banking.platform.mdm.controller;

import com.banking.platform.mdm.dto.AccountResponse;
import com.banking.platform.mdm.dto.SearchRequest;
import com.banking.platform.mdm.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller exposing MDM account search and lookup endpoints.
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "MDM Accounts", description = "Master data management — account search & lookup")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/search/phone")
    @Operation(summary = "Search accounts by phone number")
    public List<AccountResponse> searchByPhone(@Valid @RequestBody SearchRequest request) {
        return service.searchByPhone(request.value());
    }

    @PostMapping("/search/name")
    @Operation(summary = "Search accounts by customer name (partial match)")
    public List<AccountResponse> searchByName(@Valid @RequestBody SearchRequest request) {
        return service.searchByName(request.value());
    }

    @PostMapping("/search/email")
    @Operation(summary = "Search accounts by email address")
    public List<AccountResponse> searchByEmail(@Valid @RequestBody SearchRequest request) {
        return service.searchByEmail(request.value());
    }

    @GetMapping("/accounts/{accountNumber}")
    @Operation(summary = "Get account details by account number")
    public AccountResponse getAccount(@PathVariable String accountNumber) {
        return service.getByAccountNumber(accountNumber);
    }

    @GetMapping("/accounts/{accountNumber}/exists")
    @Operation(summary = "Check whether an account number exists")
    public Map<String, Boolean> accountExists(@PathVariable String accountNumber) {
        return Map.of("exists", service.existsByAccountNumber(accountNumber));
    }
}

