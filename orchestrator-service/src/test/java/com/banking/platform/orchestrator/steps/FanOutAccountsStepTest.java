package com.banking.platform.orchestrator.steps;

import com.banking.platform.orchestrator.client.CcslClient;
import com.banking.platform.orchestrator.dto.CardResponse;
import com.banking.platform.orchestrator.dto.MdmAccountResponse;
import com.banking.platform.orchestrator.pipeline.OrchestrationContext;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FanOutAccountsStepTest {

    @Mock private CcslClient ccslClient;

    private SimpleMeterRegistry meterRegistry;
    private FanOutAccountsStep step;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        step = new FanOutAccountsStep(ccslClient, meterRegistry);
    }

    @Test
    void name_returnsFanoutAccounts() {
        assertThat(step.name()).isEqualTo("FANOUT_ACCOUNTS");
    }

    @Test
    void execute_setsEmptyResponsesWhenNoAccounts() throws Exception {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setFilteredAccounts(List.of());

        step.execute(ctx);

        assertThat(ctx.getFinalResponses()).isEmpty();
    }

    @Test
    void execute_setsEmptyResponsesWhenNullAccounts() throws Exception {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setFilteredAccounts(null);

        step.execute(ctx);

        assertThat(ctx.getFinalResponses()).isEmpty();
    }

    @Test
    void execute_tokenizesAndFetchesCardsForEachAccount() throws Exception {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setFilteredAccounts(List.of(
                acct("ACC1", "CC01"),
                acct("ACC2", "CC02")
        ));

        when(ccslClient.tokenize("ACC1")).thenReturn("tok-1");
        when(ccslClient.tokenize("ACC2")).thenReturn("tok-2");
        when(ccslClient.getCards("tok-1")).thenReturn(List.of(card("4111")));
        when(ccslClient.getCards("tok-2")).thenReturn(List.of(card("5222"), card("5333")));

        step.execute(ctx);

        assertThat(ctx.getFinalResponses()).hasSize(2);
        assertThat(meterRegistry.counter("orchestrator.account.success").count()).isEqualTo(2.0);
    }

    @Test
    void execute_throwsOnCcslFailure() {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setFilteredAccounts(List.of(acct("ACC1", "CC01")));

        when(ccslClient.tokenize("ACC1")).thenThrow(new RuntimeException("CCSL down"));

        assertThatThrownBy(() -> step.execute(ctx))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("CCSL down");
    }

    @Test
    void execute_recordsFanoutDurationMetric() throws Exception {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setFilteredAccounts(List.of(acct("ACC1", "CC01")));

        when(ccslClient.tokenize("ACC1")).thenReturn("tok-1");
        when(ccslClient.getCards("tok-1")).thenReturn(List.of());

        step.execute(ctx);

        assertThat(meterRegistry.timer("orchestrator.fanout.duration").count()).isEqualTo(1);
    }

    private MdmAccountResponse acct(String accNum, String product) {
        return new MdmAccountResponse(accNum, product, "Name", "416", "e@e.com", "ACTIVE", "BR001");
    }

    private CardResponse card(String number) {
        return new CardResponse(number, "VISA", "CREDIT", "ACTIVE", "RBC");
    }
}

