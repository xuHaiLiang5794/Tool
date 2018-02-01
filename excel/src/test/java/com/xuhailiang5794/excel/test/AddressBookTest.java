package com.xuhailiang5794.excel.test;

import com.xuhailiang5794.excel.ExcelHelper;
import com.xuhailiang5794.excel.converter.BooleanFieldConverter;
import com.xuhailiang5794.excel.entity.AddressBook;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 读取excel“通讯录.xlsx”
 * <p>
 * <pre>
 * @author hailiang.xu
 *
 * * Version 	Author	Time		describe
 * ----------------------------------------
 * 1.00		hailiang.xu	2017年10月30日	release
 * ----------------------------------------
 * </pre>
 */
public class AddressBookTest {

    String filename = "/通讯录.xlsx";

    ExcelHelper<AddressBook> excelHelper;

    Class<AddressBook> targetClazz;

    @Before
    public void init() throws Exception {
        filename = getClass().getResource(filename).toURI().getPath();
        excelHelper = new ExcelHelper<AddressBook>();
        targetClazz = AddressBook.class;
        /**
         * 注册一个BooleanFieldConverter替换默认的BooleanFieldConverter，自定义Boolean字段的String值
         */
        excelHelper.registerConverter(boolean.class, new BooleanFieldConverter("在职", "离职", true));
    }

    @Test
    public void parseExcelToAddressBooks() throws Exception {
        List<AddressBook> addressBooks = excelHelper.parseExcelToBeans(
                filename, targetClazz);
        addressBooks.forEach(addressBook -> {
            System.out.println(addressBook);
        });
    }

}
