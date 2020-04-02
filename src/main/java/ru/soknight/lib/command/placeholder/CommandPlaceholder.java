package ru.soknight.lib.command.placeholder;

import ru.soknight.lib.configuration.Messages;

/**
 * Placeholder of something command
 */
public class CommandPlaceholder extends Placeholder {

	/**
	 * Creating command placeholder with custom text
	 * @param text - custom text of placeholder
	 */
	public CommandPlaceholder(String text) {
		super(text);
	}
	
	/**
	 * Creating command placeholder with default text from section 'help.placeholders.command'
	 * @param messages - messages configuration with placeholder's text
	 */
	public CommandPlaceholder(Messages messages) {
		super(messages.get("help.placeholders.command"));
	}
	
}
