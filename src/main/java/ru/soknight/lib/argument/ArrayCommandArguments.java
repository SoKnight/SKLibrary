package ru.soknight.lib.argument;

import java.util.List;

import lombok.Getter;

/**
 * The array-based {@link CommandArguments} implementation
 */
@Getter
public class ArrayCommandArguments implements CommandArguments {

	private final String[] arguments;
	private final int count;
	
	public ArrayCommandArguments(String[] arguments) {
		boolean empty = arguments == null || arguments.length == 0;
		
		this.arguments = empty
				? new String[0]
				: arguments;
				
		this.count = this.arguments.length;
	}
	
	public ArrayCommandArguments(List<String> listArguments) {
		boolean empty = listArguments == null || listArguments.isEmpty();
		
		this.arguments = empty
				? new String[0]
				: listArguments.toArray(new String[listArguments.size()]);
				
		this.count = arguments.length;
	}

	@Override
	public String get(int index) {
		return index < count ? arguments[index] : null;
	}

	@Override
	public double getAsDouble(int index) {
		if(index >= count)
			return -1D;
		
		try {
			return Double.parseDouble(arguments[index]);
		} catch (Exception ignored) {
			return -1D;
		}
	}

	@Override
	public float getAsFloat(int index) {
		if(index >= count)
			return -1F;
		
		try {
			return Float.parseFloat(arguments[index]);
		} catch (Exception ignored) {
			return -1F;
		}
	}

	@Override
	public int getAsInteger(int index) {
		if(index >= count)
			return -1;
		
		try {
			return Integer.parseInt(arguments[index]);
		} catch (Exception ignored) {
			return -1;
		}
	}
	
	@Override
	public boolean isEmpty() {
		return count == 0;
	}

	@Override
	public boolean isExist(int index) {
		return index < count;
	}
	
}
