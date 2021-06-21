package ru.soknight.lib.command.preset.standalone;

import ru.soknight.lib.command.response.CommandResponseType;
import ru.soknight.lib.configuration.Messages;

/**
 * The simple extension for {@link PermissibleCommand} with the player-only flag checking..
 * <p>
 * See the sources for additional information and use this preset :D
 */
public abstract class PlayerOnlyCommand extends PermissibleCommand {

	public PlayerOnlyCommand(String command, String permission, Messages messages) {
		super(command, permission, messages);
		
		super.setPlayerOnly(true);
		super.setResponseMessageByKey(CommandResponseType.ONLY_FOR_PLAYERS, "error.only-for-players");
	}

}
