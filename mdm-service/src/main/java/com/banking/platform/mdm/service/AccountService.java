package com.banking.platform.mdm.service;

import com.banking.platform.common.exception.ResourceNotFoundException;
import com.banking.platform.mdm.domain.Account;
import com.banking.platform.mdm.dto.AccountResponse;
import com.banking.platform.mdm.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business logic layer for MDM account operations.
 * Converts JPA entities to DTOs to ensure internal database structure is never exposed.
 */
@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    /** Search accounts by phone number. */
    public List<AccountResponse> searchByPhone(String phone) {
        log.info("Searching accounts by phone: {}", phone);
        return repository.findByPhone(phone).stream()
                .map(this::toResponse)
                .toList();
    }

    /** Search accounts by customer name (partial, case-insensitive). */
    public List<AccountResponse> searchByName(String name) {
        log.info("Searching accounts by name: {}", name);
        return repository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toResponse)
                .toList();
    }

    /** Search accounts by email address (exact, case-insensitive). */
    public List<AccountResponse> searchByEmail(String email) {
        log.info("Searching accounts by email: {}", email);
        return repository.findByEmailIgnoreCase(email).stream()
                .map(this::toResponse)
                .toList();
    }

    /** Get a single account by its unique account number. */
    public AccountResponse getByAccountNumber(String accountNumber) {
        log.info("Fetching account: {}", accountNumber);
        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account", accountNumber));
        return toResponse(account);
    }

    /** Check whether an account number exists. */
    public boolean existsByAccountNumber(String accountNumber) {
        return repository.existsByAccountNumber(accountNumber);
    }

    // ---------------------------------------------------------------

    private AccountResponse toResponse(Account a) {
        return new AccountResponse(
                a.getAccountNumber(),
                a.getProductCode(),
                a.getName(),
                a.getPhone(),
                a.getEmail(),
                a.getStatus(),
                a.getBranchCode()
        );
    }
}

