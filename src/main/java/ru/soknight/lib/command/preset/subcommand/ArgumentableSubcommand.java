package ru.soknight.lib.command.preset.subcommand;

import ru.soknight.lib.command.response.CommandResponseType;
import ru.soknight.lib.configuration.Messages;

/**
 * The simple extension for {@link PermissibleSubcommand} with the arguments count checking..
 * <p>
 * See the sources for additional information and use this preset :D
 */
public abstract class ArgumentableSubcommand extends PermissibleSubcommand {

	public ArgumentableSubcommand(String parent, String permission, int requiredArgsCount, Messages messages) {
		super(permission, messages);
		
		super.setRequiredArgsCount(requiredArgsCount);
		super.setResponseMessageByKey(
				CommandResponseType.WRONG_SYNTAX,
				"error.wrong-syntax",
				"%command%", parent
		);
	}

	public ArgumentableSubcommand(String permission, int requiredArgsCount, Messages messages) {
		this(null, permission, requiredArgsCount, messages);
	}

}
