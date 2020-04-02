package ru.soknight.lib.command.placeholder;

import ru.soknight.lib.configuration.Messages;

/**
 * Placeholder of something option
 */
public class OptionPlaceholder extends Placeholder {

	/**
	 * Creating option placeholder with custom text
	 * @param text - custom text of placeholder
	 */
	public OptionPlaceholder(String text) {
		super(text);
	}
	
	/**
	 * Creating option placeholder with default text from section 'help.placeholders.option'
	 * @param messages - messages configuration with placeholder's text
	 */
	public OptionPlaceholder(Messages messages) {
		super(messages.get("help.placeholders.option"));
	}
	
}
