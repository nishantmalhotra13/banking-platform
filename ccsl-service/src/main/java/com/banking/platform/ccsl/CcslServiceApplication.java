package com.banking.platform.ccsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CCSL (Card Services Layer) Service.
 * <p>
 * Manages card data (credit and debit) and token-to-account mappings.
 * Provides tokenization, card lookup by token/account, and card validation APIs.
 * </p>
 */
@SpringBootApplication(scanBasePackages = {
        "com.banking.platform.ccsl",
        "com.banking.platform.common"
})
public class CcslServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcslServiceApplication.class, args);
    }
}

