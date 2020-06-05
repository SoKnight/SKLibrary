package ru.soknight.lib.command.preset.standalone;

import ru.soknight.lib.command.response.CommandResponseType;
import ru.soknight.lib.configuration.Messages;

/**
 * The omnipotent extension for {@link ArgumentableCommand} with player-only flag checking :D
 * <p>
 * It's just a checks collection which can save your time!
 * <p>
 * See the sources for additional information and use this preset :D
 */
public abstract class OmnipotentCommand extends ArgumentableCommand {

	public OmnipotentCommand(String command, String parent, String permission, int requiredArgsCount, Messages messages) {
		super(command, parent, permission, requiredArgsCount, messages);
		
		super.setPlayerOnly(true);
		
		String playeronly = messages.get("error.only-for-players");
		
		super.setResponseMessage(CommandResponseType.ONLY_FOR_PLAYERS, playeronly);
	}

}
