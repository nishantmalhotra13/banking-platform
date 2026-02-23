package com.banking.platform.orchestrator.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCodeFilterTest {

    @ParameterizedTest
    @ValueSource(strings = {"CC01", "CC02"})
    void isCreditCard_trueForValidCodes(String code) {
        assertThat(ProductCodeFilter.isCreditCard(code)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"DC01", "DC02", "SA01", "", "cc01"})
    void isCreditCard_falseForInvalidCodes(String code) {
        assertThat(ProductCodeFilter.isCreditCard(code)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"DC01", "DC02"})
    void isDebitCard_trueForValidCodes(String code) {
        assertThat(ProductCodeFilter.isDebitCard(code)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"CC01", "CC02", "SA01", "", "dc01"})
    void isDebitCard_falseForInvalidCodes(String code) {
        assertThat(ProductCodeFilter.isDebitCard(code)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"CC01", "CC02", "DC01", "DC02"})
    void isCard_trueForAllCardCodes(String code) {
        assertThat(ProductCodeFilter.isCard(code)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"SA01", "MF01", "INVALID"})
    void isCard_falseForNonCardCodes(String code) {
        assertThat(ProductCodeFilter.isCard(code)).isFalse();
    }
}

