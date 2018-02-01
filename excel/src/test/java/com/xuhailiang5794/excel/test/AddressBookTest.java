package com.xuhailiang5794.excel.test;

import com.xuhailiang5794.excel.ExcelHelper;
import com.xuhailiang5794.excel.converter.BooleanFieldConverter;
import com.xuhailiang5794.excel.entity.AddressBook;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * <pre>
 * 读取excel“通讯录.xlsx”
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/1 17:15
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

    @Test
    public void parseBeansToExcel() throws Exception {
        List<AddressBook> beans = excelHelper.parseExcelToBeans(
                filename, targetClazz);
        String filename = "D:\\A-Document\\t.xlsx";
        Class<AddressBook> targetClazz = AddressBook.class;
        excelHelper.parseBeansToExcel(beans, filename, targetClazz);
    }

}
