package ru.soknight.lib.executable.quiet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractQuietExecutor {

    protected final ThrowableHandler throwableHandler;

    protected CompletableFuture<Void> runQuietlyAsync(ThrowableRunnable runnable) {
        return CompletableFuture.runAsync(() -> runQuietly(runnable));
    }

    protected void runQuietly(ThrowableRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            if(throwableHandler != null)
                throwableHandler.handle(throwable);
        }
    }

    protected <T> CompletableFuture<T> supplyQuietlyAsync(ThrowableSupplier<T> supplier) {
        return CompletableFuture.supplyAsync(() -> supplyQuietly(supplier));
    }

    protected <T> T supplyQuietly(ThrowableSupplier<T> supplier) {
        try {
            return supplier.supply();
        } catch (Throwable throwable) {
            if(throwableHandler != null)
                throwableHandler.handle(throwable);
            return null;
        }
    }
    
}
