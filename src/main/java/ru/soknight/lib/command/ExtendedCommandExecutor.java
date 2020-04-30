package ru.soknight.lib.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import lombok.Getter;
import ru.soknight.lib.argument.ArrayCommandArguments;
import ru.soknight.lib.argument.CommandArguments;
import ru.soknight.lib.configuration.Messages;

/**
 * Extended subcommand which extends ValidatableCommand
 */
@Getter
public abstract class ExtendedCommandExecutor extends ValidatableCommandExecutor implements CommandExecutor, TabCompleter {
	
	private final Messages messages;
	
	public ExtendedCommandExecutor(Messages messages) {
		super(messages);
		this.messages = messages;
	}
	
	/**
	 * Execute command with received sender and args
	 * @param sender - sender for execution
	 * @param args - args for execution
	 */
	public abstract void executeCommand(CommandSender sender, CommandArguments args);
	
	/**
	 * Getting tab completions which will be sent to sender
	 * @param sender - sender for completion handling
	 * @param args - args for completion handling
	 * @return list of completions with will be sent to sender
	 */
	public List<String> executeTabCompletion(CommandSender sender, CommandArguments args) {
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		executeCommand(sender, new ArrayCommandArguments(args));
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return executeTabCompletion(sender, new ArrayCommandArguments(args));
	}
	
}
