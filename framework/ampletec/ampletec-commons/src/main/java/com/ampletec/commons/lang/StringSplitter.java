package com.ampletec.commons.lang;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class StringSplitter {

	public static List<Long> splitLong(String str, String regex) {
		return Arrays.asList(str.split(regex)).stream().map(StringSplitter::parseLong).collect(Collectors.toList());
	}

	public static List<Integer> splitInteger(String str, String regex) {
		return Arrays.asList(str.split(regex)).stream().map(StringSplitter::parseInt).collect(Collectors.toList());
	}

	public static List<String> splitString(String str, String regex) {
		return Arrays.asList(str.split(regex)).stream().map(s -> (s.trim())).collect(Collectors.toList());
	}
	
	private static int parseInt(String s) {
		if(StringUtils.isEmpty(s)) return 0;
		return Integer.parseInt(s);
	}
	
	private static long parseLong(String s) {
		if(StringUtils.isEmpty(s)) return 0L;
		return Long.parseLong(s);
	}
}
