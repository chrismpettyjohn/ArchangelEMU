package com.us.nova.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ManagedTask<T extends ManagedTask<T>> {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean cancelled = new AtomicBoolean(false);
    private ScheduledFuture<?> scheduledTask;

    public final void start() {
        if (running.compareAndSet(false, true)) {
            cancelled.set(false);
            scheduledTask = executor.scheduleAtFixedRate(() -> {
                if (!running.get() || cancelled.get()) {
                    stop();
                    return;
                }
                cycle();
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    public final void stop() {
        if (running.compareAndSet(true, false)) {
            cancelled.set(true);
            if (scheduledTask != null && !scheduledTask.isDone()) {
                scheduledTask.cancel(true);
            }
            executor.shutdownNow();
        }
    }

    public final void cancel() {
        if (cancelled.compareAndSet(false, true)) {
            stop();
        }
    }

    public boolean isRunning() {
        return running.get() && !cancelled.get();
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long delay) {
        if (!executor.isShutdown()) {
            return executor.schedule(() -> {
                if (!isRunning()) return;
                runnable.run();
            }, delay, TimeUnit.SECONDS);
        }
        return null;
    }

    public abstract void cycle();
}