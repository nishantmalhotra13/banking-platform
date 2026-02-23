package com.banking.platform.orchestrator.steps;

import com.banking.platform.orchestrator.dto.MdmAccountResponse;
import com.banking.platform.orchestrator.pipeline.OrchestrationContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FilterCreditCardProductsStepTest {

    private final FilterCreditCardProductsStep step = new FilterCreditCardProductsStep();

    @Test
    void name_returnsFilterCcProducts() {
        assertThat(step.name()).isEqualTo("FILTER_CC_PRODUCTS");
    }

    @Test
    void execute_filtersCreditCardProductsOnly() {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setAccounts(List.of(
                acct("ACC1", "CC01"),
                acct("ACC2", "DC01"),
                acct("ACC3", "CC02"),
                acct("ACC4", "SA01")
        ));

        step.execute(ctx);

        assertThat(ctx.getFilteredAccounts()).hasSize(2);
        assertThat(ctx.getFilteredAccounts()).extracting(MdmAccountResponse::productCode)
                .containsExactly("CC01", "CC02");
    }

    @Test
    void execute_returnsEmptyWhenNoCreditCards() {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setAccounts(List.of(acct("ACC1", "DC01"), acct("ACC2", "SA01")));

        step.execute(ctx);

        assertThat(ctx.getFilteredAccounts()).isEmpty();
    }

    private MdmAccountResponse acct(String accNum, String product) {
        return new MdmAccountResponse(accNum, product, "Name", "416", "e@e.com", "ACTIVE", "BR001");
    }
}

