package com.xuhailiang5794.demo.view;

import com.xuhailiang5794.excel.ExcelHelper;
import lombok.AllArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Excel视图，用于Excel文件下载
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/8 14:42
 */
@AllArgsConstructor
public class ExcelView<E> extends AbstractXlsView {
    /**
     * 数据Bean集合
     */
    private List<E> beans;
    /**
     * 表格标题，无标题时设为null
     */
    private String title;
    /**
     * 数据Bean对象的Class
     */
    private Class<E> targetClazz;

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String excelName = map.get("excelName").toString() + ".xlsx";
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(excelName, "utf-8"));
        response.setContentType("application/ms-excel; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    /**
     * <pre>
     * 说明:createWorkbook方法，使用ExcelHelper来创建Workbook
     * </pre>
     *
     * @param model
     * @param request
     * @author hailiang.xu
     * @since 2017年2月8日 下午1:54:04
     */
    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        ExcelHelper excelHelper = new ExcelHelper<E>();
        try {
            return excelHelper.parseBeansToWorkbook(beans, title, targetClazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
