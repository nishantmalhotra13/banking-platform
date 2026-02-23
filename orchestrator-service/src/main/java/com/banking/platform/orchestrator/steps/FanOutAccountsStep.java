package com.banking.platform.orchestrator.steps;

import com.banking.platform.orchestrator.client.CcslClient;
import com.banking.platform.orchestrator.dto.CardResponse;
import com.banking.platform.orchestrator.dto.FinalResponse;
import com.banking.platform.orchestrator.dto.MdmAccountResponse;
import com.banking.platform.orchestrator.pipeline.OrchestrationContext;
import com.banking.platform.orchestrator.pipeline.Step;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Fan-out step: for each filtered account, tokenize and fetch cards in parallel
 * using virtual threads (stable in Java 21).
 */
@Component
public class FanOutAccountsStep implements Step {

    private static final Logger log = LoggerFactory.getLogger(FanOutAccountsStep.class);

    private final CcslClient ccslClient;
    private final MeterRegistry meterRegistry;

    public FanOutAccountsStep(CcslClient ccslClient, MeterRegistry meterRegistry) {
        this.ccslClient = ccslClient;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void execute(OrchestrationContext context) throws Exception {
        List<MdmAccountResponse> accounts = context.getFilteredAccounts();

        if (accounts == null || accounts.isEmpty()) {
            log.warn("No accounts to fan out");
            context.setFinalResponses(List.of());
            return;
        }

        Timer.Sample overallTimer = Timer.start(meterRegistry);

        // Virtual thread executor — lightweight, no thread-pool sizing needed
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<FinalResponse>> futures = new ArrayList<>();

            for (MdmAccountResponse account : accounts) {
                futures.add(executor.submit(() -> processAccount(account)));
            }

            List<FinalResponse> responses = new ArrayList<>();
            for (Future<FinalResponse> future : futures) {
                try {
                    responses.add(future.get());
                } catch (ExecutionException ex) {
                    log.error("Account processing failed", ex.getCause());
                    meterRegistry.counter("orchestrator.account.failure").increment();
                    throw new RuntimeException(ex.getCause());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Fan-out interrupted", ex);
                }
            }

            context.setFinalResponses(responses);

        } finally {
            overallTimer.stop(meterRegistry.timer("orchestrator.fanout.duration"));
        }
    }

    private FinalResponse processAccount(MdmAccountResponse account) {
        Timer.Sample timer = Timer.start(meterRegistry);
        try {
            log.info("Processing account: {}", account.accountNumber());
            String token = ccslClient.tokenize(account.accountNumber());
            List<CardResponse> cards = ccslClient.getCards(token);
            meterRegistry.counter("orchestrator.account.success").increment();
            return new FinalResponse(account.accountNumber(), account.productCode(), cards);
        } catch (Exception ex) {
            log.error("Error processing account {}", account.accountNumber(), ex);
            meterRegistry.counter("orchestrator.account.failure").increment();
            throw ex;
        } finally {
            timer.stop(meterRegistry.timer("orchestrator.account.duration"));
        }
    }

    @Override
    public String name() { return "FANOUT_ACCOUNTS"; }
}

