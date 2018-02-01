package com.xuhailiang5794.excel.converter;

import com.xuhailiang5794.common.util.StringUtils;

import java.lang.reflect.Field;

public class LongFieldConverter extends AbstractFieldValueConverter {

	{
		supportClazzs.add(long.class);
		supportClazzs.add(Long.class);
	}

	@Override
	public Object toObject(String source, Field field) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		return Long.decode(source);
	}

}
