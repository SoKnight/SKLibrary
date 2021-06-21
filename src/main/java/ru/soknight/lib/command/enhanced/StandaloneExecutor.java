package ru.soknight.lib.command.enhanced;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import ru.soknight.lib.argument.BaseCommandArguments;
import ru.soknight.lib.argument.CommandArguments;
import ru.soknight.lib.command.MergedExecutor;
import ru.soknight.lib.configuration.Messages;
import ru.soknight.lib.exception.ParameterValueRequiredException;

import java.util.List;

/**
 * <u>Use it to create a standalone command in your plugin</u>
 * <p>
 * The standalone executor implementation for {@link MergedExecutor}
 * <p>
 * So it's a child executor class of {@link EnhancedExecutor} and it implements all methods from parent class
 */
public abstract class StandaloneExecutor extends EnhancedExecutor implements MergedExecutor {
	
	private final String command;
	
	/**
	 * The new standalone executor instance
	 * @param command The command name
	 * @param messages The messages configuration for some default command response messages
	 */
	public StandaloneExecutor(String command, Messages messages) {
		super(messages);
		
		this.command = command;
	}

	/**
	 * Registers this class as executor for plugin's command
	 * <p>
	 * So by default this class <u>will not be registered as tab-completer</u>.
	 * If you want it, you need to use {@link StandaloneExecutor#register(JavaPlugin, boolean)}
	 * and send 'true' boolean parameter to register the tab-completer
	 * <p>
	 * If the internal <u>command name is null</u> or <u>plugin is null</u> or
	 * <u>plugin hasn't this command</u>, registration of this class will be aborted
	 * @param plugin The plugin which is owner of command
	 */
	public void register(JavaPlugin plugin) {
		register(plugin, false);
	}
	
	/**
	 * Registers this class as executor and tab-completer (if needed) for plugin's command
	 * <p>
	 * If the internal <u>command name is null</u> or <u>plugin is null</u> or
	 * <u>plugin hasn't this command</u>, registration of this class will be aborted
	 * @param plugin The plugin which is owner of command
	 * @param registerTabCompleter Should this class will be registered as tab-completer or not
	 */
	public void register(JavaPlugin plugin, boolean registerTabCompleter) {
		if(command == null || command.isEmpty()) return;
		
		PluginCommand command = plugin.getCommand(this.command);
		if(command == null) return;
		
		command.setExecutor(this);
		if(registerTabCompleter)
			command.setTabCompleter(this);
	}
	
	@Override
	protected abstract void executeCommand(CommandSender sender, CommandArguments args);
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		try {
			return validateAndTabComplete(sender, new BaseCommandArguments(sender, args, parameterRegistry()));
		} catch (ParameterValueRequiredException ignored) {
			return null;
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			validateAndExecute(sender, new BaseCommandArguments(sender, args, parameterRegistry()));
			return true;
		} catch (ParameterValueRequiredException ex) {
			onParameterValueUnspecified(sender, ex.getParameter());
			return true;
		}
	}
	
	/**
	 * Gets the command name which was be specified by the constructor
	 * @return The command name (required value, but may be null)
	 */
	public String getCommand() {
		return command;
	}
	
}
