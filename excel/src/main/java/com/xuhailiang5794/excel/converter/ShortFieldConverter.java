package com.xuhailiang5794.excel.converter;

import com.xuhailiang5794.common.util.StringUtils;

import java.lang.reflect.Field;

public class ShortFieldConverter extends AbstractFieldValueConverter {

	{
		supportClazzs.add(short.class);
		supportClazzs.add(Short.class);
	}

	@Override
	public Object toObject(String source, Field field) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		return Short.decode(source);
	}

}
