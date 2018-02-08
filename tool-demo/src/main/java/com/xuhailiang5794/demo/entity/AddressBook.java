package com.xuhailiang5794.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xuhailiang5794.excel.DataType;
import com.xuhailiang5794.excel.annotation.CellConfig;
import com.xuhailiang5794.excel.annotation.TableConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <pre>
 * 通讯录.xlsx对应的java bean
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/1 17:15
 */
@Data// 在编译过程中会自动添加setter等方法
@TableConfig(startRowNum = 1)// 从第1行开始解析
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook {
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @CellConfig(index = 0)
    private String name;
    /**
     * 职位
     */
    @ApiModelProperty("职位")
    @CellConfig(index = 1)
    private String position;
    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @CellConfig(index = 2)
    private String mobileNumber;
    /**
     * 住址
     */
    @ApiModelProperty("住址")
    @CellConfig(index = 3)
    private String address;

    @ApiModelProperty("是否在职")
    @CellConfig(index = 4)
    private boolean stat;

    /**
     * 入职时间
     */
    @ApiModelProperty("入职时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CellConfig(index = 5, dataType = DataType.DATE, pattern = "yyyy/MM/dd")
    private LocalDateTime hiredate;

    /**
     * 数组类型，支持已注册的FieldValueConverter
     */
    @ApiModelProperty("头衔")
    @CellConfig(index = 6)
    private char[] titles;
}
