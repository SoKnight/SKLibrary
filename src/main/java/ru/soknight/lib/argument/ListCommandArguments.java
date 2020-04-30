package ru.soknight.lib.argument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
/**
 * The list-based {@link CommandArguments} implementation
 */
@Getter
public class ListCommandArguments implements CommandArguments {

	private final List<String> arguments;
	private final int count;
	
	public ListCommandArguments(List<String> arguments) {
		boolean empty = arguments == null || arguments.isEmpty();
		
		this.arguments = empty
				? Collections.unmodifiableList(new ArrayList<>())
				: arguments;
				
		this.count = this.arguments.size();
	}
	
	public ListCommandArguments(String[] arrayArguments) {
		boolean empty = arrayArguments == null || arrayArguments.length == 0;
		
		this.arguments = empty
				? Collections.unmodifiableList(new ArrayList<>())
				: Arrays.stream(arrayArguments)
						.collect(Collectors.toList());
				
		this.count = arguments.size();
	}

	@Override
	public String get(int index) {
		return index < count ? arguments.get(0) : null;
	}

	@Override
	public double getAsDouble(int index) {
		if(index >= count)
			return -1D;
		
		try {
			return Double.parseDouble(arguments.get(index));
		} catch (Exception ignored) {
			return -1D;
		}
	}

	@Override
	public float getAsFloat(int index) {
		if(index >= count)
			return -1F;
		
		try {
			return Float.parseFloat(arguments.get(index));
		} catch (Exception ignored) {
			return -1F;
		}
	}

	@Override
	public int getAsInteger(int index) {
		if(index >= count)
			return -1;
		
		try {
			return Integer.parseInt(arguments.get(index));
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
