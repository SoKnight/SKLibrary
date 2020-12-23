package ru.soknight.lib.validation.validator;

import ru.soknight.lib.validation.CommandExecutionData;
import ru.soknight.lib.validation.ValidationResult;

/**
 * Validator for command executor
 */
public interface Validator {
	
	/**
	 * Validate method, which will be called by command executor
	 * @param - command args
	 * @return validation result
	 */
	ValidationResult validate(CommandExecutionData data);
	
}
