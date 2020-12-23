package ru.soknight.lib.command.enhanced;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import ru.soknight.lib.argument.CommandArguments;
import ru.soknight.lib.command.response.CommandResponseType;
import ru.soknight.lib.configuration.Messages;

/**
 * The enhanced configurable command executor
 */
public abstract class EnhancedExecutor extends ExecutionHelper {

	private final Messages messages;
	private final Map<CommandResponseType, String> responses;
	
	private String permission = null;
	private int requiredArgsCount = 0;
	private boolean playerOnly = false;
	
	/**
	 * The new enhanced executor instance
	 * @param messages The messages configuration for some default command response messages
	 */
	public EnhancedExecutor(Messages messages) {
		this.messages = messages;
		this.responses = new HashMap<>();
	}
	
	/**
	 * <b>It's the service method, which will be used by subcommands handler!</b>
	 * <p>
	 * If you want to override execution method, you must use the
	 * {@link EnhancedExecutor#executeCommand(CommandSender, CommandArguments)}
	 * @param sender The command sender
	 * @param args The specified command arguments
	 */
	public void validateAndExecute(CommandSender sender, CommandArguments args) {
		// Checks for player-only flag
		if(playerOnly && !isPlayer(sender)) {
			sendResponseMessage(sender, CommandResponseType.ONLY_FOR_PLAYERS);
			return;
		}
		
		// Checks for permission
		if(permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
			sendResponseMessage(sender, CommandResponseType.NO_PERMISSIONS);
			return;
		}
		
		// Checks for arguments count
		if(requiredArgsCount != 0 && requiredArgsCount > args.size()) {
			sendResponseMessage(sender, CommandResponseType.WRONG_SYNTAX);
			return;
		}
		
		executeCommand(sender, args);
	}
	
	/**
	 * <b>It's the service method, which will be used by subcommands handler!</b>
	 * <p>
	 * If you want to override execution method, you must use the
	 * {@link EnhancedExecutor#executeTabCompletion(CommandSender, CommandArguments)}
	 * @param sender The command sender
	 * @param args The specified command arguments
	 * @return The list of tab-completions as a {@link List} of strings
	 */
	public List<String> validateAndTabComplete(CommandSender sender, CommandArguments args) {
		// Checks for player-only flag
		if(playerOnly && !isPlayer(sender))
			return null;
		
		// Checks for permission
		if(permission != null && !permission.isEmpty() && !sender.hasPermission(permission))
			return null;
		
		return executeTabCompletion(sender, args);
	}
	
	/**
	 * Sends the command response message to command sender
	 * <p>
	 * If this message is not specified, method will send the raw response type enum as string
	 * @param sender The command sender (receiver for message)
	 * @param type The type of command response message
	 */
	public void sendResponseMessage(CommandSender sender, CommandResponseType type) {
		if(type == null) return;
		
		String message = responses.get(type);
		String response = message != null ? message : ChatColor.RED + type.toString().toLowerCase();
		
		if(response != null)
			sender.sendMessage(response);
	}
	
	/**
	 * Executes the command for sender
	 * <p>
	 * <u>This method must be overrided</u> by your executor class
	 * @param sender The command sender
	 * @param args The specified command arguments
	 * @return The list of tab-completions as a {@link List} of strings
	 */
	protected abstract void executeCommand(CommandSender sender, CommandArguments args);
	
	/**
	 * Executes the tab-completion for command sender
	 * <p>
	 * This method must be overrided if you want use custom tab-completions
	 * @param sender The command sender
	 * @param args The specified command arguments
	 * @return The list of tab-completions as a {@link List} of strings
	 */
	protected List<String> executeTabCompletion(CommandSender sender, CommandArguments args) {
		return null;
	}
	
	/**
	 * Gets the current response message for this response type
	 * @param type The type of command response
	 * @return The current message for this type (may be null)
	 * 
	 * @see EnhancedExecutor#setResponseMessage(CommandResponseType, String)
	 */
	public String getResponseMessage(CommandResponseType type) {
		return responses.get(type);
	}
	
	/**
	 * Sets the response message which was be set for this response type
	 * @param type The type of command response
	 * @param message The new response message for this type
	 * 
	 * @see EnhancedExecutor#getResponseMessage(CommandResponseType)
	 */
	public void setResponseMessage(CommandResponseType type, String message) {
		responses.put(type, message);
	}
	
	/**
	 * Sets the response message by key from messages configuration which was be set for this response type
	 * <p>
	 * If specified {@link Messages} configuration in the
	 * @param type The type of command response
	 * @param message The new response message for this type
	 * 
	 * @see EnhancedExecutor#getResponseMessage(CommandResponseType)
	 */
	public void setResponseMessageByKey(CommandResponseType type, String messageKey) {
		responses.put(type, messages != null ? messages.get(messageKey) : null);
	}
	
	/**
	 * Gets the used {@link Messages} configuration for some command response messages
	 * @return The used messages configuration (required value, but may be null)
	 */
	public Messages getMessages() {
		return messages;
	}
	
	/**
	 * Gets the command permission which is required for successfully command execution
	 * @return The current command permission (optional value, may be null)
	 * 
	 * @see EnhancedExecutor#setPermission(String)
	 */
	public String getPermission() {
		return permission;
	}
	
	/**
	 * Gets the count of arguments which is required for successfully command execution
	 * @return The current required arguments count (optional value, is 0 by default)
	 * 
	 * @see EnhancedExecutor#setRequiredArgsCount(int)
	 */
	public int getRequiredArgsCount() {
		return requiredArgsCount;
	}
	
	/**
	 * Checks if this command currently has flag player-only or not
	 * @return The player-only flag status (true or false)
	 * 
	 * @see EnhancedExecutor#setPlayerOnly(boolean)
	 */
	public boolean isPlayerOnly() {
		return playerOnly;
	}
	
	/**
	 * Sets the command permission which is required for successfully command execution
	 * @param permission The command required permission
	 * 
	 * @see EnhancedExecutor#getPermission()
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	/**
	 * Sets the count of arguments which is required for successfully command execution
	 * @param requiredArgsCount The command required arguments count
	 * 
	 * @see EnhancedExecutor#getRequiredArgsCount()
	 */
	public void setRequiredArgsCount(int requiredArgsCount) {
		this.requiredArgsCount = requiredArgsCount;
	}
	
	/**
	 * Sets the player-only flag which determines that this command can be executed only by player
	 * @param playerOnly The player-only flag value
	 * 
	 * @see EnhancedExecutor#isPlayerOnly()
	 */
	public void setPlayerOnly(boolean playerOnly) {
		this.playerOnly = playerOnly;
	}
	
}
