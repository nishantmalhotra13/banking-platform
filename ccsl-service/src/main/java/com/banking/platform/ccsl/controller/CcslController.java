package com.banking.platform.ccsl.controller;

import com.banking.platform.ccsl.dto.*;
import com.banking.platform.ccsl.service.CcslService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CCSL card tokenization, lookup, and validation endpoints.
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "CCSL Cards", description = "Card tokenization, lookup & validation")
public class CcslController {

    private final CcslService service;

    public CcslController(CcslService service) {
        this.service = service;
    }

    @PostMapping("/tokenize")
    @Operation(summary = "Tokenize an account number")
    public TokenResponse tokenize(@Valid @RequestBody TokenRequest request) {
        return new TokenResponse(service.tokenize(request.accountNumber()));
    }

    @GetMapping("/cards/{token}")
    @Operation(summary = "Get all cards (credit + debit) by token")
    public List<CardResponse> getCards(@PathVariable String token) {
        return service.getCards(token);
    }

    @GetMapping("/cards/{token}/credit")
    @Operation(summary = "Get only credit cards by token")
    public List<CardResponse> getCreditCards(@PathVariable String token) {
        return service.getCreditCards(token);
    }

    @GetMapping("/cards/{token}/debit")
    @Operation(summary = "Get only debit cards by token")
    public List<CardResponse> getDebitCards(@PathVariable String token) {
        return service.getDebitCards(token);
    }

    @GetMapping("/cards/account/{accountNumber}")
    @Operation(summary = "Get all cards directly by account number (no token)")
    public List<CardResponse> getCardsByAccount(@PathVariable String accountNumber) {
        return service.getCardsByAccountNumber(accountNumber);
    }

    @PostMapping("/cards/validate")
    @Operation(summary = "Validate whether a card number exists")
    public CardResponse validateCard(@Valid @RequestBody CardValidateRequest request) {
        return service.validateCard(request.cardNumber());
    }
}

