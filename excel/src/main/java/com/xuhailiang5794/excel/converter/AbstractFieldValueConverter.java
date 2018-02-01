package com.xuhailiang5794.excel.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现默认的canConvert方法.
 * <pre>
 * @author hailiang.xu
 *
 * * Version 	Author	Time		describe
 * ----------------------------------------
 * 1.00		hailiang.xu	2017年10月30日	release
 * ----------------------------------------
 * </pre>
 */
public abstract class AbstractFieldValueConverter implements FieldValueConverter {
	protected List<Class<?>> supportClazzs = new ArrayList<Class<?>>();

	@Override
	public boolean canConvert(Class<?> clazz) {
		boolean isSup = false;
		for (Class<?> cls : supportClazzs) {
			if (cls == clazz) {
				isSup = true;
				break;
			}
		}
		return isSup;
	}

}
