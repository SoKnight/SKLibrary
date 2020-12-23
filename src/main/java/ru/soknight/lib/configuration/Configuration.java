package ru.soknight.lib.configuration;

import java.io.File;
import java.io.InputStream;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.NoArgsConstructor;

/**
 * Configuration for working with configuration files
 */
@NoArgsConstructor
public class Configuration extends AbstractConfiguration {
	
	/**
	 * Configuration file object with methods, implemented from FileConfiguration
	 * @param plugin - owner plugin for configuration file
	 * @param filename - name of destination file and internal (in-jar) resource
	 */
	public Configuration(JavaPlugin plugin, String filename) {
		super(plugin, filename);
	}
	
	/**
	 * Configuration file object with methods, implemented from FileConfiguration
	 * @param plugin - owner plugin for configuration file
	 * @param source - input stream of custom specified internal resource
	 * @param filename - name of destination file
	 */
	public Configuration(JavaPlugin plugin, InputStream source, String filename) {
		super(plugin, source, filename);
	}
	
	/**
	 * Configuration file object with methods, implemented from FileConfiguration
	 * @param plugin - owner plugin for configuration file
	 * @param datafolder - custom data folder for configuration file
	 * @param source - input stream of custom specified internal resource
	 * @param filename - name of destination file
	 */
	public Configuration(JavaPlugin plugin, File datafolder, InputStream source, String filename) {
		super(plugin, datafolder, source, filename);
	}
	
}
