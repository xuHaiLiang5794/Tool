package com.xuhailiang5794.excel.converter;


import com.xuhailiang5794.common.util.StringUtils;

import java.lang.reflect.Field;

public class CharacterFieldConverter extends AbstractFieldValueConverter {

	{
		supportClazzs.add(char.class);
		supportClazzs.add(Character.class);
	}

	@Override
	public Object toObject(String source, Field field) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		if (source.toCharArray().length > 1) {
			throw new IllegalArgumentException(source + "不是一个Character类型值");
		}
		return Character.valueOf(source.charAt(0));
	}

}
