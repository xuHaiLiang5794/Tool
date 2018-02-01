package com.xuhailiang5794.excel.converter;

import com.xuhailiang5794.excel.exception.FieldConverterException;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
public class DateFieldConverter extends AbstractFieldValueConverter {

    private SimpleDateFormat DEFAULT_DATE_FORMAT;

    {
        supportClazzs.add(Date.class);
    }

    public DateFieldConverter() {
        this(new SimpleDateFormat("yyyy/MM/dd"));
    }

    @Override
    public Object toObject(String source, Field field) throws FieldConverterException {
        try {
            return DEFAULT_DATE_FORMAT.parse(source);
        } catch (ParseException e) {
            throw new FieldConverterException(e);
        }
    }
}
