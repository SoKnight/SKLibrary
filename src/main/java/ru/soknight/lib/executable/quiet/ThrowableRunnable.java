package ru.soknight.lib.executable.quiet;

@FunctionalInterface
public interface ThrowableRunnable {

    void run() throws Throwable;

}
