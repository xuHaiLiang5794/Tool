package com.xuhailiang5794.excel.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 实现默认的canConvert方法.
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/1 17:15
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
