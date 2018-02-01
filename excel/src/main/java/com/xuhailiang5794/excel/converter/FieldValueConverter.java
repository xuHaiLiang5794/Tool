package com.xuhailiang5794.excel.converter;

import java.lang.reflect.Field;

/**
 * Field值转换类.
 * <pre>
 * @author hailiang.xu
 *
 * * Version 	Author	Time		describe
 * ----------------------------------------
 * 1.00		hailiang.xu	2017年10月30日	release
 * ----------------------------------------
 * </pre>
 */
public interface FieldValueConverter {
	
	/**
	 * 
	 * <pre>
	 * 说明:测试Class是否可以被转换
	 * </pre>
	 * @param clazz
	 * @return
	 * @author hailiang.xu
	 * @since 2017年10月30日 下午4:00:58
	 */
	boolean canConvert(Class<?> clazz);

	/**
	 * 
	 * <pre>
	 * 说明:将String类型的source转成为指定的Class对象
	 * </pre>
	 * @param source
	 * @param field
	 * @return
	 * @author hailiang.xu
	 * @since 2017年10月30日 下午4:01:44
	 */
	Object toObject(String source, Field field) throws Exception;

}
