package ru.soknight.lib.command.help;

import java.util.Arrays;

import ru.soknight.lib.configuration.Messages;

/**
 * Help message factory for building help message
 */
public class HelpMessageFactory {

	private final HelpMessage helpMessage;
	private final String header, body, footer;
	
	/**
	 * Creating new help message factory with default header, footer and body format from section 'help.*'
	 * @param messages - messages configuration
	 */
	public HelpMessageFactory(Messages messages) {
		this.helpMessage = new HelpMessage();
		
		this.header = messages.get("help.header");
		this.body = messages.get("help.body");
		this.footer = messages.get("help.footer");
		
		append(header);
	}
	
	/**
	 * Appending new message to help message as item without permission
	 * @param message - new message to add
	 */
	public HelpMessageFactory append(String message) {
		if(message == null) return this;
		
		helpMessage.append(message);
		return this;
	}
	
	/**
	 * Appending new help message item to help message
	 * @param item - new help message item
	 * @return
	 */
	public HelpMessageFactory append(HelpMessageItem item) {
		if(item == null) return this;
		
		String message = item.format(body);
		String permission = item.getPermission();
		helpMessage.append(message, permission);
		return this;
	}
	
	/**
	 * Appending new help message items to help message
	 * @param items - array of help message items
	 */
	public HelpMessageFactory append(HelpMessageItem... items) {
		if(items == null || items.length == 0) return this;
		
		Arrays.stream(items).forEach(i -> {
			String message = i.format(body);
			String permission = i.getPermission();
			helpMessage.append(message, permission);
		});
		return this;
	}
	
	/**
	 * Finishing help message creation and getting it
	 * @return completed help message
	 */
	public HelpMessage build() {
		append(footer);
		return this.helpMessage;
	}
	
}
