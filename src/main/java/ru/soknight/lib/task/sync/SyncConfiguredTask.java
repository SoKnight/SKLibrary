package ru.soknight.lib.task.sync;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.task.AbstractConfiguredTask;

public abstract class SyncConfiguredTask extends AbstractConfiguredTask {

    protected SyncConfiguredTask(@NotNull Plugin plugin, @NotNull Configuration config, @NotNull String configurationRootPath) {
        super(plugin, config, configurationRootPath);
    }

    @Override
    public void start() {
        if(config.getBoolean(configurationRootPath + ".enabled", true))
            this.bukkitTask = runTaskTimer(false);
    }

}
