package com.xuhailiang5794.demo.converter;

import com.xuhailiang5794.excel.converter.AbstractFieldValueConverter;
import com.xuhailiang5794.excel.exception.FieldConverterException;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * <pre>
 * 这里日期用jdk8的LocalDateTime，这个类用来处理LocalDateTime与String之间的转换
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/6 17:36
 */
@NoArgsConstructor
public class LocalDateTimeFieldConverter extends AbstractFieldValueConverter {
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy/MM/dd");

    public LocalDateTimeFieldConverter(String pattern) {
        timeFormatter = new SimpleDateFormat(pattern);
    }

    {
        supportClazzs.add(LocalDateTime.class);
    }

    @Override
    public Object toObject(String source, Field field) throws FieldConverterException {
        try {
            Date date = timeFormatter.parse(source);
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ParseException e) {
            throw new FieldConverterException(e);
        }
    }
}
