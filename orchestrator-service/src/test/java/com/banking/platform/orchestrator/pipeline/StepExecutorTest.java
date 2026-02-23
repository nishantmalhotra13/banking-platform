package com.banking.platform.orchestrator.pipeline;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StepExecutorTest {

    private MeterRegistry meterRegistry;
    private OrchestrationContext context;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        context = new OrchestrationContext();
    }

    @Test
    void executesStepsInOrder() throws Exception {
        List<String> order = new ArrayList<>();

        Step step1 = new TestStep("STEP_1", ctx -> order.add("1"));
        Step step2 = new TestStep("STEP_2", ctx -> order.add("2"));
        Step step3 = new TestStep("STEP_3", ctx -> order.add("3"));

        StepExecutor.create(context, meterRegistry)
                .then(step1)
                .then(step2)
                .then(step3)
                .execute();

        assertThat(order).containsExactly("1", "2", "3");
    }

    @Test
    void thenIf_executesStepWhenConditionTrue() throws Exception {
        List<String> order = new ArrayList<>();

        Step step = new TestStep("CONDITIONAL", ctx -> order.add("executed"));

        StepExecutor.create(context, meterRegistry)
                .thenIf(ctx -> true, step)
                .execute();

        assertThat(order).containsExactly("executed");
    }

    @Test
    void thenIf_skipsStepWhenConditionFalse() throws Exception {
        List<String> order = new ArrayList<>();

        Step step = new TestStep("SKIPPED", ctx -> order.add("should-not-run"));

        StepExecutor.create(context, meterRegistry)
                .thenIf(ctx -> false, step)
                .execute();

        assertThat(order).isEmpty();
    }

    @Test
    void abortsOnFirstFailure() {
        List<String> order = new ArrayList<>();

        Step step1 = new TestStep("OK", ctx -> order.add("1"));
        Step step2 = new TestStep("FAIL", ctx -> { throw new RuntimeException("boom"); });
        Step step3 = new TestStep("UNREACHABLE", ctx -> order.add("3"));

        assertThatThrownBy(() ->
                StepExecutor.create(context, meterRegistry)
                        .then(step1)
                        .then(step2)
                        .then(step3)
                        .execute()
        ).isInstanceOf(RuntimeException.class).hasMessage("boom");

        assertThat(order).containsExactly("1");
    }

    @Test
    void recordsPipelineDurationMetric() throws Exception {
        StepExecutor.create(context, meterRegistry)
                .then(new TestStep("NOOP", ctx -> {}))
                .execute();

        assertThat(meterRegistry.timer("orchestrator.pipeline.duration").count()).isEqualTo(1);
    }

    @Test
    void recordsStepDurationMetric() throws Exception {
        StepExecutor.create(context, meterRegistry)
                .then(new TestStep("MY_STEP", ctx -> {}))
                .execute();

        assertThat(meterRegistry.timer("orchestrator.step.duration", "step", "MY_STEP").count()).isEqualTo(1);
    }

    @Test
    void recordsFailureCounterOnError() {
        Step failing = new TestStep("BAD_STEP", ctx -> { throw new RuntimeException("fail"); });

        try {
            StepExecutor.create(context, meterRegistry)
                    .then(failing)
                    .execute();
        } catch (Exception ignored) {}

        assertThat(meterRegistry.counter("orchestrator.step.failure", "step", "BAD_STEP").count()).isEqualTo(1.0);
    }

    @Test
    void emptyPipelineExecutesWithoutError() throws Exception {
        StepExecutor.create(context, meterRegistry).execute();

        assertThat(meterRegistry.timer("orchestrator.pipeline.duration").count()).isEqualTo(1);
    }

    // --- test helper ---

    private interface StepAction {
        void run(OrchestrationContext ctx) throws Exception;
    }

    private record TestStep(String stepName, StepAction action) implements Step {
        @Override
        public void execute(OrchestrationContext context) throws Exception {
            action.run(context);
        }

        @Override
        public String name() {
            return stepName;
        }
    }
}

