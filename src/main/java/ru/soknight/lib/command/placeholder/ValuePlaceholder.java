package ru.soknight.lib.command.placeholder;

import ru.soknight.lib.configuration.Messages;

/**
 * Placeholder of some option value
 */
public class ValuePlaceholder extends Placeholder {

	/**
	 * Creating value placeholder with custom text
	 * @param text - custom text of placeholder
	 */
	public ValuePlaceholder(String text) {
		super(text);
	}
	
	/**
	 * Creating value placeholder with default text from section 'help.placeholders.value'
	 * @param messages - messages configuration with placeholder's text
	 */
	public ValuePlaceholder(Messages messages) {
		super(messages.get("help.placeholders.value"));
	}
	
}
