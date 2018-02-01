package com.xuhailiang5794.excel.converter;

import com.xuhailiang5794.common.util.StringUtils;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;

@AllArgsConstructor// 为类提供一个全参的构造方法
public class BooleanFieldConverter extends AbstractFieldValueConverter {
    private static final String DEFAULT_TRUE_CASE_STR = "TRUE";
    private static final String DEFAULT_FALSE_CASE_STR = "FALSE";
    /**
     * true的默认String值
     */
    private final String trueCaseStr;
    /**
     * false的默认String值
     */
    private final String falseCaseStr;
    /**
     * 是否忽略大小写，默认忽略
     */
    private boolean ignoreCase;

    {
        supportClazzs.add(Boolean.class);
        supportClazzs.add(boolean.class);
    }

    public BooleanFieldConverter() {
        this(DEFAULT_TRUE_CASE_STR, DEFAULT_FALSE_CASE_STR, true);
    }

    @Override
    public Object toObject(String source, Field field) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        validateValue(source);
        boolean result = false;
        if (ignoreCase) {
            result = source.equalsIgnoreCase(trueCaseStr);
        } else {
            result = source.equals(trueCaseStr);
        }
        return result;
    }

    private void validateValue(String source) {
        /**
         * 校验结果，默认false，有匹配结果则更新为true，表示校验通过
         */
        boolean result = false;
        if (ignoreCase) {
            result = source.equalsIgnoreCase(trueCaseStr) || source.equalsIgnoreCase(falseCaseStr);
        } else {
            result = source.equals(trueCaseStr) || source.equals(falseCaseStr);
        }

        if (!result) {
            throw new IllegalArgumentException("[" + source + "]" + "不是有效的值");
        }
    }
}
