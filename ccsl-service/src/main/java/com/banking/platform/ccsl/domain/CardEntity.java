package com.banking.platform.ccsl.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * JPA entity representing a card (credit or debit) linked to an account.
 */
@Entity
@Table(name = "cards", indexes = {
        @Index(name = "idx_cards_account_number", columnList = "account_number"),
        @Index(name = "idx_cards_card_number", columnList = "card_number")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false, length = 30)
    private String accountNumber;

    @Column(name = "card_number", nullable = false, length = 20)
    private String cardNumber;

    @Column(name = "card_type", nullable = false, length = 20)
    private String cardType;

    /** CREDIT or DEBIT */
    @Column(name = "card_category", nullable = false, length = 10)
    private String cardCategory;

    /** ACTIVE, BLOCKED, or EXPIRED */
    @Column(name = "card_status", nullable = false, length = 10)
    @Builder.Default
    private String cardStatus = "ACTIVE";

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "issuer_bank", length = 50)
    private String issuerBank;
}

