package ru.soknight.lib.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import ru.soknight.lib.command.ExtendedCommandExecutor;
import ru.soknight.lib.configuration.Messages;
import ru.soknight.lib.validation.BaseExecutionData;
import ru.soknight.lib.validation.validator.PermissionValidator;

public class CommandInfo extends ExtendedCommandExecutor {
	
	private final PluginDescriptionFile description;

	public CommandInfo(SKLibraryPlugin plugin, Messages messages) {
		super(messages);
		this.description = plugin.getDescription();
		
		addValidators(new PermissionValidator("sklibrary.info"));
	}

	@Override
	public void executeCommand(CommandSender sender, String[] args) {
		if(!validateExecution(new BaseExecutionData(sender, args))) return;
		
		String pversion = description.getVersion();
		
		sender.sendMessage(ChatColor.GRAY + "   SoKnight's library information");
		sender.sendMessage(" Plugin version: " + ChatColor.AQUA + pversion);
		sender.sendMessage(" Developer: " + ChatColor.AQUA + "SoKnight");
		sender.sendMessage(" Github: " + ChatColor.AQUA + "https://github.com/SoKnight/SKLibrary");
		sender.sendMessage(" ");
	}

}
