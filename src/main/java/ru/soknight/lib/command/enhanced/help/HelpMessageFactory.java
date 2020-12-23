package ru.soknight.lib.command.enhanced.help;

import java.util.ArrayList;
import java.util.List;

import ru.soknight.lib.command.enhanced.help.line.HelpLine;
import ru.soknight.lib.command.enhanced.help.line.HelpLineBuilder;
import ru.soknight.lib.configuration.Messages;

/**
 * The reloaded factory to create the help message easy
 */
public class HelpMessageFactory {

	private final Messages messages;
	private final List<HelpLine> lines;
	
	private String argumentPathFormat;
	private String descriptionPathFormat;
	private String helpLineFormat;
	private String permissionFormat;
	
	/**
	 * Creates the new factory instance
	 * @param messages The messages instance to get some messages
	 */
	public HelpMessageFactory(Messages messages) {
		this.messages = messages;
		this.lines = new ArrayList<>();
	}
	
	/**
	 * Adds the specified completed {@link HelpLine} to help message
	 * @param line The completed help line
	 * @return The current factory instance with last changes
	 */
	public HelpMessageFactory addLine(HelpLine line) {
		lines.add(line);
		return this;
	}
	
	/**
	 * Creates the new line builder and returns it
	 * @return The builder of new help line
	 */
	public HelpLineBuilder newLine() {
		HelpLineBuilder builder = new HelpLineBuilder(messages, this);
		
		if(argumentPathFormat != null)
			builder.setArgumentPathFormat(argumentPathFormat);
		
		if(descriptionPathFormat != null)
			builder.setDescriptionPathFormat(descriptionPathFormat);
		
		if(helpLineFormat != null)
			builder.format(helpLineFormat);
		
		if(permissionFormat != null)
			builder.setPermissionFormat(permissionFormat);
		
		return builder;
	}

	/**
	 * Gets the help message content as a list of {@link HelpLine}s
	 * @return The help message content
	 */
	public List<HelpLine> getMessageContent() {
		return lines;
	}
	
	/**
	 * Specifies the format of argument path in your messages.yml file
	 * <p>
	 * So, if your arguments are located in the <b>help.arguments section</b>, you should
	 * to specify <b>help.arguments.%s</b> and next line builders will implement it
	 * <p>
	 * Default value is: <b>help.arguments.%s</b>
	 * @param argumentPathFormat The format of argument path
	 * @return The current factory instance with last changes
	 */
	public HelpMessageFactory argumentPathFormat(String argumentPathFormat) {
		this.argumentPathFormat = argumentPathFormat;
		return this;
	}

	/**
	 * Specifies the format of description path in your messages.yml file
	 * <p>
	 * So, if your descriptions are located in the <b>help.descriptions section</b>, you should
	 * to specify <b>help.descriptions.%s</b> and next line builders will implement it
	 * <p>
	 * Default value is: <b>help.descriptions.%s</b>
	 * @param descriptionPathFormat The format of description path
	 * @return The current factory instance with last changes
	 */
	public HelpMessageFactory descriptionPathFormat(String descriptionPathFormat) {
		this.descriptionPathFormat = descriptionPathFormat;
		return this;
	}
	
	/**
	 * Specifies the format of displayable help line in the game chat
	 * <p>
	 * This format <b>must contains</b> the <b>%command%</b> and <b>%description%</b> placeholders!
	 * <p>
	 * Default value is: <b>%command% - %description%</b>
	 * @param helpLineFormat The format of displayable help line
	 * @return The current factory instance with last changes
	 */
	public HelpMessageFactory helpLineFormat(String helpLineFormat) {
		this.helpLineFormat = helpLineFormat;
		return this;
	}
	
	/**
	 * Specifies the format of displayable help line from the
	 * locale {@link Messages} instance by specified path
	 * <p>
	 * Default value is: <b>%command% - %description%</b>
	 * @param path The path in your messages.yml file to this line format
	 * @return The current factory instance with last changes
	 * @see HelpMessageFactory#helpLineFormat(String)
	 */
	public HelpMessageFactory helpLineFormatFrom(String path) {
		String format = messages.get(path);
		
		this.helpLineFormat = format;
		return this;
	}

	/**
	 * Specifies the format of required permission to see the help line
	 * <p>
	 * By default the factory specifies the permission equals command
	 * <p>
	 * So, if you creates new line for command 'staff', permission will be 'staff' by default
	 * <p>
	 * Default value is: <b>%s</b>
	 * @param permissionFormat The format of required permission
	 * @return The current factory instance with last changes
	 */
	public HelpMessageFactory permissionFormat(String permissionFormat) {
		this.permissionFormat = permissionFormat;
		return this;
	}
	
}
