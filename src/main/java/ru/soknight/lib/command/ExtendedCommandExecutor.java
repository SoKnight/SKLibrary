package ru.soknight.lib.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import lombok.Getter;
import ru.soknight.lib.argument.BaseCommandArguments;
import ru.soknight.lib.configuration.Messages;

/**
 * Extended subcommand which extends ValidatableCommand
 */
@Getter
public abstract class ExtendedCommandExecutor extends ExtendedSubcommandExecutor implements CommandExecutor, TabCompleter {
	
	private final Messages messages;
	
	public ExtendedCommandExecutor(Messages messages) {
		super(messages);
		this.messages = messages;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		executeCommand(sender, new BaseCommandArguments(args));
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return executeTabCompletion(sender, new BaseCommandArguments(args));
	}
	
}
