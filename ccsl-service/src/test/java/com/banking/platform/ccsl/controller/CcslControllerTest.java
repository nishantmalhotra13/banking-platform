package com.banking.platform.ccsl.controller;

import com.banking.platform.ccsl.dto.CardResponse;
import com.banking.platform.ccsl.dto.TokenResponse;
import com.banking.platform.ccsl.service.CcslService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CcslControllerTest {

    @Mock private CcslService service;
    @InjectMocks private CcslController controller;

    @Test
    void tokenize_delegatesToService() {
        when(service.tokenize("ACC1")).thenReturn("tok-123");

        var request = new com.banking.platform.ccsl.dto.TokenRequest("ACC1");
        TokenResponse response = controller.tokenize(request);

        assertThat(response.token()).isEqualTo("tok-123");
    }

    @Test
    void getCards_delegatesToService() {
        CardResponse card = new CardResponse("4111", "VISA", "CREDIT", "ACTIVE", "RBC");
        when(service.getCards("tok-1")).thenReturn(List.of(card));

        List<CardResponse> result = controller.getCards("tok-1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).cardNumber()).isEqualTo("4111");
    }

    @Test
    void getCreditCards_delegatesToService() {
        when(service.getCreditCards("tok-1")).thenReturn(List.of());

        List<CardResponse> result = controller.getCreditCards("tok-1");

        assertThat(result).isEmpty();
    }

    @Test
    void getDebitCards_delegatesToService() {
        when(service.getDebitCards("tok-1")).thenReturn(List.of());

        List<CardResponse> result = controller.getDebitCards("tok-1");

        assertThat(result).isEmpty();
    }

    @Test
    void getCardsByAccount_delegatesToService() {
        CardResponse card = new CardResponse("5555", "MC", "DEBIT", "ACTIVE", "TD");
        when(service.getCardsByAccountNumber("ACC1")).thenReturn(List.of(card));

        List<CardResponse> result = controller.getCardsByAccount("ACC1");

        assertThat(result).hasSize(1);
    }

    @Test
    void validateCard_delegatesToService() {
        CardResponse card = new CardResponse("4111", "VISA", "CREDIT", "ACTIVE", "RBC");
        when(service.validateCard("4111")).thenReturn(card);

        var request = new com.banking.platform.ccsl.dto.CardValidateRequest("4111");
        CardResponse result = controller.validateCard(request);

        assertThat(result.cardNumber()).isEqualTo("4111");
    }
}

