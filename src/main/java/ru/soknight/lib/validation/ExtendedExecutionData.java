package ru.soknight.lib.validation;

import org.bukkit.command.CommandSender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.soknight.lib.argument.CommandArguments;

/**
 * Extended execution data which stores additional data array
 */
@Getter
@AllArgsConstructor
public class ExtendedExecutionData implements HoldingExecutionData {

	private CommandSender sender;
	private CommandArguments args;
	private String[] data;
	
	@Override
	public String getString(int index) {
		return data.length > index ? data[index] : null;
	}

	@Override
	public Integer getInt(int index) {
		String data = getString(index);
		if(data == null) return null;
		
		try {
			return Integer.parseInt(data);
		} catch (NumberFormatException ignored) {
			return null;
		}
	}
	
}
