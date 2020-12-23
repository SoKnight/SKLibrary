package ru.soknight.lib.tool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Static tool class for working with lists and maps
 */
public class CollectionsTool {

	/**
	 * Pagination content and getting content on specified page if it's may be obtained
	 * @param <T> - class type of List
	 * @param targetList - list of objects to pagination
	 * @param pageSize - size of page, amount of rows per one page
	 * @param pageNumber - page number (from 1)
	 * @return Content for specified page if it can be obtained, else new empty ArrayList object
	 */
	public static <T> List<T> getSubList(List<T> targetList, int pageSize, int pageNumber) {
		List<T> empty = new ArrayList<>();
		if(targetList.isEmpty()) return empty;
		
		int start = pageSize * (pageNumber - 1), end = pageSize * pageNumber;
		
		if(start >= targetList.size()) return empty;
		if(end >= targetList.size()) end = targetList.size();
		
		List<T> onpage = targetList.subList(start, end);
		return onpage;
	}
	
	/**
	 * Pagination content and getting content on specified page if it's may be obtained
	 * @param <K> - class type of Map key
	 * @param <V> - class type of Map value
	 * @param map - map of objects to pagination
	 * @param size - size of page, amount of rows per one page
	 * @param page - page number (from 1)
	 * @return Content for specified page if it can be obtained, else new empty LinkedHashMap object
	 */
	public static <K, V> Map<K, V> getSubMap(Map<K, V> map, int size, int page) {
		Map<K, V> output = new LinkedHashMap<>();
		if(map.isEmpty()) return output;

		int start = size * (page - 1), end = size * page;
		if(start >= map.size()) return output;
		if(end >= map.size()) end = map.size();

		List<K> keys = map.keySet().stream().collect(Collectors.toList());
		for(int i = start; i < end; i++) {
			K key = keys.get(i);
			output.put(key, map.get(key));
		}

		return output;
	}
	
}
