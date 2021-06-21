package ru.soknight.lib.command.preset.standalone;

import ru.soknight.lib.command.enhanced.StandaloneExecutor;
import ru.soknight.lib.command.response.CommandResponseType;
import ru.soknight.lib.configuration.Messages;

/**
 * The simple extension for {@link StandaloneExecutor} with the permission checking..
 * <p>
 * See the sources for additional information and use this preset :D
 */
public abstract class PermissibleCommand extends StandaloneExecutor {

	public PermissibleCommand(String command, String permission, Messages messages) {
		super(command, messages);
		
		super.setPermission(permission);
		super.setResponseMessageByKey(CommandResponseType.NO_PERMISSIONS, "error.no-permissions");
	}
	
}
