package ru.soknight.lib.validation.validator;

import org.bukkit.command.CommandSender;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.soknight.lib.validation.CommandExecutionData;
import ru.soknight.lib.validation.ValidationResult;

/**
 * Checks if sender has specified permission and sends deny message if it's specified
 */
@AllArgsConstructor
@RequiredArgsConstructor
public class PermissionValidator extends Validator {

	private final String permission;
	private String message;
	
	@Override
	public ValidationResult validate(CommandExecutionData data) {
		CommandSender sender = data.getSender();
		
		if(sender.hasPermission(permission))
			return new ValidationResult(true);
		else return new ValidationResult(false, message);
	}

}
