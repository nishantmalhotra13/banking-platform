package com.banking.platform.mdm.service;

import com.banking.platform.common.exception.ResourceNotFoundException;
import com.banking.platform.mdm.domain.Account;
import com.banking.platform.mdm.dto.AccountResponse;
import com.banking.platform.mdm.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock private AccountRepository repository;

    private AccountService service;

    @BeforeEach
    void setUp() {
        service = new AccountService(repository);
    }

    // --- searchByPhone ---

    @Test
    void searchByPhone_returnsMatchingAccounts() {
        Account acc = buildAccount("ACC1", "CC01", "John", "416123", "j@e.com");
        when(repository.findByPhone("416123")).thenReturn(List.of(acc));

        List<AccountResponse> result = service.searchByPhone("416123");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).accountNumber()).isEqualTo("ACC1");
        assertThat(result.get(0).productCode()).isEqualTo("CC01");
    }

    @Test
    void searchByPhone_returnsEmptyListWhenNoMatch() {
        when(repository.findByPhone("000")).thenReturn(List.of());

        List<AccountResponse> result = service.searchByPhone("000");

        assertThat(result).isEmpty();
    }

    // --- searchByName ---

    @Test
    void searchByName_returnsPartialMatches() {
        Account acc = buildAccount("ACC2", "DC01", "Jane Doe", "555", "jane@e.com");
        when(repository.findByNameContainingIgnoreCase("Jane")).thenReturn(List.of(acc));

        List<AccountResponse> result = service.searchByName("Jane");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Jane Doe");
    }

    // --- searchByEmail ---

    @Test
    void searchByEmail_returnsExactMatch() {
        Account acc = buildAccount("ACC3", "CC02", "Bob", "111", "bob@e.com");
        when(repository.findByEmailIgnoreCase("bob@e.com")).thenReturn(List.of(acc));

        List<AccountResponse> result = service.searchByEmail("bob@e.com");

        assertThat(result).hasSize(1);
    }

    // --- getByAccountNumber ---

    @Test
    void getByAccountNumber_returnsAccountWhenFound() {
        Account acc = buildAccount("ACC1", "CC01", "John", "416", "j@e.com");
        when(repository.findByAccountNumber("ACC1")).thenReturn(Optional.of(acc));

        AccountResponse result = service.getByAccountNumber("ACC1");

        assertThat(result.accountNumber()).isEqualTo("ACC1");
    }

    @Test
    void getByAccountNumber_throwsWhenNotFound() {
        when(repository.findByAccountNumber("ACC999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getByAccountNumber("ACC999"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("ACC999");
    }

    // --- existsByAccountNumber ---

    @Test
    void existsByAccountNumber_returnsTrue() {
        when(repository.existsByAccountNumber("ACC1")).thenReturn(true);
        assertThat(service.existsByAccountNumber("ACC1")).isTrue();
    }

    @Test
    void existsByAccountNumber_returnsFalse() {
        when(repository.existsByAccountNumber("NOPE")).thenReturn(false);
        assertThat(service.existsByAccountNumber("NOPE")).isFalse();
    }

    // --- helper ---

    private Account buildAccount(String accNum, String product, String name, String phone, String email) {
        return Account.builder()
                .accountNumber(accNum)
                .productCode(product)
                .name(name)
                .phone(phone)
                .email(email)
                .status("ACTIVE")
                .branchCode("BR001")
                .build();
    }
}

