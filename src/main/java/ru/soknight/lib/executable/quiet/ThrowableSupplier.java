package ru.soknight.lib.executable.quiet;

@FunctionalInterface
public interface ThrowableSupplier<T> {

    T supply() throws Throwable;

}
