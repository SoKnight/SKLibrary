package ru.soknight.lib.validation;

/**
 * Extended execution data which stores string array
 */
public interface HoldingExecutionData extends CommandExecutionData {

	/**
	 * Getting stored data as strings array
	 * @return stored data
	 */
	String[] getData();
	
	/**
	 * Getting any string from data array
	 * @param index - index of element in data array
	 * @return exist string from data array or null
	 */
	String getString(int index);
	
	/**
	 * Converting any string from data array to integer
	 * @param index - index of element in data array
	 * @return integer object from exist string from data array or null
	 */
	Integer getInt(int index);

}
