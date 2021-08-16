package ru.soknight.lib.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Abstract configuration instance which contains basic methods for working with configuration file
 */
@Getter @Setter
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
	 * @param dataFolder - custom data folder for configuration file
	 * @param source - input stream of custom specified internal resource
	 * @param filename - name of destination file
	 */
	public AbstractConfiguration(JavaPlugin plugin, File dataFolder, InputStream source, String filename) {
		this.plugin = plugin;
		this.filename = filename;
		this.source = source;
		this.datafolder = dataFolder;
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
		return string == null ? null : colorize(string);
	}
	
	/**
	 * Getting string from file and replacing '&#' to color codes
	 * @param section - section with target string value in file
	 * @param def - default output value if section not contains string value
	 * @return received string or 'def' value if section not contains string value
	 */
	public String getColoredString(String section, String def) {
		String string = fileConfig.getString(section, def);
		return string == null ? null : colorize(string);
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
     * Getting long from file
     * @param section - section with target long value in file
     * @return received long or null if section not contains long value
     */
    public long getLong(String section) {
        return fileConfig.getLong(section);
    }
    
    /**
     * Getting long from file
     * @param section - section with target long value in file
     * @param def - default output value if section not contains long value
     * @return received long or 'def' value if section not contains long value
     */
    public long getLong(String section, long def) {
        return fileConfig.getLong(section, def);
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
		Object object = fileConfig.get(section);
		return object instanceof Number ? ((Number) object).floatValue() : 0.0F;
	}
	
	/**
	 * Getting float from file
	 * @param section - section with target float value in file
	 * @param def - default output value if section not contains float value
	 * @return received float or 'def' value if section not contains float value
	 */
	public float getFloat(String section, float def) {
		Object object = fileConfig.get(section, def);
		return object instanceof Number ? ((Number) object).floatValue() : 0.0F;
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

		List<String> colored = new ArrayList<>();
		list.forEach(s -> colored.add(colorize(s)));
		return colored;
	}
	
	/**
	 * Getting colored string from legacy '&#' colored string
	 * @param source - string, colored by '&#'
	 * @return new colored string instance
	 */
	public static String colorize(String source) {
	    return ChatColor.translateAlternateColorCodes('&', source);
	}
	
	/**
	 * Formatting your custom message
	 * @param message - the message to format
	 * @param replacements - array of string with this syntax: 'placeholder' 'value' 'placeholder' 'value'...
	 * @return formatted string with replaced placeholders
	 */
	public String format(String message, Object... replacements) {
		if(replacements == null)
			return message;
		
		int length = replacements.length;
		if(length == 0)
			return message;
		
		for(int i = 0; i < length; i += 2) {
			if(i == length - 1) continue;
			
			String placeholder = replacements[i].toString();
			Object value = replacements[i + 1];
			
			message = message.replace(placeholder, value != null ? value.toString() : "");
		}
		
		return message;
	}
	
	/**
	 * Formatting strings list using specified replacements objects array
	 * @param list - target list to format
	 * @param replacements - objects array of replacements formatted as '..., key, value, ...'
	 * @return formatted strings list with replaced placeholders
	 */
	public List<String> formatList(List<String> list, Object... replacements) {
		if(list == null || replacements == null || list.isEmpty())
			return list;
		
		return list.stream()
				.map(s -> format(s, replacements))
				.collect(Collectors.toList());
	}
	
}
