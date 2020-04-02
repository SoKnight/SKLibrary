package ru.soknight.lib.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.configuration.Messages;
import ru.soknight.lib.logging.PluginLogger;

public class SKLibraryPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		PluginLogger logger = new PluginLogger(this);
		
		Configuration config = new Configuration(this, "config");
		if(!config.getBoolean("enabled")) {
			logger.info("Library plugin functional is unavailable.");
			return;
		}
		
		Messages messages = new Messages(this, "messages");
		
		getCommand("sklibrary").setExecutor(new CommandInfo(this, messages));
		
		logger.info("Library plugin functional is available.");
	}
	
}
