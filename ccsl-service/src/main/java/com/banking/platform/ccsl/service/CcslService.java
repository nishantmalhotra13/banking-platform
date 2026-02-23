package com.banking.platform.ccsl.service;

import com.banking.platform.ccsl.domain.CardEntity;
import com.banking.platform.ccsl.domain.TokenEntity;
import com.banking.platform.ccsl.dto.CardResponse;
import com.banking.platform.ccsl.repository.CardRepository;
import com.banking.platform.ccsl.repository.TokenRepository;
import com.banking.platform.common.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Business logic for card tokenization and lookup.
 */
@Service
public class CcslService {

    private static final Logger log = LoggerFactory.getLogger(CcslService.class);

    private final TokenRepository tokenRepository;
    private final CardRepository cardRepository;

    public CcslService(TokenRepository tokenRepository, CardRepository cardRepository) {
        this.tokenRepository = tokenRepository;
        this.cardRepository = cardRepository;
    }

    /**
     * Create or retrieve a token for the given account number.
     * If the account was already tokenized, returns the existing token.
     */
    public String tokenize(String accountNumber) {
        log.info("Tokenizing account: {}", accountNumber);
        return tokenRepository.findByAccountNumber(accountNumber)
                .map(TokenEntity::getToken)
                .orElseGet(() -> {
                    String token = UUID.randomUUID().toString();
                    tokenRepository.save(TokenEntity.builder()
                            .token(token)
                            .accountNumber(accountNumber)
                            .build());
                    log.info("Created new token for account: {}", accountNumber);
                    return token;
                });
    }

    /** Get all cards for a token (credit + debit). */
    public List<CardResponse> getCards(String token) {
        String accountNumber = resolveToken(token);
        return cardRepository.findByAccountNumber(accountNumber).stream()
                .map(this::toResponse)
                .toList();
    }

    /** Get only credit cards for a token. */
    public List<CardResponse> getCreditCards(String token) {
        String accountNumber = resolveToken(token);
        return cardRepository.findByAccountNumberAndCardCategory(accountNumber, "CREDIT").stream()
                .map(this::toResponse)
                .toList();
    }

    /** Get only debit cards for a token. */
    public List<CardResponse> getDebitCards(String token) {
        String accountNumber = resolveToken(token);
        return cardRepository.findByAccountNumberAndCardCategory(accountNumber, "DEBIT").stream()
                .map(this::toResponse)
                .toList();
    }

    /** Get all cards directly by account number (no token required). */
    public List<CardResponse> getCardsByAccountNumber(String accountNumber) {
        log.info("Fetching cards for account: {}", accountNumber);
        return cardRepository.findByAccountNumber(accountNumber).stream()
                .map(this::toResponse)
                .toList();
    }

    /** Validate whether a card number exists and return its details if found. */
    public CardResponse validateCard(String cardNumber) {
        CardEntity card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", cardNumber));
        return toResponse(card);
    }

    // ---------------------------------------------------------------

    private String resolveToken(String token) {
        TokenEntity entity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token", token));
        return entity.getAccountNumber();
    }

    private CardResponse toResponse(CardEntity card) {
        return new CardResponse(
                card.getCardNumber(),
                card.getCardType(),
                card.getCardCategory(),
                card.getCardStatus(),
                card.getIssuerBank()
        );
    }
}

