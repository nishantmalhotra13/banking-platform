package com.banking.platform.orchestrator.steps;

import com.banking.platform.orchestrator.dto.MdmAccountResponse;
import com.banking.platform.orchestrator.pipeline.OrchestrationContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FilterDebitCardProductsStepTest {

    private final FilterDebitCardProductsStep step = new FilterDebitCardProductsStep();

    @Test
    void name_returnsFilterDebitProducts() {
        assertThat(step.name()).isEqualTo("FILTER_DEBIT_PRODUCTS");
    }

    @Test
    void execute_filtersDebitCardProductsOnly() {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setAccounts(List.of(
                acct("ACC1", "CC01"),
                acct("ACC2", "DC01"),
                acct("ACC3", "DC02"),
                acct("ACC4", "SA01")
        ));

        step.execute(ctx);

        assertThat(ctx.getFilteredAccounts()).hasSize(2);
        assertThat(ctx.getFilteredAccounts()).extracting(MdmAccountResponse::productCode)
                .containsExactly("DC01", "DC02");
    }

    @Test
    void execute_returnsEmptyWhenNoDebitCards() {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setAccounts(List.of(acct("ACC1", "CC01"), acct("ACC2", "SA01")));

        step.execute(ctx);

        assertThat(ctx.getFilteredAccounts()).isEmpty();
    }

    private MdmAccountResponse acct(String accNum, String product) {
        return new MdmAccountResponse(accNum, product, "Name", "416", "e@e.com", "ACTIVE", "BR001");
    }
}

