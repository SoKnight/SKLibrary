package ru.soknight.lib.argument;

import java.util.List;

/**
 * Presents any command arguments
 */
public interface CommandArguments extends List<String> {
	
	/**
	 * Gets argument by index (starts from 0) and tries to parse double from him
	 * @param index Index of target argument
	 * @return The existing argument as double or -1 if it's not exist
	 */
	double getAsDouble(int index);
	
	/**
	 * Gets argument by index (starts from 0) and tries to parse float from him
	 * @param index Index of target argument
	 * @return The existing argument as float or -1 if it's not exist
	 */
	float getAsFloat(int index);
	
	/**
	 * Gets argument by index (starts from 0) and tries to parse integer from him
	 * @param index Index of target argument
	 * @return The existing argument as integer or -1 if it's not exist
	 */
	int getAsInteger(int index);
	
	/**
	 * Checks if argument with specified index is exists
	 * @param index Index of target argument
	 * @return 'true' if this argument is exists or 'false' if not
	 */
	boolean hasArgument(int index);
	
}
