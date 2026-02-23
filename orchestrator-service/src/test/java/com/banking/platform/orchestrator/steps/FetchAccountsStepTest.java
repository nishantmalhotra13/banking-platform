package com.banking.platform.orchestrator.steps;

import com.banking.platform.orchestrator.client.MdmClient;
import com.banking.platform.orchestrator.dto.MdmAccountResponse;
import com.banking.platform.orchestrator.pipeline.OrchestrationContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchAccountsStepTest {

    @Mock private MdmClient mdmClient;
    @InjectMocks private FetchAccountsStep step;

    @Test
    void name_returnsFetchAccounts() {
        assertThat(step.name()).isEqualTo("FETCH_ACCOUNTS");
    }

    @Test
    void execute_setsAccountsOnContext() {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setSearchType("phone");
        ctx.setSearchValue("416123");

        MdmAccountResponse acc = new MdmAccountResponse("ACC1", "CC01", "John", "416123", "j@e.com", "ACTIVE", "BR001");
        when(mdmClient.search("phone", "416123")).thenReturn(List.of(acc));

        step.execute(ctx);

        assertThat(ctx.getAccounts()).hasSize(1);
        assertThat(ctx.getAccounts().get(0).accountNumber()).isEqualTo("ACC1");
    }

    @Test
    void execute_setsEmptyListWhenNoResults() {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setSearchType("email");
        ctx.setSearchValue("nobody@e.com");

        when(mdmClient.search("email", "nobody@e.com")).thenReturn(List.of());

        step.execute(ctx);

        assertThat(ctx.getAccounts()).isEmpty();
    }
}

