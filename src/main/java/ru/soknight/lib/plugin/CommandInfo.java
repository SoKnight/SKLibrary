package ru.soknight.lib.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import ru.soknight.lib.argument.CommandArguments;
import ru.soknight.lib.command.enhanced.StandaloneExecutor;
import ru.soknight.lib.configuration.Messages;
import ru.soknight.lib.cooldown.preset.LitePlayersCooldownStorage;
import ru.soknight.lib.cooldown.preset.PlayersCooldownStorage;
import ru.soknight.lib.format.DateFormatter;

public class CommandInfo extends StandaloneExecutor {
	
	private final PluginDescriptionFile description;
	private final PlayersCooldownStorage cooldownStorage;
	private final DateFormatter dateFormatter;

	public CommandInfo(SKLibraryPlugin plugin, Messages messages) {
		super("sklibrary", messages);
		
		this.description = plugin.getDescription();
		this.cooldownStorage = new LitePlayersCooldownStorage(60);
		this.dateFormatter = new DateFormatter();
		
		super.setPermission("sklibrary.info");
		super.register(plugin);
	}

	@Override
	public void executeCommand(CommandSender sender, CommandArguments args) {
		String name = sender.getName();
		
		// Checks if sender has cooldown or not
		if(cooldownStorage.hasCooldown(name)) {
			long remained = cooldownStorage.getRemainedTime(name);
			
			if(remained != 0) {
				// Displaying remained time
				String formatted = dateFormatter.format(remained / 1000);
				sender.sendMessage(ChatColor.RED + "You will can use that after " + formatted + ".");
				return;
			// Removes useless nullified cooldown
			} else cooldownStorage.removeCooldown(name);
		}
		
		// Resets player's cooldown
		cooldownStorage.refreshResetDate(name);
		
		String pversion = description.getVersion();
		
		sender.sendMessage(ChatColor.GRAY + "   SoKnight's library information");
		sender.sendMessage(" Plugin version: " + ChatColor.AQUA + pversion);
		sender.sendMessage(" Author & Developer: " + ChatColor.AQUA + "SoKnight");
		sender.sendMessage(" Github: " + ChatColor.AQUA + "https://github.com/SoKnight/SKLibrary");
		sender.sendMessage(" ");
	}

}
