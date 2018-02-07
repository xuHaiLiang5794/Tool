package com.xuhailiang5794.demo.controller;

import com.xuhailiang5794.demo.converter.LocalDateTimeFieldConverter;
import com.xuhailiang5794.demo.entity.AddressBook;
import com.xuhailiang5794.excel.ExcelHelper;
import com.xuhailiang5794.excel.converter.BooleanFieldConverter;
import com.xuhailiang5794.excel.exception.FieldConverterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/2 17:23
 */
@Api(value = "excel功能演示", description = "excel功能演示")
@RestController
@RequestMapping("excel")
public class ExcelDemoController {
    private String filename = "/通讯录.xlsx";

    @ApiOperation(value = "下载excel模板", notes = "excel模板，下面做表格解析时要用到")
    @GetMapping("downloadTemplate")
    public void downloadTemplate() throws URISyntaxException {
        filename = getClass().getResource(filename).toURI().getPath();
    }

    @ApiOperation(value = "excel表格解析", notes = "excel表格解析，不支持合并单元格这种复杂表格", response = String.class)
    @PostMapping
    public Object parseExcel(MultipartFile file) throws InvalidFormatException,
            IOException, InstantiationException, IllegalAccessException, FieldConverterException {
        ExcelHelper excelHelper = new ExcelHelper<AddressBook>();
        excelHelper.registerConverter(boolean.class, new BooleanFieldConverter("在职", "离职", true));
        excelHelper.registerConverter(LocalDateTime.class, new LocalDateTimeFieldConverter());
        Class<AddressBook> targetClazz = AddressBook.class;
        List<AddressBook> addressBooks = excelHelper.parseExcelToBeans(file.getInputStream(), targetClazz);
        return addressBooks;
    }
}
