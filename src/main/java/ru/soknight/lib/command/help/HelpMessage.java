package ru.soknight.lib.command.help;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import lombok.Getter;

/**
 * Help message object which contains HashMap when key is message and value is required permission.
 * If permission string (value) will be null, checking for permission will be skiped and message will be sent.
 */
@Getter
public class HelpMessage {
	
	private final Map<String, String> messages;
	private final String mask;
	
	/**
	 * Creating new help message object which contains HashMap when key is message and value is required permission.
	 * If permission string (value) will be null, checking for permission will be skiped and message will be sent.
	 */
	public HelpMessage(String mask) {
		this.messages = new LinkedHashMap<>();
		this.mask = mask;
	}
	
	/**
	 * Adding message without required permission into internal messages HashMap
	 * @param message - message to add
	 * @param command - name of command
	 */
	public void appendWithoutPermission(String message) {
		this.messages.put(message, null);
	}
	
	/**
	 * Adding message with formatted by mask permission into internal messages HashMap
	 * @param message - message to add
	 * @param command - name of command
	 */
	public void appendWithDefaultPermission(String message, String command) {
		String permission = command != null ? mask.replace("%command%", command) : null;
		this.messages.put(message, permission);
	}
	
	/**
	 * Adding message with required permission into internal messages HashMap
	 * @param message - message to add
	 * @param permission - required permission to message viewing
	 */
	public void appendWithCustomPermission(String message, String permission) {
		this.messages.put(message, permission);
	}
	
	/**
	 * Sending help message to specified receiver if he has required permissions
	 * @param receiver - receiver of help message
	 */
	public void send(CommandSender receiver) {
		this.send(receiver, true);
	}
	
	/**
	 * Sending help message to specified receiver
	 * @param receiver - receiver of help message
	 * @param checkPermissions - should to check if receiver has required permissions
	 */
	public void send(CommandSender receiver, boolean checkPermissions) {
		if(receiver == null || messages.isEmpty()) return;
		
		messages.forEach((msg, perm) -> {
			if(checkPermissions && perm != null && !perm.equals("") && !receiver.hasPermission(perm)) return;
			
			receiver.sendMessage(msg);
		});
		
	}
	
}
