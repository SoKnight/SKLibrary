package ru.soknight.lib.command.enhanced.help.line;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import lombok.Data;
import ru.soknight.lib.configuration.Messages;

@Data
public class HelpLine {

	private final Messages messages;
	private final StringBuilder arguments;
	
	private String command;
	private String description;
	private String format;
	private String permission;
	
	public HelpLine(Messages messages) {
		this.messages = messages;
		this.arguments = new StringBuilder();
		this.format = "%command% - %description%";
	}
	
	public void addArgument(String argument) {
		if(argument != null && !argument.isEmpty())
			arguments.append(" ").append(argument);
	}
	
	public void addArguments(String... arguments) {
		if(arguments != null)
			Arrays.stream(arguments).forEach(a -> addArgument(a));
	}
	
	public String format() {
		if(format == null)
			return ChatColor.RED + "Help line format is null :(";
		
		String command = this.command != null ? this.command : "";
		String description = this.description != null ? this.description : "";
		
		if(arguments.length() != 0)
			command += arguments.toString();
		
		return messages.format(format, "%command%", command, "%description%", description);
	}
	
	public boolean isAvailableFor(CommandSender sender) {
		return permission != null && !permission.isEmpty() ? sender.hasPermission(permission) : true;
	}
	
}
