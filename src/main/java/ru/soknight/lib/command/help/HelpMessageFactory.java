package ru.soknight.lib.command.help;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import ru.soknight.lib.configuration.Messages;

/**
 * Help message factory for building help message
 */
public class HelpMessageFactory {

	private final HelpMessage helpMessage;
	@Getter @Setter private String mask, header, body, footer;
	
	/**
	 * Creating new help message factory with default header & footer & body format & mask
	 * @param messages - messages configuration
	 */
	public HelpMessageFactory(Messages messages) {
		this.helpMessage = new HelpMessage("%command%");
		
		this.header = messages.get("help.header");
		this.body = messages.get("help.body");
		this.footer = messages.get("help.footer");
		this.mask = "%command%";
		
		appendWithoutPermission(header);
	}
	
	/**
	 * Creating new help message factory with default header & footer & body format and custom mask
	 * @param messages - messages configuration
	 * @param mask - mask of permissions with %command% placeholder (ex. "eplus.command.%command%")
	 */
	public HelpMessageFactory(Messages messages, String mask) {
		this.helpMessage = new HelpMessage(mask);
		
		this.header = messages.get("help.header");
		this.body = messages.get("help.body");
		this.footer = messages.get("help.footer");
		this.mask = mask;
		
		appendWithoutPermission(header);
	}
	
	/**
	 * Appending new message to help message as item without permission
	 * @param message - new message to add
	 * @param updated factory object
	 */
	public HelpMessageFactory appendWithoutPermission(String message) {
		if(message == null) return this;
		
		helpMessage.appendWithoutPermission(message);
		return this;
	}
	
	/**
	 * Appending new message to help message as item with default permission
	 * @param message - new message to add
	 * @param command - command name
	 * @return updated factory object
	 */
	public HelpMessageFactory appendWithDefaultPermission(String message, String command) {
		if(message == null) return this;
		
		helpMessage.appendWithDefaultPermission(message, command);
		return this;
	}
	
	/**
	 * Appending new message to help message as item with custom permission
	 * @param message - new message to add
	 * @param permission - custom permission
	 * @return updated factory object
	 */
	public HelpMessageFactory appendWithCustomPermission(String message, String permission) {
		if(message == null) return this;
		
		helpMessage.appendWithCustomPermission(message, permission);
		return this;
	}
	
	/**
	 * Appending new help message item to help message
	 * @param item - new help message item
	 * @return updated factory object
	 */
	public HelpMessageFactory appendItem(HelpMessageItem item) {
		return appendItem(item, false);
	}
	
	/**
	 * Appending new help message item to help message
	 * @param item - new help message item
	 * @param defaultPermission - should automaticly set formatted by mask permission
	 * @return updated factory object
	 */
	public HelpMessageFactory appendItem(HelpMessageItem item, boolean defaultPermission) {
		if(item == null) return this;
		
		String message = item.format(body);
		if(defaultPermission) helpMessage.appendWithDefaultPermission(message, item.getCommand());
		else helpMessage.appendWithCustomPermission(message, item.getPermission());
		return this;
	}
	
	/**
	 * Appending new help message items to help message
	 * @param items - array of help message items
	 * @return updated factory object
	 */
	public HelpMessageFactory appendItems(HelpMessageItem... items) {
		if(items == null || items.length == 0) return this;
		
		Arrays.stream(items).forEach(i -> {
			String message = i.format(body);
			helpMessage.appendWithCustomPermission(message, i.getPermission());
		});
		return this;
	}
	
	/**
	 * Appending new help message items to help message
	 * @param defaultPermissions - should automaticly set formatted by mask permissions
	 * @param items - array of help message items
	 * @return updated factory object
	 */
	public HelpMessageFactory append(boolean defaultPermissions, HelpMessageItem... items) {
		if(items == null || items.length == 0) return this;
		
		Arrays.stream(items).forEach(i -> {
			String message = i.format(body);
			helpMessage.appendWithDefaultPermission(message, i.getCommand());
		});
		return this;
	}
	
	/**
	 * Finishing help message creation and getting it
	 * @return completed help message
	 */
	public HelpMessage build() {
		appendWithoutPermission(footer);
		return this.helpMessage;
	}
	
}
