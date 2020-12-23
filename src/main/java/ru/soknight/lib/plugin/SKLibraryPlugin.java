package ru.soknight.lib.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.configuration.Messages;

public class SKLibraryPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		Configuration config = new Configuration(this, "config.yml");
		if(!config.getBoolean("enabled"))
			return;
		
		Messages messages = new Messages(this, "messages.yml");
		new CommandInfo(this, messages);
	}
	
}
