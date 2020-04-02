package ru.soknight.lib.command.placeholder;

import ru.soknight.lib.configuration.Messages;

/**
 * Placeholder of something name
 */
public class NamePlaceholder extends Placeholder {

	/**
	 * Creating name placeholder with custom text
	 * @param text - custom text of placeholder
	 */
	public NamePlaceholder(String text) {
		super(text);
	}
	
	/**
	 * Creating name placeholder with default text from section 'help.placeholders.name'
	 * @param messages - messages configuration with placeholder's text
	 */
	public NamePlaceholder(Messages messages) {
		super(messages.get("help.placeholders.name"));
	}
	
}
