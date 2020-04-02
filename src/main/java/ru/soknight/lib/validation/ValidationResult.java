package ru.soknight.lib.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Result of every validator, which will be readed by command execution validator
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ValidationResult {

	private final boolean passed;
	private String message;
	
}
