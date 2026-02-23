package com.banking.platform.orchestrator.service;

import com.banking.platform.orchestrator.client.CcslClient;
import com.banking.platform.orchestrator.client.MdmClient;
import com.banking.platform.orchestrator.dto.*;
import com.banking.platform.orchestrator.steps.*;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrchestrationServiceTest {

    @Mock private MdmClient mdmClient;
    @Mock private CcslClient ccslClient;

    private OrchestrationService service;

    @BeforeEach
    void setUp() {
        SimpleMeterRegistry meter = new SimpleMeterRegistry();
        FetchAccountsStep fetchStep = new FetchAccountsStep(mdmClient);
        FilterCreditCardProductsStep creditFilter = new FilterCreditCardProductsStep();
        FilterDebitCardProductsStep debitFilter = new FilterDebitCardProductsStep();
        FanOutAccountsStep fanOut = new FanOutAccountsStep(ccslClient, meter);

        service = new OrchestrationService(meter, fetchStep, creditFilter, debitFilter, fanOut, mdmClient, ccslClient);
    }

    // --- getCardsByAccount ---

    @Test
    void getCardsByAccount_tokenizesAndFetchesCards() {
        when(ccslClient.tokenize("ACC1")).thenReturn("tok-1");
        when(ccslClient.getCards("tok-1")).thenReturn(List.of(card("4111")));

        List<CardResponse> result = service.getCardsByAccount("ACC1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).cardNumber()).isEqualTo("4111");
    }

    // --- getCreditCardsByAccount ---

    @Test
    void getCreditCardsByAccount_delegatesToCcsl() {
        when(ccslClient.tokenize("ACC1")).thenReturn("tok-1");
        when(ccslClient.getCreditCards("tok-1")).thenReturn(List.of(card("4111")));

        List<CardResponse> result = service.getCreditCardsByAccount("ACC1");

        assertThat(result).hasSize(1);
    }

    // --- getDebitCardsByAccount ---

    @Test
    void getDebitCardsByAccount_delegatesToCcsl() {
        when(ccslClient.tokenize("ACC1")).thenReturn("tok-1");
        when(ccslClient.getDebitCards("tok-1")).thenReturn(List.of(card("5555")));

        List<CardResponse> result = service.getDebitCardsByAccount("ACC1");

        assertThat(result).hasSize(1);
    }

    // --- getAccountSummary ---

    @Test
    void getAccountSummary_combinesMdmAndCcslData() {
        MdmAccountResponse mdmResp = new MdmAccountResponse(
                "ACC1", "CC01", "John", "416", "j@e.com", "ACTIVE", "BR001");
        when(mdmClient.getAccount("ACC1")).thenReturn(mdmResp);
        when(ccslClient.tokenize("ACC1")).thenReturn("tok-1");
        when(ccslClient.getCards("tok-1")).thenReturn(List.of(card("4111")));

        AccountSummaryResponse result = service.getAccountSummary("ACC1");

        assertThat(result.accountNumber()).isEqualTo("ACC1");
        assertThat(result.name()).isEqualTo("John");
        assertThat(result.cards()).hasSize(1);
    }

    // --- validateCard ---

    @Test
    void validateCard_returnsValidWhenCardExists() {
        CardResponse card = card("4111");
        when(ccslClient.validateCard("4111")).thenReturn(card);

        CardValidationResponse result = service.validateCard("4111");

        assertThat(result.valid()).isTrue();
        assertThat(result.card()).isNotNull();
    }

    @Test
    void validateCard_returnsInvalidWhenCardNotFound() {
        when(ccslClient.validateCard("0000")).thenThrow(new RuntimeException("not found"));

        CardValidationResponse result = service.validateCard("0000");

        assertThat(result.valid()).isFalse();
        assertThat(result.card()).isNull();
    }

    // --- healthCheck ---

    @Test
    void healthCheck_returnsUpWhenAllServicesUp() {
        when(mdmClient.healthCheck()).thenReturn("{\"status\":\"UP\"}");
        when(ccslClient.healthCheck()).thenReturn("{\"status\":\"UP\"}");

        HealthCheckResponse result = service.healthCheck();

        assertThat(result.overallStatus()).isEqualTo("UP");
        assertThat(result.services()).containsKeys("mdm-service", "ccsl-service");
    }

    @Test
    void healthCheck_returnsDegradedWhenOneServiceDown() {
        when(mdmClient.healthCheck()).thenReturn("{\"status\":\"UP\"}");
        when(ccslClient.healthCheck()).thenReturn("DOWN: Connection refused");

        HealthCheckResponse result = service.healthCheck();

        assertThat(result.overallStatus()).isEqualTo("DEGRADED");
    }

    private CardResponse card(String number) {
        return new CardResponse(number, "VISA", "CREDIT", "ACTIVE", "RBC");
    }
}

