package com.xuhailiang5794.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置table属性.
 * 
 * <pre>
 * @author hailiang.xu
 * 
 * * Version 	Author	Time		describe
 * ----------------------------------------
 * 1.00		hailiang.xu	2017年10月30日	release
 * ----------------------------------------
 * </pre>
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

}
