package ru.soknight.lib.validation;

import org.bukkit.command.CommandSender;

import ru.soknight.lib.argument.CommandArguments;

/**
 * Abstract execution data which should be extends by your execution data, if you want share custom parameters
 */
public interface CommandExecutionData {

	/**
	 * Command sender getter
	 * @return sender of command
	 */
	CommandSender getSender();
	
	/**
	 * Command arguments getter
	 * @return arguments of command
	 */
	CommandArguments getArgs();
	
}
