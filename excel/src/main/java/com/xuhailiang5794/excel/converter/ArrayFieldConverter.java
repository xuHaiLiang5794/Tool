package com.xuhailiang5794.excel.converter;

import com.xuhailiang5794.excel.handler.DefaultFieldConverterHandler;
import com.xuhailiang5794.excel.util.StringUtils;
import lombok.AllArgsConstructor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

@AllArgsConstructor
public class ArrayFieldConverter extends AbstractFieldValueConverter {

	private String separator;

	{
		supportClazzs.add(Array.class);
	}

	public ArrayFieldConverter() {
		this(",");
	}

	@Override
	public Object toObject(String source, Field field) throws Exception {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		String[] sources = source.split(separator);
		Class<?> actualType = field.getType().getComponentType();
		Object array = Array.newInstance(actualType, sources.length);
		for (int i = 0; i < sources.length; i++) {
			Object data = sources[i];
			FieldValueConverter converter = DefaultFieldConverterHandler.getLocalFieldConverter(actualType);
			if (converter != null) {
				data = converter.toObject(sources[i], field);
			}
			Array.set(array, i, data);
		}
		return array;
	}

}
