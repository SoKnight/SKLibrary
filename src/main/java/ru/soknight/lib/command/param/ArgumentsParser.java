package ru.soknight.lib.command.param;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * Parser of arguments from some command execution
 * @param commandParameters - List of {@link CommandParameter} types for parsing
 */
@Getter
public class ArgumentsParser {

	private final Map<String, CommandParameter> commandParameters;
	private Map<String, String> parsedParameters;
	
	public ArgumentsParser() {
		this.commandParameters = new HashMap<>();
		this.parsedParameters = new HashMap<>();
	}
	
	public ArgumentsParser(Map<String, CommandParameter> commandParameters) {
		this.commandParameters = commandParameters;
		this.parsedParameters = new HashMap<>();
	}
	
	/**
	 * Parsing parameters values from string arguments array
	 * @param args - String array of command arguments
	 */
	public void parse(String[] args) {
		this.parsedParameters = new HashMap<>();
		
		if(this.commandParameters.isEmpty()) return;
		if(args.length == 0) return;
		
		String previous = null;
		for(int i = 0; i < args.length; i++) {
			String target = args[i];
			
			CommandParameter param = commandParameters.get(target);
			// if param is not specified
			if(param == null) {
				// if previous arg is param, writing valued entry and nullifing previous arg var
				if(previous != null) {
					this.parsedParameters.put(previous, target);
					previous = null;
				// else waiting first valid parameter
				} else continue;
			// if param is specified
			} else {
				// if parameter is single, writing it and nullifing previous arg var
				if(param.isSingle()) {
					this.parsedParameters.put(target, null);
					previous = null;
				// if parameter is valued, waiting value for him
				} else previous = target;
			}
		}
	}
	
	/**
	 * Adding command parameter for next parsing by {@link ArgumentsParser#parse(String[])}
	 * @param parameter - Target command parameter
	 */
	public void addCommandParameter(CommandParameter parameter) {
		this.commandParameters.put(parameter.getName(), parameter);
	}
	
	/**
	 * Dublicating current arguments parser with current command parameters list
	 * @return Another {@link ArgumentsParser} object with current command parameters list
	 */
	public ArgumentsParser duplicate() {
		return new ArgumentsParser(this.commandParameters);
	}
	
	/**
	 * Check if specified parameter has been used by command sender and contains by internal parsedParameters map as entry key
	 * @param parameter - Parameter name to check
	 * @return 'true' if parameter has been used and 'false' if not
	 */
	public boolean isParsed(String parameter) {
		return parsedParameters.containsKey(parameter);
	}
	
	/**
	 * Getting parsed value of specified parameter if it's has been parsed
	 * @param parameter - Target parameter name
	 * @return parsed value if it's exist or empty string if not and if parameter is single
	 */
	public String getValue(String parameter) {
		String value = parsedParameters.containsKey(parameter) ? parsedParameters.get(parameter) : "";
		return value == null ? "" : value;
	}
	
}
