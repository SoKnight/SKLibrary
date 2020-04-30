package ru.soknight.lib.validation;

import org.bukkit.command.CommandSender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.soknight.lib.argument.CommandArguments;

/**
 * Basical execution data which contains command sender and command args
 */
@Getter
@AllArgsConstructor
public class BaseExecutionData implements CommandExecutionData {
	
	private final CommandSender sender;
	private final CommandArguments args;

}
