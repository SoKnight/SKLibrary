package ru.soknight.lib.command.enhanced.help;

import java.util.ArrayList;
import java.util.List;

import ru.soknight.lib.command.enhanced.help.line.HelpLine;
import ru.soknight.lib.command.enhanced.help.line.HelpLineBuilder;
import ru.soknight.lib.configuration.Messages;

public class HelpMessageFactory {

	private final Messages messages;
	private final List<HelpLine> lines;
	
	private String argumentPathFormat;
	private String descriptionPathFormat;
	private String helpLineFormat;
	private String permissionFormat;
	
	public HelpMessageFactory(Messages messages) {
		this.messages = messages;
		this.lines = new ArrayList<>();
	}
	
	public HelpMessageFactory addLine(HelpLine line) {
		lines.add(line);
		return this;
	}
	
	public HelpLineBuilder newLine() {
		HelpLineBuilder builder = new HelpLineBuilder(messages, this);
		
		if(argumentPathFormat != null) 		builder.setArgumentPathFormat(argumentPathFormat);
		if(descriptionPathFormat != null) 	builder.setDescriptionPathFormat(descriptionPathFormat);
		if(helpLineFormat != null) 			builder.format(helpLineFormat);
		if(permissionFormat != null) 		builder.setPermissionFormat(permissionFormat);
		
		return builder;
	}

	public List<HelpLine> getMessageContent() {
		return lines;
	}
	
	public HelpMessageFactory argumentPathFormat(String argumentPathFormat) {
		this.argumentPathFormat = argumentPathFormat;
		return this;
	}

	public HelpMessageFactory descriptionPathFormat(String descriptionPathFormat) {
		this.descriptionPathFormat = descriptionPathFormat;
		return this;
	}
	
	public HelpMessageFactory helpLineFormat(String helpLineFormat) {
		this.helpLineFormat = helpLineFormat;
		return this;
	}
	
	public HelpMessageFactory helpLineFormatFrom(String path) {
		String format = messages.get(path);
		
		this.helpLineFormat = format;
		return this;
	}

	public HelpMessageFactory permissionFormat(String permissionFormat) {
		this.permissionFormat = permissionFormat;
		return this;
	}
	
}
