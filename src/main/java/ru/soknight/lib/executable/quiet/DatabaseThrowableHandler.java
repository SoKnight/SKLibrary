package ru.soknight.lib.executable.quiet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.logging.Logger;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
final class DatabaseThrowableHandler implements ThrowableHandler {

    private final Logger logger;

    @Override
    public void handle(Throwable throwable) {
        logger.severe("An error occurred while trying to execute a database query:");
        logger.severe(throwable.getMessage());
    }

}
