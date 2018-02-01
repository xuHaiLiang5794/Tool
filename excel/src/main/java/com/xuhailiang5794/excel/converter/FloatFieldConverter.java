package com.xuhailiang5794.excel.converter;

import com.xuhailiang5794.excel.util.StringUtils;

import java.lang.reflect.Field;

public class FloatFieldConverter extends AbstractFieldValueConverter {

	{
		supportClazzs.add(float.class);
		supportClazzs.add(Float.class);
	}

	@Override
	public Object toObject(String source, Field field) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		return Float.valueOf(source);
	}

}
