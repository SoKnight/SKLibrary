package ru.soknight.lib.validation.validator;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import ru.soknight.lib.validation.CommandExecutionData;
import ru.soknight.lib.validation.ValidationResult;

/**
 * Checks if sender is player and sends deny message if it's specified
 */
@AllArgsConstructor
public class SenderIsPlayerValidator extends Validator {

	private String message;
	
	@Override
	public ValidationResult validate(CommandExecutionData data) {
		CommandSender sender = data.getSender();
		
		if(sender instanceof Player)
			return new ValidationResult(true);
		else return new ValidationResult(false, message);
	}
	
}
