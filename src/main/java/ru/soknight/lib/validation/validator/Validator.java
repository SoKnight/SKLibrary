package ru.soknight.lib.validation.validator;

import lombok.Data;
import ru.soknight.lib.validation.CommandExecutionData;
import ru.soknight.lib.validation.ValidationResult;

@Data
/**
 * Validator for command executor
 */
public abstract class Validator {
	
	/**
	 * Validate method, which will be called by command executor
	 * @param - command args
	 * @return validation result
	 */
	public abstract ValidationResult validate(CommandExecutionData data);
	
}
