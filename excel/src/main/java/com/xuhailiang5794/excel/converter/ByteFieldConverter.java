package com.xuhailiang5794.excel.converter;

import com.xuhailiang5794.excel.util.StringUtils;

import java.lang.reflect.Field;

public class ByteFieldConverter extends AbstractFieldValueConverter {

	{
		supportClazzs.add(byte.class);
		supportClazzs.add(Byte.class);
	}

	@Override
	public Object toObject(String source, Field field) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		return Byte.decode(source);
	}

}
