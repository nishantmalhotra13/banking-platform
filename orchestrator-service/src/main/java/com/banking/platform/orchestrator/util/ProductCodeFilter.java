package com.banking.platform.orchestrator.util;

import java.util.Set;

/**
 * Utility to classify product codes into credit card, debit card, etc.
 */
public final class ProductCodeFilter {

    private ProductCodeFilter() {}

    private static final Set<String> CREDIT_CARD_CODES = Set.of("CC01", "CC02");
    private static final Set<String> DEBIT_CARD_CODES  = Set.of("DC01", "DC02");

    public static boolean isCreditCard(String code) {
        return CREDIT_CARD_CODES.contains(code);
    }

    public static boolean isDebitCard(String code) {
        return DEBIT_CARD_CODES.contains(code);
    }

    public static boolean isCard(String code) {
        return isCreditCard(code) || isDebitCard(code);
    }
}

