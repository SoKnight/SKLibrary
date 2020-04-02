package ru.soknight.lib.logging;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

import lombok.Getter;

/**
 * Simple plugin logger with basical logging methods
 */
@Getter
public class PluginLogger {

	private final Logger logger;
	
	/**
	 * Simple plugin logger with basical logging methods
	 * @param plugin - host plugin
	 */
	public PluginLogger(Plugin plugin) {
		this.logger = plugin.getLogger();
	}
	
	/**
	 * Sending info prefixed message
	 * @param info - some information
	 */
	public void info(String info) {
		this.logger.info(info);
	}
	
	/**
	 * Sending warning prefixed message
	 * @param warning - some warning
	 */
	public void warning(String warning) {
		this.logger.warning(warning);
	}
	
	/**
	 * Sending error prefixed message
	 * @param info - some error
	 */
	public void error(String error) {
		this.logger.severe(error);
	}
	
	/**
	 * Sending info prefixed message with additional prefix [Debug]
	 * @param debug - some debug information
	 */
	public void debug(String debug) {
		this.logger.info("[Debug] " + debug);
	}
	
}
