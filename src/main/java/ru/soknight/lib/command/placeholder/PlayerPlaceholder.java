package ru.soknight.lib.command.placeholder;

import ru.soknight.lib.configuration.Messages;

/**
 * Placeholder of something player
 */
public class PlayerPlaceholder extends Placeholder {

	/**
	 * Creating player placeholder with custom text
	 * @param text - custom text of placeholder
	 */
	public PlayerPlaceholder(String text) {
		super(text);
	}
	
	/**
	 * Creating player placeholder with default text from section 'help.placeholders.player'
	 * @param messages - messages configuration with placeholder's text
	 */
	public PlayerPlaceholder(Messages messages) {
		super(messages.get("help.placeholders.player"));
	}
	
}
