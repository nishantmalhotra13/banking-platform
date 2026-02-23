package com.banking.platform.orchestrator.pipeline;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Fluent pipeline builder for composing orchestration steps.
 * <p>
 * Usage:
 * <pre>{@code
 * StepExecutor.create(context, meterRegistry)
 *     .then(fetchAccountsStep)
 *     .then(filterCreditCardProductsStep)
 *     .thenIf(ctx -> !ctx.getFilteredAccounts().isEmpty(), fanOutAccountsStep)
 *     .execute();
 * }</pre>
 * <p>
 * Each step is timed with Micrometer and logged.  If any step throws, the pipeline
 * aborts immediately — no subsequent steps execute.
 * </p>
 */
public final class StepExecutor {

    private static final Logger log = LoggerFactory.getLogger(StepExecutor.class);

    private final OrchestrationContext context;
    private final MeterRegistry meterRegistry;
    private final List<ConditionalStep> steps = new ArrayList<>();

    private StepExecutor(OrchestrationContext context, MeterRegistry meterRegistry) {
        this.context = context;
        this.meterRegistry = meterRegistry;
    }

    /**
     * Create a new pipeline bound to the given context and meter registry.
     */
    public static StepExecutor create(OrchestrationContext context, MeterRegistry meterRegistry) {
        return new StepExecutor(context, meterRegistry);
    }

    /**
     * Append a step that always runs.
     */
    public StepExecutor then(Step step) {
        steps.add(new ConditionalStep(step, ctx -> true));
        return this;
    }

    /**
     * Append a step that runs only when the predicate evaluates to {@code true}
     * at execution time.
     */
    public StepExecutor thenIf(Predicate<OrchestrationContext> condition, Step step) {
        steps.add(new ConditionalStep(step, condition));
        return this;
    }

    /**
     * Execute all registered steps in order.
     *
     * @throws Exception propagated from the first failing step
     */
    public void execute() throws Exception {
        Timer.Sample pipelineTimer = Timer.start(meterRegistry);
        log.info("Pipeline starting — {} step(s) registered", steps.size());

        try {
            for (ConditionalStep cs : steps) {
                if (!cs.condition.test(context)) {
                    log.info("Step [{}] — SKIPPED (condition not met)", cs.step.name());
                    continue;
                }

                Timer.Sample stepTimer = Timer.start(meterRegistry);
                log.info("Step [{}] — EXECUTING", cs.step.name());

                try {
                    cs.step.execute(context);
                    log.info("Step [{}] — COMPLETED", cs.step.name());
                } catch (Exception ex) {
                    log.error("Step [{}] — FAILED: {}", cs.step.name(), ex.getMessage(), ex);
                    meterRegistry.counter("orchestrator.step.failure", "step", cs.step.name()).increment();
                    throw ex;
                } finally {
                    stepTimer.stop(meterRegistry.timer("orchestrator.step.duration", "step", cs.step.name()));
                }
            }
        } finally {
            pipelineTimer.stop(meterRegistry.timer("orchestrator.pipeline.duration"));
        }

        log.info("Pipeline completed successfully");
    }

    // ---------------------------------------------------------------

    private record ConditionalStep(Step step, Predicate<OrchestrationContext> condition) {}
}

