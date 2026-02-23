package com.banking.platform.mdm.controller;

import com.banking.platform.mdm.dto.AccountResponse;
import com.banking.platform.mdm.dto.SearchRequest;
import com.banking.platform.mdm.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock private AccountService service;
    @InjectMocks private AccountController controller;

    private final AccountResponse sampleAccount = new AccountResponse(
            "ACC1", "CC01", "John", "416", "j@e.com", "ACTIVE", "BR001");

    @Test
    void searchByPhone_delegatesToService() {
        when(service.searchByPhone("416")).thenReturn(List.of(sampleAccount));

        List<AccountResponse> result = controller.searchByPhone(new SearchRequest("416"));

        assertThat(result).hasSize(1);
    }

    @Test
    void searchByName_delegatesToService() {
        when(service.searchByName("John")).thenReturn(List.of(sampleAccount));

        List<AccountResponse> result = controller.searchByName(new SearchRequest("John"));

        assertThat(result).hasSize(1);
    }

    @Test
    void searchByEmail_delegatesToService() {
        when(service.searchByEmail("j@e.com")).thenReturn(List.of(sampleAccount));

        List<AccountResponse> result = controller.searchByEmail(new SearchRequest("j@e.com"));

        assertThat(result).hasSize(1);
    }

    @Test
    void getAccount_delegatesToService() {
        when(service.getByAccountNumber("ACC1")).thenReturn(sampleAccount);

        AccountResponse result = controller.getAccount("ACC1");

        assertThat(result.accountNumber()).isEqualTo("ACC1");
    }

    @Test
    void accountExists_returnsTrue() {
        when(service.existsByAccountNumber("ACC1")).thenReturn(true);

        Map<String, Boolean> result = controller.accountExists("ACC1");

        assertThat(result.get("exists")).isTrue();
    }

    @Test
    void accountExists_returnsFalse() {
        when(service.existsByAccountNumber("NOPE")).thenReturn(false);

        Map<String, Boolean> result = controller.accountExists("NOPE");

        assertThat(result.get("exists")).isFalse();
    }
}

