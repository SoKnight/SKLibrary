package ru.soknight.lib.command;

import java.util.List;

import org.bukkit.command.CommandSender;

import lombok.Getter;
import ru.soknight.lib.configuration.Messages;

/**
 * Extended subcommand which extends ValidatableCommand
 */
@Getter
public abstract class ExtendedSubcommandExecutor extends ValidatableCommandExecutor {
	
	private final Messages messages;
	
	public ExtendedSubcommandExecutor(Messages messages) {
		super(messages);
		this.messages = messages;
	}
	
	/**
	 * Execute command with received sender and args
	 * @param sender - sender for execution
	 * @param args - args for execution
	 */
	public abstract void executeCommand(CommandSender sender, String[] args);
	
	/**
	 * Getting tab completions which will be sent to sender
	 * @param sender - sender for completion handling
	 * @param args - args for completion handling
	 * @return list of completions with will be sent to sender
	 */
	public abstract List<String> executeTabCompletion(CommandSender sender, String[] args);
	
}
