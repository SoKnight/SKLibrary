package ru.soknight.lib.command.enhanced.help.command;

import java.util.List;

import org.bukkit.command.CommandSender;

import ru.soknight.lib.argument.CommandArguments;
import ru.soknight.lib.command.enhanced.EnhancedExecutor;
import ru.soknight.lib.command.enhanced.help.HelpMessageFactory;
import ru.soknight.lib.command.enhanced.help.line.HelpLine;
import ru.soknight.lib.configuration.Messages;

public abstract class EnhancedHelpExecutor extends EnhancedExecutor {

	private final HelpMessageFactory factory;
	private final Messages messages;
	
	private String header, footer;
	private List<HelpLine> lines;
	
	public EnhancedHelpExecutor(Messages messages) {
		super(messages);
		
		this.factory = new HelpMessageFactory(messages);
		this.messages = messages;
	}
	
	protected HelpMessageFactory factory() {
		return factory;
	}
	
	protected void completeMessage() {
		this.lines = factory.getMessageContent();
	}
	
	protected void sendHelpMessage(CommandSender receiver) {
		StringBuilder builder = new StringBuilder();
		
		if(header != null)
			builder.append(header);
		
		if(lines != null && !lines.isEmpty())
			lines.stream()
					.filter(l -> l.isAvailableFor(receiver))
					.map(HelpLine::format)
					.forEach(l -> builder.append("\n").append(l));
		
		if(footer != null)
			builder.append("\n").append(footer);
		
		receiver.sendMessage(builder.toString());
	}
	
	protected void setHeader(String header) {
		this.header = header;
	}
	
	protected void setHeaderFrom(String path) {
		String header = messages.get(path);
		
		this.header = header;
	}
	
	protected void setFooter(String footer) {
		this.footer = footer;
	}
	
	protected void setFooterFrom(String path) {
		String footer = messages.get(path);
		
		this.footer = footer;
	}
	
	@Override
	protected void executeCommand(CommandSender sender, CommandArguments args) {
		sendHelpMessage(sender);
	}

}
