package ru.soknight.lib.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Abstract configuration instance which contains basical methods for working with configuration file
 */
@Data
@NoArgsConstructor
public abstract class AbstractConfiguration {

	private JavaPlugin plugin;
	private InputStream source;
	private String filename;
	private File datafolder;
	
	@Getter
	private FileConfiguration fileConfig;
	
	/**
	 * Configuration file object with methods, implemented from FileConfiguration
	 * @param plugin - owner plugin for configuration file
	 * @param filename - name of destination file and internal (in-jar) resource
	 */
	public AbstractConfiguration(JavaPlugin plugin, String filename) {
		this.plugin = plugin;
		this.filename = filename;
		this.source = plugin.getResource(this.filename);
		this.datafolder = plugin.getDataFolder();
		refresh();
	}
	
	/**
	 * Configuration file object with methods, implemented from FileConfiguration
	 * @param plugin - owner plugin for configuration file
	 * @param source - input stream of custom specified internal resource
	 * @param filename - name of destination file
	 */
	public AbstractConfiguration(JavaPlugin plugin, InputStream source, String filename) {
		this.plugin = plugin;
		this.filename = filename;
		this.source = source;
		this.datafolder = plugin.getDataFolder();
		refresh();
	}
	
	/**
	 * Configuration file object with methods, implemented from FileConfiguration
	 * @param plugin - owner plugin for configuration file
	 * @param datafolder - custom data folder for configuration file
	 * @param source - input stream of custom specified internal resource
	 * @param filename - name of destination file
	 */
	public AbstractConfiguration(JavaPlugin plugin, File datafolder, InputStream source, String filename) {
		this.plugin = plugin;
		this.filename = filename;
		this.source = source;
		this.datafolder = datafolder;
		refresh();
	}
	
	/**
	 * Refreshing file and file configuration
	 * Specify 'refresh(true)' to enable additional info output
	 */
	public void refresh() {
		refresh(false);
	}
	
	/**
	 * Refreshing file and file configuration
	 * @param verbose - should this method send info messages using plugin's logger
	 */
	public void refresh(boolean verbose) {
		Logger logger = plugin.getLogger();
		
		if(datafolder == null) {
			logger.severe("SKLib received null data folder, check this plugin.");
			return;
		}
		
		if(!datafolder.isDirectory()) {
			datafolder.mkdirs();
			if(verbose) logger.info("Created new data folder.");
		}
		
		File file = new File(datafolder, filename);
		if(!file.exists()) {
			try {
				if(source == null) {
					file.createNewFile();
				} else {
					Files.copy(source, file.toPath());
				}
				
				if(verbose)
					logger.info("Created new file '" + filename + "'.");
			} catch (IOException e) {
				logger.severe("Failed create file '" + filename + "' in " + datafolder.getPath() + "':" + e.getMessage());
				if(verbose)
					e.printStackTrace();
				return;
			}
		}
		
		this.fileConfig = YamlConfiguration.loadConfiguration(file);
	}
	
	/*
	 * Methods from FileConfiguration
	 */
	
	/**
	 * Getting child section from file
	 * @param section - target section
	 * @return child section if getting of it is possible (may be null)
	 */
	public ConfigurationSection getSection(String section) {
		return fileConfig.getConfigurationSection(section);
	}
	
	/**
	 * Getting string from file
	 * @param section - section with target string value in file
	 * @return received string or null if section not contains string value
	 */
	public String getString(String section) {
		return fileConfig.getString(section);
	}
	
	/**
	 * Getting string from file
	 * @param section - section with target string value in file
	 * @param def - default output value if section not contains string value
	 * @return received string or 'def' value if section not contains string value
	 */
	public String getString(String section, String def) {
		return fileConfig.getString(section, def);
	}
	
	/**
	 * Getting string from file and replacing '&#' to color codes
	 * @param section - section with target string value in file
	 * @return received string or null if section not contains string value
	 */
	public String getColoredString(String section) {
		String string = fileConfig.getString(section);
		return string == null ? null : string.replace("&", "\u00a7");
	}
	
	/**
	 * Getting string from file and replacing '&#' to color codes
	 * @param section - section with target string value in file
	 * @param def - default output value if section not contains string value
	 * @return received string or 'def' value if section not contains string value
	 */
	public String getColoredString(String section, String def) {
		String string = fileConfig.getString(section, def);
		return string == null ? null : string.replace("&", "\u00a7");
	}
	
	/**
	 * Getting integer from file
	 * @param section - section with target integer value in file
	 * @return received integer or null if section not contains integer value
	 */
	public int getInt(String section) {
		return fileConfig.getInt(section);
	}
	
	/**
	 * Getting integer from file
	 * @param section - section with target integer value in file
	 * @param def - default output value if section not contains integer value
	 * @return received integer or 'def' value if section not contains integer value
	 */
	public int getInt(String section, int def) {
		return fileConfig.getInt(section, def);
	}
	
	/**
	 * Getting double from file
	 * @param section - section with target double value in file
	 * @return received double or null if section not contains double value
	 */
	public double getDouble(String section) {
		return fileConfig.getDouble(section);
	}
	
	/**
	 * Getting double from file
	 * @param section - section with target double value in file
	 * @param def - default output value if section not contains double value
	 * @return received double or 'def' value if section not contains double value
	 */
	public double getDouble(String section, double def) {
		return fileConfig.getDouble(section, def);
	}
	
	/**
	 * Getting float from file
	 * @param section - section with target float value in file
	 * @return received float or null if section not contains float value
	 */
	public float getFloat(String section) {
		return (float) fileConfig.getDouble(section);
	}
	
	/**
	 * Getting float from file
	 * @param section - section with target float value in file
	 * @param def - default output value if section not contains float value
	 * @return received float or 'def' value if section not contains float value
	 */
	public float getFloat(String section, float def) {
		return (float) fileConfig.getDouble(section, def);
	}
	
	/**
	 * Getting boolean from file
	 * @param section - section with target boolean value in file
	 * @return received boolean or null if section not contains boolean value
	 */
	public Boolean getBoolean(String section) {
		return fileConfig.getBoolean(section);
	}
	
	/**
	 * Getting boolean from file
	 * @param section - section with target integer value in file
	 * @param def - default output value if section not contains boolean value
	 * @return received boolean or 'def' value if section not contains boolean value
	 */
	public boolean getBoolean(String section, boolean def) {
		return fileConfig.getBoolean(section, def);
	}
	
	/**
	 * Getting strings list from file
	 * @param section - section with target strings list in file
	 * @return received strings list or null if section not contains strings list
	 */
	public List<String> getList(String section) {
		return fileConfig.getStringList(section);
	}
	
	/**
	 * Getting strings list from file and replacing '&#' to color codes
	 * @param section - section with target strings list in file
	 * @return received strings list or null if section not contains strings list
	 */
	public List<String> getColoredList(String section) {
		List<String> list = fileConfig.getStringList(section);
		if(list == null) return null;
		
		List<String> colored = new ArrayList<>();
		list.forEach(s -> colored.add(s.replace("&", "\u00a7")));
		return colored;
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
	 * Formatting strings list using specified replaces objects array
	 * @param list - target list to format
	 * @param replaces - objects array of replaces formatted as '..., key, value, ...'
	 * @return formatted strings list with replaced placeholders
	 */
	public List<String> formatList(List<String> list, Object... replaces) {
		if(list == null || replaces == null || list.isEmpty()) return list;
		
		return list.stream()
				.map(s -> format(s, replaces))
				.collect(Collectors.toList());
	}
	
}
