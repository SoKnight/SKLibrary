package ru.soknight.lib.command.placeholder;

import ru.soknight.lib.configuration.Messages;

/**
 * Placeholder of some option values
 */
public class ValuesPlaceholder extends Placeholder {

	/**
	 * Creating values placeholder with custom text
	 * @param text - custom text of placeholder
	 */
	public ValuesPlaceholder(String text) {
		super(text);
	}
	
	/**
	 * Creating values placeholder with default text from section 'help.placeholders.values'
	 * @param messages - messages configuration with placeholder's text
	 */
	public ValuesPlaceholder(Messages messages) {
		super(messages.get("help.placeholders.values"));
	}
	
}
