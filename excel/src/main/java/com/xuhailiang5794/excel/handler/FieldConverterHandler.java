package com.xuhailiang5794.excel.handler;

import com.xuhailiang5794.excel.converter.FieldValueConverter;

/**
 * Field Value转换处理器
 * <pre>
 * @author hailiang.xu
 *
 * * Version 	Author	Time		describe
 * ----------------------------------------
 * 1.00		hailiang.xu	2017年10月30日	release
 * ----------------------------------------
 * </pre>
 */
public interface FieldConverterHandler {

	FieldValueConverter getFieldConverter(Class<?> clazz);

	/**
	 * 
	 * <pre>
	 * 说明:注册自定义转换器
	 * </pre>
	 * @param clazz
	 * @param converter
	 * @author hailiang.xu
	 * @since 2017年10月30日 下午4:05:36
	 */
	void registerConverter(Class<?> clazz, FieldValueConverter converter);

}
