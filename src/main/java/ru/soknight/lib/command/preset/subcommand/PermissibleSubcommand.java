package ru.soknight.lib.command.preset.subcommand;

import ru.soknight.lib.command.enhanced.EnhancedExecutor;
import ru.soknight.lib.command.response.CommandResponseType;
import ru.soknight.lib.configuration.Messages;

/**
 * The simple extension for {@link EnhancedExecutor} with the permission checking..
 * <p>
 * See the sources for additional information and use this preset :D
 */
public abstract class PermissibleSubcommand extends EnhancedExecutor {

	public PermissibleSubcommand(String permission, Messages messages) {
		super(messages);
		
		super.setPermission(permission);
		
		String noperms = messages.get("error.no-permissions");
		
		super.setResponseMessage(CommandResponseType.NO_PERMISSIONS, noperms);
	}
	
}
