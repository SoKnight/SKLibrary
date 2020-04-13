package ru.soknight.lib.validation.validator;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import lombok.AllArgsConstructor;
import ru.soknight.lib.validation.CommandExecutionData;
import ru.soknight.lib.validation.HoldingExecutionData;
import ru.soknight.lib.validation.ValidationResult;

/**
 * Checks if target player exist and sends deny message if it's specified
 */
@AllArgsConstructor
public class IsPlayerExistValidator implements Validator {

	private String message;
	
	@Override
	public ValidationResult validate(CommandExecutionData data) {
		ValidationResult failed = new ValidationResult(false, message);
		
		if(!(data instanceof HoldingExecutionData)) return failed;
		
		HoldingExecutionData holding = (HoldingExecutionData) data;
		String name = holding.getString(0);
			
		if(name == null) return failed;
		
		OfflinePlayer player = Bukkit.getOfflinePlayer(name);
		if(player != null) return new ValidationResult(true);
		else return failed;
	}
	
}
