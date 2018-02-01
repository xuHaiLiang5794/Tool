package com.xuhailiang5794.excel.converter;

import com.xuhailiang5794.excel.util.StringUtils;

import java.lang.reflect.Field;

public class DoubleFieldConverter extends AbstractFieldValueConverter {

	{
		supportClazzs.add(double.class);
		supportClazzs.add(Double.class);
	}

	@Override
	public Object toObject(String source, Field field) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		return Double.valueOf(source);
	}

}
