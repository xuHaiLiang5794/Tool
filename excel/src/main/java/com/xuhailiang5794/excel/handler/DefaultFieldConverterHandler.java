package com.xuhailiang5794.excel.handler;

import com.xuhailiang5794.excel.converter.*;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 默认的字段转换处理器.
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/1 17:15
 */
public class DefaultFieldConverterHandler implements FieldConverterHandler {
	private static final Map<Class<?>, FieldValueConverter> defaultLocalConverterMapper = Collections
			.synchronizedMap(new HashMap<Class<?>, FieldValueConverter>());

	private final Map<Class<?>, FieldValueConverter> localConverterMapper;

	static {
		/**
		 * 内置初始化的转换器
		 */
		FieldValueConverter converter;
		converter = new IntegerFieldConverter();
		defaultLocalConverterMapper.put(int.class, converter);
		defaultLocalConverterMapper.put(Integer.class, converter);
		converter = new DoubleFieldConverter();
		defaultLocalConverterMapper.put(Double.class, converter);
		defaultLocalConverterMapper.put(double.class, converter);
		converter = new BooleanFieldConverter();
		defaultLocalConverterMapper.put(Boolean.class, converter);
		defaultLocalConverterMapper.put(boolean.class, converter);
		converter = new CharacterFieldConverter();
		defaultLocalConverterMapper.put(char.class, converter);
		defaultLocalConverterMapper.put(Character.class, converter);
		defaultLocalConverterMapper.put(Date.class, new DateFieldConverter());
		defaultLocalConverterMapper.put(Array.class, new ArrayFieldConverter());
	}

	public DefaultFieldConverterHandler() {
		localConverterMapper = Collections
				.synchronizedMap(new HashMap<Class<?>, FieldValueConverter>());
		localConverterMapper.putAll(defaultLocalConverterMapper);
	}

	@Override
	public FieldValueConverter getFieldConverter(Class<?> clazz) {
		return localConverterMapper.get(clazz);
	}

	public static FieldValueConverter getLocalFieldConverter(Class<?> clazz) {
		return defaultLocalConverterMapper.get(clazz);
	}

	@Override
	public void registerConverter(Class<?> clazz, FieldValueConverter converter) {
		if (!converter.canConvert(clazz)) {
			throw new IllegalArgumentException("不支持的类型：" + clazz.getName());
		}
		localConverterMapper.put(clazz, converter);
	}

}
