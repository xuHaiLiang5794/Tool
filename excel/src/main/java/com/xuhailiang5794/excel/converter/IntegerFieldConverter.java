package com.xuhailiang5794.excel.converter;

import com.xuhailiang5794.common.util.StringUtils;

import java.lang.reflect.Field;

public class IntegerFieldConverter extends AbstractFieldValueConverter {

	{
		supportClazzs.add(int.class);
		supportClazzs.add(Integer.class);
	}

	@Override
	public Object toObject(String source, Field field) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		return Integer.decode(source);
	}

}
