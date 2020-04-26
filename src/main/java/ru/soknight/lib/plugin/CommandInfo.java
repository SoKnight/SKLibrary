package ru.soknight.lib.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import ru.soknight.lib.command.ExtendedCommandExecutor;
import ru.soknight.lib.configuration.Messages;
import ru.soknight.lib.cooldown.preset.LitePlayersCooldownStorage;
import ru.soknight.lib.cooldown.preset.PlayersCooldownStorage;
import ru.soknight.lib.format.DateFormatter;
import ru.soknight.lib.validation.validator.PermissionValidator;

public class CommandInfo extends ExtendedCommandExecutor {
	
	private final PluginDescriptionFile description;
	private final PlayersCooldownStorage cooldownStorage;
	private final DateFormatter dateFormatter;

	public CommandInfo(SKLibraryPlugin plugin, Messages messages) {
		super(messages);
		this.description = plugin.getDescription();
		
		this.cooldownStorage = new LitePlayersCooldownStorage(60);
		this.dateFormatter = new DateFormatter();
		
		addValidators(new PermissionValidator("sklibrary.info"));
	}

	@Override
	public void executeCommand(CommandSender sender, String[] args) {
		if(!validateExecution(sender, args)) return;
		
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
		sender.sendMessage(" Developer: " + ChatColor.AQUA + "SoKnight");
		sender.sendMessage(" Github: " + ChatColor.AQUA + "https://github.com/SoKnight/SKLibrary");
		sender.sendMessage(" ");
	}

}
