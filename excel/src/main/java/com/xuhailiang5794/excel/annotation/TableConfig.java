package com.xuhailiang5794.excel.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 * 配置table属性
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/1 17:15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableConfig {

	/**
	 * 起始行下标，从0开始，默认1
	 *
	 * <pre>
	 * 说明:
	 * </pre>
	 *
	 * @return
	 * @author hailiang.xu
	 * @since 2017年10月30日 下午3:09:05
	 */
	int startRowNum() default 1;

	/**
	 * 导出Excel时要忽略的字段(仅限CellConfig标识导出的字段)
	 * @return
	 */
	String[] exclude() default {};

}