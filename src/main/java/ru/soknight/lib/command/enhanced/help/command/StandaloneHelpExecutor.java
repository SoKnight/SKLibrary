package ru.soknight.lib.command.enhanced.help.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.soknight.lib.argument.CommandArguments;
import ru.soknight.lib.command.enhanced.StandaloneExecutor;
import ru.soknight.lib.command.enhanced.help.HelpMessageFactory;
import ru.soknight.lib.command.enhanced.help.line.HelpLine;
import ru.soknight.lib.command.response.CommandResponseType;
import ru.soknight.lib.configuration.Messages;
import ru.soknight.lib.tool.Validate;

import java.util.List;

public abstract class StandaloneHelpExecutor extends StandaloneExecutor {

	private final HelpMessageFactory factory;
	private final Messages messages;
	
	private String header, footer;
	private List<HelpLine> lines;
	
	public StandaloneHelpExecutor(@NotNull String command, @NotNull Messages messages) {
		super(command, messages);
		
		this.factory = new HelpMessageFactory(messages, this::completeMessage);
		this.messages = messages;
	}
	
	protected @NotNull HelpMessageFactory factory() {
		return factory;
	}

	protected void requirePermission(@NotNull String permission) {
		Validate.notEmpty(permission, "permission");
		super.setPermission(permission);
		super.setResponseMessageByKey(CommandResponseType.NO_PERMISSIONS, "error.no-permissions");
	}
	
	protected void completeMessage() {
		this.lines = factory.getMessageContent();
	}
	
	protected void sendHelpMessage(@NotNull CommandSender receiver) {
		if(receiver == null)
			return;

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
	
	protected void setHeader(@NotNull String header) {
		this.header = header;
	}
	
	protected void setHeaderFrom(@NotNull String path) {
		this.header = messages.get(path);
	}
	
	protected void setFooter(@NotNull String footer) {
		this.footer = footer;
	}
	
	protected void setFooterFrom(@NotNull String path) {
		this.footer = messages.get(path);
	}
	
	@Override
	protected void executeCommand(@NotNull CommandSender sender, @NotNull CommandArguments args) {
		sendHelpMessage(sender);
	}

}
