package com.coders.laundry.scheduler;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    private final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(64);

    public CompletableFuture<Void> submit(Runnable runnable, Duration duration) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        scheduledExecutorService.schedule(
            () -> {
                try {
                    runnable.run();
                    future.complete(null);
                } catch (Throwable ex) {
                    future.completeExceptionally(ex);
                }
            },
            duration.toMillis(),
            TimeUnit.MILLISECONDS
        );
        return future;
    }
}
