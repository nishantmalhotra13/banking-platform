package com.banking.platform.ccsl.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA entity mapping a token to an account number.
 * Tokens are opaque UUIDs used to avoid passing raw account numbers between services.
 */
@Entity
@Table(name = "tokens", indexes = {
        @Index(name = "idx_tokens_account_number", columnList = "account_number")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String token;

    @Column(name = "account_number", nullable = false, length = 30)
    private String accountNumber;
}

