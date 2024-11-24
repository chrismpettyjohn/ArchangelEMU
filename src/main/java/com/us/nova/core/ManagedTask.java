package com.us.nova.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ManagedTask<T extends ManagedTask<T>> {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final AtomicBoolean running = new AtomicBoolean(false);

    public final void start() {
        if (running.compareAndSet(false, true)) {
            onStart();
            executor.scheduleAtFixedRate(() -> {
                if (running.get()) {
                    cycle();
                }
            }, 0, 5, TimeUnit.SECONDS);
        }
    }

    public final void stop() {
        if (running.compareAndSet(true, false)) {
            executor.shutdownNow();
            onStop();
        }
    }

    public abstract void cycle();

    protected void onStart() {
        // Default implementation for start, can be overridden
        System.out.println("Task started");
    }

    protected void onStop() {
        // Default implementation for stop, can be overridden
        System.out.println("Task stopped");
    }

    public boolean isRunning() {
        return running.get();
    }
}
