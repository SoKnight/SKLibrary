package ru.soknight.lib.argument;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * The list-based {@link CommandArguments} implementation
 */
public class BaseCommandArguments extends ArrayList<String> implements CommandArguments {
	
	public BaseCommandArguments(String[] arrayArguments) {
		if(arrayArguments != null && arrayArguments.length != 0)
			Arrays.stream(arrayArguments).forEach(s -> add(s));
	}
	
	@Override
	public String get(int index) {
		return hasArgument(index) ? super.get(index) : null;
	}

	@Override
	public double getAsDouble(int index) {
		if(index >= size())
			return -1D;
		
		try {
			return Double.parseDouble(get(index));
		} catch (Exception ignored) {
			return -1D;
		}
	}

	@Override
	public float getAsFloat(int index) {
		if(index >= size())
			return -1F;
		
		try {
			return Float.parseFloat(get(index));
		} catch (Exception ignored) {
			return -1F;
		}
	}

	@Override
	public int getAsInteger(int index) {
		if(index >= size())
			return -1;
		
		try {
			return Integer.parseInt(get(index));
		} catch (Exception ignored) {
			return -1;
		}
	}

	@Override
	public boolean hasArgument(int index) {
		return size() > index;
	}
	
}
