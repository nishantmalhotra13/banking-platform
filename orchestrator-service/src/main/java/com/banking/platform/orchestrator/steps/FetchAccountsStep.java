package com.banking.platform.orchestrator.steps;

import com.banking.platform.orchestrator.client.MdmClient;
import com.banking.platform.orchestrator.pipeline.OrchestrationContext;
import com.banking.platform.orchestrator.pipeline.Step;
import org.springframework.stereotype.Component;

/** Step 1: Fetch accounts from MDM based on search type + value. */
@Component
public class FetchAccountsStep implements Step {

    private final MdmClient mdmClient;

    public FetchAccountsStep(MdmClient mdmClient) {
        this.mdmClient = mdmClient;
    }

    @Override
    public void execute(OrchestrationContext context) {
        context.setAccounts(
                mdmClient.search(context.getSearchType(), context.getSearchValue())
        );
    }

    @Override
    public String name() { return "FETCH_ACCOUNTS"; }
}

