package com.xuhailiang5794.excel.annotation;

import com.xuhailiang5794.excel.DataType;

import java.lang.annotation.*;

/**
 * <pre>
 * 单元格配置，导入导出配置信息.
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/1 17:15
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CellConfig {

	/**
	 *
	 * <pre>
	 * 说明:下标值，从0开始<br/>
	 * index值为-1，表示解析excel时去根据这个字段取取数（即这个字段不是excel中的列）
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

	/**
	 * 状态值描述(JSON字符串)
	 * @return
	 */
	String statusValue() default "{}";

	/**
	 * 数据类型[string/currency/date]
	 * @return
	 */
	DataType dataType() default DataType.TEXT;

	/**
	 * 格式化模板，与dataType配套使用
	 * @return
	 */
	String pattern() default "";

	/**
	 * 顺序权值
	 * @return
	 */
	int priority() default -1;// 默认-1，不排序

}