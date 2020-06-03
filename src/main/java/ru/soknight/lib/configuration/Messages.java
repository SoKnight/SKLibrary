package ru.soknight.lib.configuration;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Configuration of messages file which contains methods for working with plugin messages
 */
public class Messages extends AbstractConfiguration {
	
	/**
	 * Messages file object with methods from Configuration and messages formatter
	 * @param plugin Owner plugin for configuration file
	 * @param filename Name of destination file and internal (in-jar) resource
	 */
	public Messages(JavaPlugin plugin, String filename) {
		super(plugin, filename);
	}
	
	/**
	 * Messages file object with methods from Configuration and messages formatter
	 * @param plugin Owner plugin for configuration file
	 * @param source {@link InputStream} of custom specified internal resource
	 * @param filename Name of destination file
	 */
	public Messages(JavaPlugin plugin, InputStream source, String filename) {
		super(plugin, source, filename);
	}
	
	/**
	 * Messages file object with methods from Configuration and messages formatter
	 * @param plugin Owner plugin for configuration file
	 * @param datafolder Custom data folder for configuration file
	 * @param source {@link InputStream} of custom specified internal resource
	 * @param filename Name of destination file
	 */
	public Messages(JavaPlugin plugin, File datafolder, InputStream source, String filename) {
		super(plugin, datafolder, source, filename);
	}
	
	/**
	 * Getting colored message from file without
	 * @param section Section with target message in file
	 * @return Formatted string with replaced placeholders or message about not exist message
	 */
	public String get(String section) {
		String def = "Couldn't get message by section '" + section + "' :(";
		String message = getColoredString(section, def);
		return message;
	}
	
	/**
	 * Formatting your custom message
	 * @param section Section with target message in file
	 * @param replaces Array of string with this syntax: placeholder value placeholder value...
	 * @return Formatted string with replaced placeholders
	 */
	public String format(String message, Object... replaces) {
		if(replaces == null) return message;
		
		int length = replaces.length;
		if(length == 0) return message;
		
		for(int i = 0; i < length; i += 2) {
			if(i == length - 1) continue;
			
			String placeholder = replaces[i].toString();
			String value = replaces[i + 1].toString();
			
			message = message.replace(placeholder, value);
		}
		
		return message;
	}
	
	/**
	 * Formatting exist message from configuration by section key
	 * @param section Section with target message in file
	 * @param replaces Array of string with this syntax: placeholder value placeholder value...
	 * @return Formatted string with replaced placeholders or message about not exist message
	 */
	public String getFormatted(String section, Object... replaces) {
		String message = getColoredString(section);
		
		if(message == null)
			message = "Couldn't get message by section '" + section + "' :(";
		else message = format(message, replaces);
			
		return message;
	}
	
	/**
	 * Sending message to sender
	 * @param sender Message receiver
	 * @param message Message which will be sent
	 */
	public void send(CommandSender sender, String message) {
		send(sender, message, false);
	}
	
	/**
	 * Sending messages array to sender
	 * @param sender Message receiver
	 * @param messages Messages array which will be sent
	 */
	public void send(CommandSender sender, String[] messages) {
		send(sender, messages, false);
	}
	
	/**
	 * Sending messages list to sender
	 * @param sender Message receiver
	 * @param messages Messages list which will be sent
	 */
	public void send(CommandSender sender, List<String> messages) {
		send(sender, messages.toArray(new String[0]), false);
	}
	
	/**
	 * Sending message to sender
	 * @param sender Message receiver
	 * @param message Message which will be sent
	 * @param toActionbar Should method to send this message into player's actionbar if it's possible
	 */
	public void send(CommandSender sender, String message, boolean toActionbar) {
		if(sender == null || message == null) return;
		
		if(toActionbar && sender instanceof Player) {
			BaseComponent[] msg = TextComponent.fromLegacyText(message);
			((Player) sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, msg);
		} else sender.sendMessage(message);
	}
	
	/**
	 * Sending messages array to sender
	 * @param sender Message receiver
	 * @param messages Messages array which will be sent
	 * @param toActionbar Should method to send this messages into player's actionbar if it's possible
	 */
	public void send(CommandSender sender, String[] messages, boolean toActionbar) {
		if(sender == null || messages == null || messages.length == 0) return;
		
		if(toActionbar && sender instanceof Player) {
			Arrays.stream(messages).forEach(message -> {
				BaseComponent[] msg = TextComponent.fromLegacyText(message);
				((Player) sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, msg);
			});
		} else sender.sendMessage(messages);
	}
	
	/**
	 * Sending messages list to sender
	 * @param sender Message receiver
	 * @param messages Messages list which will be sent
	 * @param toActionbar Should method to send this messages into player's actionbar if it's possible
	 */
	public void send(CommandSender sender, List<String> messages, boolean toActionbar) {
		if(sender == null || messages == null || messages.isEmpty()) return;
		
		if(toActionbar && sender instanceof Player) {
			messages.forEach(message -> {
				BaseComponent[] msg = TextComponent.fromLegacyText(message);
				((Player) sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, msg);
			});
		} else sender.sendMessage(messages.toArray(new String[0]));
	}
	
	/**
	 * Getting message from section and sending it to sender
	 * @param sender Message receiver
	 * @param section Section with message which will be sent
	 */
	public void getAndSend(CommandSender sender, String section) {
		if(sender == null || section == null) return;
		
		boolean toActionbar = sender instanceof Player
				? isActionbar(section)
				: false;
		
		send(sender, get(section), toActionbar);
	}
	
	/**
	 * Getting and formatting message and sending it to sender
	 * @param sender Message receiver
	 * @param section Section with message which will be sent
	 * @param replaces Array of string with this syntax: placeholder value placeholder value...
	 */
	public void sendFormatted(CommandSender sender, String section, Object... replaces) {
		if(sender == null || section == null) return;
		
		boolean toActionbar = sender instanceof Player
				? isActionbar(section)
				: false;
		
		if(replaces == null || replaces.length == 0)
			send(sender, get(section), toActionbar);
		else send(sender, getFormatted(section, replaces), toActionbar);
	}
	
	/**
	 * Checking if message from specified section must be sent into player's actionbar or not
	 * @param section Section of message to check
	 * @return 'true' if this message must be sent into player's actionbar or 'false' if not
	 */
	public boolean isActionbar(String section) {
		List<String> actionbared = getList("actionbar");
		if(actionbared == null || actionbared.isEmpty())
			return false;
		
		// Directly contains check
		if(actionbared.contains(section))
			return true;
		
		// Check parent sections
		for(String a : actionbared) {
			if(a.equals(" ") || !a.endsWith(".*"))
				continue;
			
			// If specified parent section then return true
			if(section.startsWith(a.substring(0, a.length() - 1)))
				return true;
		}
			
		return false;
	}
	
}
