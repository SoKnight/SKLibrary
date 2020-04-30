package ru.soknight.lib.argument;

/**
 * Presents any command arguments
 */
public interface CommandArguments {
	
	/**
	 * Gets argument by index (starts from 0)
	 * @param index Index of target argument
	 * @return The existing argument or null
	 */
	String get(int index);
	
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
	 * Gets count of stored arguments
	 * @return The arguments count
	 */
	int getCount();
	
	/**
	 * Checks if this object stores any arguments or not
	 * @return 'true' if any arguments are presented here or 'false' if not
	 */
	boolean isEmpty();
	
	/**
	 * Checks if argument with specified index is exists
	 * @param index Index of target argument
	 * @return 'true' if this argument is exists or 'false' if not
	 */
	boolean isExist(int index);
	
}
