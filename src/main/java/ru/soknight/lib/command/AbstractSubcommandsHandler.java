package ru.soknight.lib.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;

import ru.soknight.lib.argument.CommandArguments;
import ru.soknight.lib.configuration.Messages;
import ru.soknight.lib.validation.BaseExecutionData;
import ru.soknight.lib.validation.CommandExecutionData;

/**
 * Subcommands handler which automaticly executes any subcommands and also sends deny messages
 */
public abstract class AbstractSubcommandsHandler extends ExtendedCommandExecutor {

	private final Map<String, ExtendedSubcommandExecutor> executors;
	private final String NO_ARGS_MESSAGE, UNKNOWN_SUBCOMMAND_MESSAGE;
	
	/**
	 * Subcommands handler with default no-args and unknown-subcommand error messages.
	 * @param messages - messages configuration for default messages
	 */
	public AbstractSubcommandsHandler(Messages messages) {
		super(messages);
		this.executors = new HashMap<>();
		
		this.NO_ARGS_MESSAGE = messages.get("error.no-args");
		this.UNKNOWN_SUBCOMMAND_MESSAGE = messages.get("error.unknown-subcommand");
	}
	
	/**
	 * Subcommands handler with custom no-args and unknown-subcommand error messages. Specify null as message
	 * argument to say that constructor should use default message from messages configuration.
	 * @param messages - messages configuration for default messages
	 */
	public AbstractSubcommandsHandler(Messages messages, String noArgsMessage, String unknownSubcommandMessage) {
		super(messages);
		this.executors = new HashMap<>();
		
		this.NO_ARGS_MESSAGE = noArgsMessage == null
				? messages.get("error.no-args")
				: noArgsMessage;
		
		this.UNKNOWN_SUBCOMMAND_MESSAGE = unknownSubcommandMessage == null
				? messages.get("error.unknown-subcommand")
				: unknownSubcommandMessage;
	}

	/**
	 * Setup executor for specified subcommand
	 * @param command - target subcommand
	 * @param executor - new subcommand executor
	 * @return current subcommands handler object
	 */
	public AbstractSubcommandsHandler setExecutor(String subcommand, ExtendedSubcommandExecutor executor) {
		this.executors.put(subcommand, executor);
		return this;
	}
	
	@Override
	public void executeCommand(CommandSender sender, CommandArguments args) {
		if(args.isEmpty()) {
			sender.sendMessage(NO_ARGS_MESSAGE);
			return;
		}
		
		String subcommand = args.get(0).toLowerCase();
		if(!this.executors.containsKey(subcommand)) {
			sender.sendMessage(UNKNOWN_SUBCOMMAND_MESSAGE);
			return;
		} else {
			ExtendedSubcommandExecutor executor = this.executors.get(subcommand);
			
			args.remove(0);
			executor.executeCommand(sender, args);
		}
	}
	
	@Override
	public List<String> executeTabCompletion(CommandSender sender, CommandArguments args) {
		if(args.isEmpty() || executors.isEmpty()) return null;
		
		List<String> completions = new ArrayList<>();
		String subcommand = args.get(0).toLowerCase();
		
		CommandExecutionData data = new BaseExecutionData(sender, args);
		
		if(args.size() == 1)
			executors.forEach((s, e) -> {
				if(!s.startsWith(subcommand) || !e.validateTabCompletion(data)) return;
			
				completions.add(s);
			});
		else {
			if(!this.executors.containsKey(subcommand))
				return null;
			else {
				ExtendedSubcommandExecutor executor = this.executors.get(subcommand);
				
				args.remove(0);
				return executor.executeTabCompletion(sender, args);
			}
		}
		
		return completions;
	}
	
}
