package com.xuhailiang5794.common.util;

/**
 * <pre>
 * 字符串相关的工具类，以后引入有这个功能相关的jar时继承jar里的StringUtils
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/1 17:15
 */
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
