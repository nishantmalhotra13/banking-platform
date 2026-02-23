package com.banking.platform.ccsl.service;

import com.banking.platform.ccsl.domain.CardEntity;
import com.banking.platform.ccsl.domain.TokenEntity;
import com.banking.platform.ccsl.dto.CardResponse;
import com.banking.platform.ccsl.repository.CardRepository;
import com.banking.platform.ccsl.repository.TokenRepository;
import com.banking.platform.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CcslServiceTest {

    @Mock private TokenRepository tokenRepository;
    @Mock private CardRepository cardRepository;

    private CcslService service;

    @BeforeEach
    void setUp() {
        service = new CcslService(tokenRepository, cardRepository);
    }

    // --- tokenize ---

    @Test
    void tokenize_returnsExistingToken() {
        TokenEntity existing = TokenEntity.builder().token("existing-token").accountNumber("ACC1").build();
        when(tokenRepository.findByAccountNumber("ACC1")).thenReturn(Optional.of(existing));

        String result = service.tokenize("ACC1");

        assertThat(result).isEqualTo("existing-token");
        verify(tokenRepository, never()).save(any());
    }

    @Test
    void tokenize_createsNewTokenWhenNotFound() {
        when(tokenRepository.findByAccountNumber("ACC2")).thenReturn(Optional.empty());

        String result = service.tokenize("ACC2");

        assertThat(result).isNotBlank();
        ArgumentCaptor<TokenEntity> captor = ArgumentCaptor.forClass(TokenEntity.class);
        verify(tokenRepository).save(captor.capture());
        assertThat(captor.getValue().getAccountNumber()).isEqualTo("ACC2");
        assertThat(captor.getValue().getToken()).isEqualTo(result);
    }

    // --- getCards ---

    @Test
    void getCards_resolvesTokenAndReturnsCards() {
        TokenEntity token = TokenEntity.builder().token("tok-1").accountNumber("ACC1").build();
        when(tokenRepository.findByToken("tok-1")).thenReturn(Optional.of(token));

        CardEntity card = buildCard("4111", "VISA", "CREDIT", "ACTIVE", "RBC");
        when(cardRepository.findByAccountNumber("ACC1")).thenReturn(List.of(card));

        List<CardResponse> result = service.getCards("tok-1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).cardNumber()).isEqualTo("4111");
        assertThat(result.get(0).cardType()).isEqualTo("VISA");
    }

    @Test
    void getCards_throwsWhenTokenNotFound() {
        when(tokenRepository.findByToken("bad")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getCards("bad"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Token");
    }

    // --- getCreditCards ---

    @Test
    void getCreditCards_filtersCorrectly() {
        TokenEntity token = TokenEntity.builder().token("tok-1").accountNumber("ACC1").build();
        when(tokenRepository.findByToken("tok-1")).thenReturn(Optional.of(token));
        when(cardRepository.findByAccountNumberAndCardCategory("ACC1", "CREDIT"))
                .thenReturn(List.of(buildCard("4111", "VISA", "CREDIT", "ACTIVE", "RBC")));

        List<CardResponse> result = service.getCreditCards("tok-1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).cardCategory()).isEqualTo("CREDIT");
    }

    // --- getDebitCards ---

    @Test
    void getDebitCards_filtersCorrectly() {
        TokenEntity token = TokenEntity.builder().token("tok-1").accountNumber("ACC1").build();
        when(tokenRepository.findByToken("tok-1")).thenReturn(Optional.of(token));
        when(cardRepository.findByAccountNumberAndCardCategory("ACC1", "DEBIT"))
                .thenReturn(List.of(buildCard("5555", "INTERAC", "DEBIT", "ACTIVE", "TD")));

        List<CardResponse> result = service.getDebitCards("tok-1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).cardCategory()).isEqualTo("DEBIT");
    }

    // --- getCardsByAccountNumber ---

    @Test
    void getCardsByAccountNumber_returnsCards() {
        when(cardRepository.findByAccountNumber("ACC1"))
                .thenReturn(List.of(buildCard("4111", "VISA", "CREDIT", "ACTIVE", "RBC")));

        List<CardResponse> result = service.getCardsByAccountNumber("ACC1");

        assertThat(result).hasSize(1);
    }

    @Test
    void getCardsByAccountNumber_returnsEmptyListWhenNone() {
        when(cardRepository.findByAccountNumber("ACC999")).thenReturn(List.of());

        List<CardResponse> result = service.getCardsByAccountNumber("ACC999");

        assertThat(result).isEmpty();
    }

    // --- validateCard ---

    @Test
    void validateCard_returnsCardWhenFound() {
        CardEntity card = buildCard("4111", "VISA", "CREDIT", "ACTIVE", "RBC");
        when(cardRepository.findByCardNumber("4111")).thenReturn(Optional.of(card));

        CardResponse result = service.validateCard("4111");

        assertThat(result.cardNumber()).isEqualTo("4111");
    }

    @Test
    void validateCard_throwsWhenNotFound() {
        when(cardRepository.findByCardNumber("0000")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.validateCard("0000"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // --- helper ---

    private CardEntity buildCard(String number, String type, String category, String status, String bank) {
        CardEntity card = new CardEntity();
        card.setCardNumber(number);
        card.setCardType(type);
        card.setCardCategory(category);
        card.setCardStatus(status);
        card.setIssuerBank(bank);
        return card;
    }
}

