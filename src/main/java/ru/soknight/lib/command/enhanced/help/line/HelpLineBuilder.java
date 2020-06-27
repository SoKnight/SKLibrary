package ru.soknight.lib.command.enhanced.help.line;

import java.util.Arrays;

import lombok.Setter;
import ru.soknight.lib.command.enhanced.help.HelpMessageFactory;
import ru.soknight.lib.configuration.Messages;

@Setter
public class HelpLineBuilder {

	private final Messages messages;
	private final HelpMessageFactory factory;
	private final HelpLine line;
	
	private String argumentPathFormat = "help.arguments.%s";
	private String descriptionPathFormat = "help.descriptions.%s";
	private String permissionFormat = "%s";
	
	public HelpLineBuilder(Messages messages, HelpMessageFactory factory) {
		this.messages = messages;
		this.factory = factory;
		this.line = new HelpLine(messages);
	}
	
	public HelpMessageFactory add() {
		factory.addLine(line);
		return factory;
	}
	
	public HelpLineBuilder argument(String argument) {
		line.addArgument(argument);
		return this;
	}
	
	public HelpLineBuilder arguments(String... arguments) {
		line.addArguments(arguments);
		return this;
	}
	
	public HelpLineBuilder argumentFrom(String section) {
		String path = String.format(argumentPathFormat, section);
		String argument = messages.get(path);
		
		line.addArgument(argument);
		return this;
	}
	
	public HelpLineBuilder argumentsFrom(String... sections) {
		if(sections == null || sections.length == 0)
			return this;
		
		Arrays.stream(sections)
				.filter(s -> s != null)
				.filter(s -> !s.isEmpty())
				.map(s -> String.format(argumentPathFormat, s))
				.map(p -> messages.get(p))
				.forEach(a -> line.addArgument(a));
		
		return this;
	}
	
	public HelpLineBuilder command(String command) {
		if(command == null || command.isEmpty())
			return this;
		
		line.setCommand(command);
		
		String permission = line.getPermission();
		if(permission == null) {
			permission = String.format(permissionFormat, command);
			line.setPermission(permission);
		}
		
		return this;
	}
	
	public HelpLineBuilder command(String command, boolean isDescriptionFromSection) {
		if(command == null || command.isEmpty())
			return this;
		
		line.setCommand(command);
		
		String description = line.getDescription();
		if(description == null) {
			description = isDescriptionFromSection
					? messages.get(String.format(descriptionPathFormat, command))
					: command;
			line.setDescription(description);
		}
		
		String permission = line.getPermission();
		if(permission == null) {
			permission = String.format(permissionFormat, command);
			line.setPermission(permission);
		}
		
		return this;
	}
	
	public HelpLineBuilder description(String description) {
		line.setDescription(description);
		return this;
	}
	
	public HelpLineBuilder descriptionFrom(String section) {
		String path = String.format(descriptionPathFormat, section);
		String description = messages.get(path);
		
		line.setDescription(description);
		return this;
	}
	
	public HelpLineBuilder format(String format) {
		line.setFormat(format);
		return this;
	}
	
	public HelpLineBuilder formatFrom(String path) {
		String format = messages.get(path);
		
		line.setFormat(format);
		return this;
	}
	
	public HelpLineBuilder permission(String permission) {
		String formatted = permissionFormat != null
				? String.format(permissionFormat, permission)
				: permission;
		
		line.setPermission(formatted);
		return this;
	}
	
}
