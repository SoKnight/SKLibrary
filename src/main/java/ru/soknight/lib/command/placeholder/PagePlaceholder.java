package ru.soknight.lib.command.placeholder;

import ru.soknight.lib.configuration.Messages;

/**
 * Placeholder of list page
 */
public class PagePlaceholder extends Placeholder {

	/**
	 * Creating page placeholder with custom text
	 * @param text - custom text of placeholder
	 */
	public PagePlaceholder(String text) {
		super(text);
	}
	
	/**
	 * Creating page placeholder with default text from section 'help.placeholders.page'
	 * @param messages - messages configuration with placeholder's text
	 */
	public PagePlaceholder(Messages messages) {
		super(messages.get("help.placeholders.page"));
	}
	
}
