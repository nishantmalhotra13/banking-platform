package com.banking.platform.mdm.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * JPA entity representing a customer account in the MDM database.
 * <p>
 * Each row maps to one financial product held by a customer.  A single customer
 * (identified by phone/name/email) may own multiple accounts with different product codes.
 * </p>
 */
@Entity
@Table(name = "accounts", indexes = {
        @Index(name = "idx_accounts_phone", columnList = "phone"),
        @Index(name = "idx_accounts_email", columnList = "email"),
        @Index(name = "idx_accounts_account_number", columnList = "account_number", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true, length = 30)
    private String accountNumber;

    @Column(name = "product_code", nullable = false, length = 20)
    private String productCode;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String address;

    @Column(length = 20, nullable = false)
    @Builder.Default
    private String status = "ACTIVE";

    @Column(name = "date_opened")
    private LocalDate dateOpened;

    @Column(name = "branch_code", length = 10)
    private String branchCode;
}

