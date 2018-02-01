package com.xuhailiang5794.excel.util;

public class StringUtils {
	
	public static boolean isBlank(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

}
