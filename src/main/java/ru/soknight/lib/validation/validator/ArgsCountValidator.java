package ru.soknight.lib.validation.validator;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.soknight.lib.validation.CommandExecutionData;
import ru.soknight.lib.validation.ValidationResult;

/**
 * Checks if command execution args count more than required count and sends deny message if it's specified
 */
@AllArgsConstructor
@RequiredArgsConstructor
public class ArgsCountValidator extends Validator {

	private final int requiredArgsCount;
	private String message;
	
	@Override
	public ValidationResult validate(CommandExecutionData data) {
		String[] args = data.getArgs();
		
		if(args != null && args.length >= requiredArgsCount)
			return new ValidationResult(true);
		else return new ValidationResult(false, message);
	}

}
