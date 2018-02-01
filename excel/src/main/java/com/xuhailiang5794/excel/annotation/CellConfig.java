package com.xuhailiang5794.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单元格配置，导入导出配置信息.
 * <pre>
 * @author hailiang.xu
 *
 * * Version 	Author	Time		describe
 * ----------------------------------------
 * 1.00		hailiang.xu	2017年10月30日	release
 * ----------------------------------------
 * </pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CellConfig {
	
	/**
	 * 
	 * <pre>
	 * 说明:下标值，从0开始
	 * </pre>
	 * @return
	 * @author hailiang.xu
	 * @since 2017年10月30日 上午10:47:43
	 */
	int index();
	
	/**
	 * 
	 * <pre>
	 * 说明:解析excel cell的必填标识，用于限制参数是否为必填。true为必填，默认false
	 * </pre>
	 * @return
	 * @author hailiang.xu
	 * @since 2017年10月30日 上午10:50:53
	 */
	boolean required() default false;
	
	/**
	 * 
	 * <pre>
	 * 说明:导出excel，字段是否忽略。true为忽略，默认false
	 * </pre>
	 * @return
	 * @author hailiang.xu
	 * @since 2017年10月30日 上午10:55:41
	 */
	boolean omit() default false;
	
	/**
	 * 
	 * <pre>
	 * 说明:导出excel，title的名称。默认""，导出时取用属性名
	 * </pre>
	 * @return
	 * @author hailiang.xu
	 * @since 2017年10月30日 上午10:58:16
	 */
	String aliasName() default "";

}
