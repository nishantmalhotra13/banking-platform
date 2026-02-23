package com.banking.platform.orchestrator.steps;

import com.banking.platform.orchestrator.pipeline.OrchestrationContext;
import com.banking.platform.orchestrator.pipeline.Step;
import com.banking.platform.orchestrator.util.ProductCodeFilter;
import org.springframework.stereotype.Component;

/** Filters accounts to only debit card product codes (DC01, DC02). */
@Component
public class FilterDebitCardProductsStep implements Step {

    @Override
    public void execute(OrchestrationContext context) {
        var filtered = context.getAccounts().stream()
                .filter(a -> ProductCodeFilter.isDebitCard(a.productCode()))
                .toList();
        context.setFilteredAccounts(filtered);
    }

    @Override
    public String name() { return "FILTER_DEBIT_PRODUCTS"; }
}

