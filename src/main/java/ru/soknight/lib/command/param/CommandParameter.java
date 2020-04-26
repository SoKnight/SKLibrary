package ru.soknight.lib.command.param;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommandParameter {

	private final String name;
	private final boolean single;

	@Override
	public String toString() {
		return "CommandParameter{name=" + name + ",single=" + single + "}";
	}
	
}
