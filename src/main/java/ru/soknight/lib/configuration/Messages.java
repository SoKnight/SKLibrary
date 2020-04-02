package ru.soknight.lib.configuration;

import java.io.File;
import java.io.InputStream;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Configuration of messages file which contains methods for working with plugin messages
 */
public class Messages extends AbstractConfiguration {
	
	/**
	 * Messages file object with methods from Configuration and messages formatter
	 * @param plugin - owner plugin for configuration file
	 * @param filename - name of destination file and internal (in-jar) resource
	 */
	public Messages(JavaPlugin plugin, String filename) {
		super(plugin, filename);
	}
	
	/**
	 * Messages file object with methods from Configuration and messages formatter
	 * @param plugin - owner plugin for configuration file
	 * @param source - input stream of custom specified internal resource
	 * @param filename - name of destination file
	 */
	public Messages(JavaPlugin plugin, InputStream source, String filename) {
		super(plugin, source, filename);
	}
	
	/**
	 * Messages file object with methods from Configuration and messages formatter
	 * @param plugin - owner plugin for configuration file
	 * @param datafolder - custom data folder for configuration file
	 * @param source - input stream of custom specified internal resource
	 * @param filename - name of destination file
	 */
	public Messages(JavaPlugin plugin, File datafolder, InputStream source, String filename) {
		super(plugin, datafolder, source, filename);
	}
	
	/**
	 * Getting colored message from file without
	 * @param section - section with target message in file
	 * @return formatted string with replaced placeholders or message about not exist message
	 */
	public String get(String section) {
		String def = "Couldn't get message by section '" + section + "' :(";
		String message = getColoredString(section, def);
		return message;
	}
	
	/**
	 * Formatting your custom message
	 * @param section - section with target message in file
	 * @param replaces - array of string with this syntax: placeholder value placeholder value...
	 * @return formatted string with replaced placeholders
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
	 * @param section - section with target message in file
	 * @param replaces - array of string with this syntax: placeholder value placeholder value...
	 * @return formatted string with replaced placeholders or message about not exist message
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
	 * @param sender - message receiver
	 * @param message - message which will be sent
	 */
	public void send(CommandSender sender, String message) {
		if(sender == null || message == null) return;
		sender.sendMessage(message);
	}
	
	/**
	 * Getting message from section and sending it to sender
	 * @param sender - message receiver
	 * @param section - section with message which will be sent
	 */
	public void getAndSend(CommandSender sender, String section) {
		if(sender == null || section == null) return;
		sender.sendMessage(get(section));
	}
	
	/**
	 * Getting and formatting message and sending it to sender
	 * @param sender - message receiver
	 * @param section - section with message which will be sent
	 */
	public void sendFormatted(CommandSender sender, String section, Object... replaces) {
		if(sender == null || section == null) return;
		
		if(replaces == null || replaces.length == 0) sender.sendMessage(get(section));
		else sender.sendMessage(getFormatted(section, replaces));
	}
	
}
