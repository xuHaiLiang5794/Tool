package com.xuhailiang5794.excel.entity;

import com.xuhailiang5794.excel.annotation.CellConfig;
import com.xuhailiang5794.excel.annotation.TableConfig;
import lombok.Data;

import java.util.Date;

/**
 * 通讯录.xlsx对应的java bean
 * <pre>
 * @author hailiang.xu
 *
 * * Version 	Author	Time		describe
 * ----------------------------------------
 * 1.00		hailiang.xu	2017年10月30日	release
 * ----------------------------------------
 * </pre>
 */
@Data// 在编译过程中会自动添加setter等方法
@TableConfig(startRowNum = 1)// 从第1行开始解析
public class AddressBook {
    /**
     * 姓名
     */
    @CellConfig(index = 0)
    private String name;
    /**
     * 职位
     */
    @CellConfig(index = 1)
    private String position;
    /**
     * 手机号码
     */
    @CellConfig(index = 2)
    private String mobileNumber;
    /**
     * 住址
     */
    @CellConfig(index = 3)
    private String address;

    @CellConfig(index = 4)
    private boolean stat;

    /**
     * 入职时间
     */
    @CellConfig(index = 5)
    private Date hiredate;

    /**
     * 数组类型，支持已注册的FieldValueConverter
     */
    @CellConfig(index = 6)
    private char[] titles;
}
