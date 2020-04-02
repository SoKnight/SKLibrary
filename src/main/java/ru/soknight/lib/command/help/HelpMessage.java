package ru.soknight.lib.command.help;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import lombok.Getter;

/**
 * Help message object which contains HashMap when key is message and value is required permission.
 * If permission string (value) will be null, checking for permission will be skiped and message will be sent.
 */
public class HelpMessage {
	
	@Getter
	private final Map<String, String> messages;
	
	/**
	 * Creating new help message object which contains HashMap when key is message and value is required permission.
	 * If permission string (value) will be null, checking for permission will be skiped and message will be sent.
	 */
	public HelpMessage() {
		this.messages = new LinkedHashMap<>();
	}
	
	/**
	 * Adding message without required permission into internal messages HashMap
	 * @param message - message to add
	 */
	public void append(String message) {
		this.append(message, null);
	}
	
	/**
	 * Adding message with required permission into internal messages HashMap
	 * @param message - message to add
	 * @param permission - required permission to message viewing
	 */
	public void append(String message, String permission) {
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
