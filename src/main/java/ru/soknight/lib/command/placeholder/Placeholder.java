package ru.soknight.lib.command.placeholder;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Abstract placeholder class for basical placeholders or your custom placeholders
 */
@Getter
@AllArgsConstructor
public abstract class Placeholder {
	
	private final String text;
	
}
