package com.xuhailiang5794.excel;

/**
 * <pre>
 * 数据类型，转成excel时用到
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/1 17:15
 */
public enum DataType {
    DATE("date"),
    CURRENCY("currency"),
    TEXT("text");

    private String value;

    DataType(String value) {
        this.value = value;
    }

    public String getValude() {
        return this.value;
    }
}
