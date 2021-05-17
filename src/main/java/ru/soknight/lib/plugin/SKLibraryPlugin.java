package ru.soknight.lib.plugin;

import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.configuration.Messages;

public class SKLibraryPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		// Allowing only ORMLite errors logging
		setORMLiteLogLevel(Log.Level.ERROR);

		Configuration config = new Configuration(this, "config.yml");
		if(!config.getBoolean("enabled"))
			return;
		
		Messages messages = new Messages(this, "messages.yml");
		new CommandInfo(this, messages);
	}

	public void setORMLiteLogLevel(Log.Level level) {
		Logger.setGlobalLogLevel(level);
	}
	
}
