package ru.soknight.lib.command.enhanced;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.soknight.lib.argument.CommandArguments;

/**
 * The collection of useful methods for a command executors
 * <p>
 * All child executors extends this class by default
 */
public abstract class ExecutionHelper {

	/**
	 * Gets the last argument from the command arguments
	 * <p>
	 * Very useful for tab-completors :D
	 * @param args The command arguments object
	 * @return The last argument if it's possible (may be null)
	 */
	public String getLastArgument(CommandArguments args) {
		return getLastArgument(args, false);
	}
	
	/**
	 * Gets the last argument in lower-case (if needed) from the command arguments
	 * <p>
	 * Very useful for tab-completors :D
	 * @param args The command arguments object
	 * @param lowerCase Should the argument to be in the lower-case or not
	 * @return The last argument if it's possible (may be null)
	 */
	public String getLastArgument(CommandArguments args, boolean lowerCase) {
		if(args.isEmpty()) return null;
		
		String arg = args.get(args.size() - 1);
		if(lowerCase)
			arg = arg.toLowerCase();
		
		return arg;
	}
	
	/**
	 * Checks if this command sender is player or not
	 * @param sender The target command sender
	 * @return The 'true' value if sender is player or 'false' if not
	 */
	public boolean isPlayer(CommandSender sender) {
		return sender instanceof Player;
	}
	
	/**
	 * Checks if offline player with this nickname is exists
	 * @param nickname The nickname of target player
	 * @return The 'true' value if offline player is exists or 'false' if not
	 */
	@SuppressWarnings("deprecation")
	public boolean isPlayerExists(String nickname) {
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nickname);
		return offlinePlayer.isOnline() || offlinePlayer.hasPlayedBefore();
	}
	
	/**
	 * Checks if player with this nickname is online
	 * @param nickname The nickname of target player
	 * @return The 'true' value if player is online or 'false' if not
	 */
	public boolean isPlayerOnline(String nickname) {
		return Bukkit.getPlayer(nickname) != null;
	}
	
	/**
	 * Checks if this string is integer or not
	 * @param source The string to check
	 * @return The 'true' value if this string is integer of 'false' if not
	 */
	public boolean isInteger(String source) {
		try {
			Integer.parseInt(source);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Checks if this string is float or not
	 * @param source The string to check
	 * @return The 'true' value if this string is float of 'false' if not
	 */
	public boolean isFloat(String source) {
		try {
			Float.parseFloat(source);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Checks if this string is double or not
	 * @param source The string to check
	 * @return The 'true' value if this string is double of 'false' if not
	 */
	public boolean isDouble(String source) {
		try {
			Double.parseDouble(source);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Checks if this string is boolean or not
	 * @param source The string to check
	 * @return The 'true' value if this string is boolean of 'false' if not
	 */
	public boolean isBoolean(String source) {
		return source.equalsIgnoreCase("true") || source.equalsIgnoreCase("false");
	}
	
}
