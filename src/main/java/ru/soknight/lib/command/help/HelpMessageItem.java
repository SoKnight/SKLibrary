package ru.soknight.lib.command.help;

import lombok.Getter;
import ru.soknight.lib.command.placeholder.Placeholder;
import ru.soknight.lib.configuration.Messages;

/**
 * Item of help message which will be used by formatter of this message
 */
@Getter
public class HelpMessageItem {

	private final String command;
	private final String description;
	private final Placeholder[] placeholders;
	private String permission;
	
	/**
	 * Item of help message
	 * @param command - command name
	 * @param description - description for command
	 * @param placeholders - placeholders array (optional)
	 */
	public HelpMessageItem(String command, String description, Placeholder... placeholders) {
		this.command = command;
		this.description = description;
		this.placeholders = placeholders;
	}
	
	/**
	 * Item of help message with default description from messages section 'help.descriptions.%command%'
	 * @param command - command name
	 * @param messages - messages configuration
	 * @param placeholders - placeholders array (optional)
	 */
	public HelpMessageItem(String command, Messages messages, Placeholder... placeholders) {
		this.command = command;
		this.description = messages.get("help.descriptions." + command);
		this.placeholders = placeholders;
	}
	
	/**
	 * Item of help message with description from specified messages section (descriptionKey)
	 * @param command - command name
	 * @param messages - messages configuration
	 * @param descriptionKey - section of description in messages file
	 * @param placeholders - placeholders array (optional)
	 */
	public HelpMessageItem(String command, Messages messages, String descriptionKey, Placeholder... placeholders) {
		this.command = command;
		this.description = messages.get(descriptionKey);
		this.placeholders = placeholders;
	}
	
	/**
	 * Setup permission for current help message item
	 * @param permission - new permission for current help message item
	 * @return current help message item with new permission
	 */
	public HelpMessageItem setPermission(String permission) {
		this.permission = permission;
		return this;
	}
	
	/**
	 * Formatting output help message item using specified format
	 * @param format - format of output message, which contains %command% and %description%
	 * @return formatted help message item
	 */
	public String format(String format) {
		String command = this.command;
		
		if(placeholders != null && placeholders.length != 0)
			for(Placeholder placeholder : placeholders)
				command += " " + placeholder.getText();
		
		return format.replace("%command%", command).replace("%description%", description);
	}
	
}
