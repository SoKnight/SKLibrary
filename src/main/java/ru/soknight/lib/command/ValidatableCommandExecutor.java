package ru.soknight.lib.command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import ru.soknight.lib.configuration.Messages;
import ru.soknight.lib.validation.BaseExecutionData;
import ru.soknight.lib.validation.CommandExecutionData;
import ru.soknight.lib.validation.ValidationResult;
import ru.soknight.lib.validation.validator.PermissionValidator;
import ru.soknight.lib.validation.validator.Validator;

/**
 * Validatable command which can be validated or not.. logical, isn't it?
 */
public abstract class ValidatableCommandExecutor {

	private final Messages messages;
	private Set<Validator> validators;
	
	public ValidatableCommandExecutor(Messages messages) {
		this.messages = messages;
		this.validators = new HashSet<>();
	}
	
	/**
	 * Adds validators in internal list
	 * @param validators - validators which should be added
	 */
	public void addValidators(Validator... validators) {
		if(validators == null || validators.length == 0) return;
		
		this.validators.addAll(Arrays.stream(validators).collect(Collectors.toSet()));
	}
	
	/**
	 * Checks if execution with specified command args can be valid and sends message if it's specified
	 * @param data - command execution data to check
	 * @return true if execution is valid and false if not
	 */
	public boolean validateExecution(CommandExecutionData data) {
		if(validators.isEmpty()) return true;
		
		for(Validator validator : validators) {
			ValidationResult result = validator.validate(data);
			if(!result.isPassed()) {
				messages.send(data.getSender(), result.getMessage());
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if execution with specified command sender and args can be valid and sends message if it's specified
	 * @param sender - command sender to check
	 * @param args - command args to check
	 * @return true if execution is valid and false if not
	 */
	public boolean validateExecution(CommandSender sender, String[] args) {
		if(validators.isEmpty()) return true;
		
		CommandExecutionData data = new BaseExecutionData(sender, args);
		return validateExecution(data);
	}
	
	/**
	 * Checks if execution is permitted by command permission if it's exist
	 * @param data - command execution data to check
	 * @return true if execution is permitted and false if not
	 */
	public boolean validateTabCompletion(CommandExecutionData data) {
		if(validators.isEmpty()) return true;
		
		for(Validator validator : validators)
			if(validator instanceof PermissionValidator) {
				PermissionValidator pv = (PermissionValidator) validator;
				ValidationResult result = pv.validate(data);
				if(!result.isPassed()) return false;
				else return true;
			}
		
		return true;
	}
	
	/**
	 * Checks if execution is permitted by command permission if it's exist
	 * @param sender - command sender to check
	 * @param args - command args to check
	 * @return true if execution is permitted and false if not
	 */
	public boolean validateTabCompletion(CommandSender sender, String[] args) {
		if(validators.isEmpty()) return true;
		
		CommandExecutionData data = new BaseExecutionData(sender, args);
		return validateTabCompletion(data);
	}
	
}
