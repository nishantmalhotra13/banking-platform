package com.banking.platform.orchestrator.pipeline;

/**
 * A single unit of work in an orchestration pipeline.
 * <p>
 * Steps are Spring-managed components that can be composed into pipelines using
 * {@link StepExecutor}.  Each step reads from and writes to the shared
 * {@link OrchestrationContext}.
 * </p>
 *
 * @see StepExecutor
 */
public interface Step {

    /**
     * Execute this step, reading inputs from and writing outputs to the context.
     *
     * @param context shared mutable state for the pipeline
     * @throws Exception if the step fails; the pipeline will abort
     */
    void execute(OrchestrationContext context) throws Exception;

    /**
     * Human-readable name for logging and metrics (e.g., {@code "FETCH_ACCOUNTS"}).
     */
    String name();
}

