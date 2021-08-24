package ru.soknight.lib.task;

import org.jetbrains.annotations.NotNull;
import ru.soknight.lib.configuration.Configuration;

public interface ConfiguredTask extends PluginTask {

    @NotNull Configuration getConfig();

    @NotNull String getConfigurationRootPath();

}
