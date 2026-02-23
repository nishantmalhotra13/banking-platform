package com.banking.platform.orchestrator.pipeline;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrchestrationContextTest {

    @Test
    void defaultFieldsAreNull() {
        OrchestrationContext ctx = new OrchestrationContext();
        assertThat(ctx.getSearchType()).isNull();
        assertThat(ctx.getSearchValue()).isNull();
        assertThat(ctx.getAccounts()).isNull();
        assertThat(ctx.getFilteredAccounts()).isNull();
        assertThat(ctx.getFinalResponses()).isNull();
    }

    @Test
    void setAndGetSearchFields() {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setSearchType("phone");
        ctx.setSearchValue("4161234567");

        assertThat(ctx.getSearchType()).isEqualTo("phone");
        assertThat(ctx.getSearchValue()).isEqualTo("4161234567");
    }

    @Test
    void setAndGetAttributes() {
        OrchestrationContext ctx = new OrchestrationContext();
        ctx.setAttribute("myKey", 42);

        Integer value = ctx.getAttribute("myKey");
        assertThat(value).isEqualTo(42);
    }

    @Test
    void getAttributeReturnsNullForMissing() {
        OrchestrationContext ctx = new OrchestrationContext();

        String value = ctx.getAttribute("nonexistent");
        assertThat(value).isNull();
    }

    @Test
    void attributeMapIsInitiallyEmpty() {
        OrchestrationContext ctx = new OrchestrationContext();
        assertThat(ctx.getAttributes()).isEmpty();
    }
}

