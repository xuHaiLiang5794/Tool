package com.xuhailiang5794.excel.converter;

import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
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
    public Object toObject(String source, Field field) throws Exception {
        return DEFAULT_DATE_FORMAT.parse(source);
    }
}
