package ru.soknight.lib.executable.quiet;

import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

@FunctionalInterface
public interface ThrowableHandler {

    static ThrowableHandler createForDatabase(Plugin plugin) {
        return createForDatabase(plugin.getLogger());
    }

    static ThrowableHandler createForDatabase(Logger logger) {
        return new DatabaseThrowableHandler(logger);
    }

    void handle(Throwable throwable);

}
