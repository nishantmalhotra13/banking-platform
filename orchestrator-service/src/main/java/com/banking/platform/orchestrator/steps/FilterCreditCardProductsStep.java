package com.banking.platform.orchestrator.steps;

import com.banking.platform.orchestrator.pipeline.OrchestrationContext;
import com.banking.platform.orchestrator.pipeline.Step;
import com.banking.platform.orchestrator.util.ProductCodeFilter;
import org.springframework.stereotype.Component;

/** Filters accounts to only credit card product codes (CC01, CC02). */
@Component
public class FilterCreditCardProductsStep implements Step {

    @Override
    public void execute(OrchestrationContext context) {
        var filtered = context.getAccounts().stream()
                .filter(a -> ProductCodeFilter.isCreditCard(a.productCode()))
                .toList();
        context.setFilteredAccounts(filtered);
    }

    @Override
    public String name() { return "FILTER_CC_PRODUCTS"; }
}

