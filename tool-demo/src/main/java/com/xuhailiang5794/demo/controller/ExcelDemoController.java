package com.xuhailiang5794.demo.controller;

import com.xuhailiang5794.common.result.OptResult;
import com.xuhailiang5794.demo.converter.LocalDateTimeFieldConverter;
import com.xuhailiang5794.demo.entity.AddressBook;
import com.xuhailiang5794.excel.ExcelHelper;
import com.xuhailiang5794.excel.converter.BooleanFieldConverter;
import com.xuhailiang5794.excel.exception.FieldConverterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
@Api(value = "excel功能演示", description = "excel功能演示", position = 1)
@RestController
@RequestMapping("excel")
public class ExcelDemoController {
    private String filename = "/通讯录.xlsx";

    @ApiOperation(value = "下载excel模板", notes = "excel模板，下面做表格解析时要用到")
    @GetMapping("downloadTemplate")
    public ResponseEntity<FileSystemResource> downloadTemplate() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xlsx");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(
                        MediaType.parseMediaType("application/octet-stream")
                )
                .body(
                        new FileSystemResource(
                                new File(getClass().getResource(filename).toURI().getPath())
                        )
                );
    }

    @ApiOperation(value = "excel表格解析", notes = "excel表格解析，不支持合并单元格这种复杂表格", response = AddressBook.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = AddressBook.class),
            @ApiResponse(code = 403, message = "fail", response = AddressBook.class)
    })
    @PostMapping
    public Object parseExcel(MultipartFile file) throws InvalidFormatException,
            IOException, InstantiationException, IllegalAccessException, FieldConverterException {
        ExcelHelper excelHelper = new ExcelHelper<AddressBook>();
        excelHelper.registerConverter(boolean.class, new BooleanFieldConverter("在职", "离职", true));
        excelHelper.registerConverter(LocalDateTime.class, new LocalDateTimeFieldConverter());
        Class<AddressBook> targetClazz = AddressBook.class;
        List<AddressBook> addressBooks = excelHelper.parseExcelToBeans(file.getInputStream(), targetClazz);
        OptResult<List<AddressBook>> result = OptResult.success();
        result.setData(addressBooks);
        return result;
    }
}
