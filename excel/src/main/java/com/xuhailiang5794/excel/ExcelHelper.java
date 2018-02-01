package com.xuhailiang5794.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuhailiang5794.common.util.StringUtils;
import com.xuhailiang5794.excel.annotation.CellConfig;
import com.xuhailiang5794.excel.annotation.TableConfig;
import com.xuhailiang5794.excel.converter.FieldValueConverter;
import com.xuhailiang5794.excel.exception.FieldConverterException;
import com.xuhailiang5794.excel.handler.DefaultFieldConverterHandler;
import com.xuhailiang5794.excel.handler.FieldConverterHandler;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * Excel操作工具类.
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/1 17:15
 */
public class ExcelHelper<E> {
    private final static int START_ROW_NUM = 1;
    private final static int MAX_CELL_WIDTH = 6000;
    private final static int MIN_CELL_WIDTH = 2000;

    private FieldConverterHandler handler = new DefaultFieldConverterHandler();

    private static short headRowBGColorIndex;
    private static short titleGBColorIndex;
    private static short oddColorIndex;
    private static short evenColorIndex;
    private static XSSFColor oddColor;
    private static XSSFColor evenColor;
    private static XSSFColor headRowBGColor;
    private static XSSFColor titleGBColor;
    private static HSSFPalette palette;

    static {
        /**
         * 初始化一些静态资源，背景色这一类的东西
         */
        palette = new HSSFWorkbook().getCustomPalette();
        palette.setColorAtIndex(HSSFColor.GREY_40_PERCENT.index, (byte) 165, (byte) 165, (byte) 165);
        headRowBGColorIndex = HSSFColor.GREY_40_PERCENT.index;

        palette.setColorAtIndex(HSSFColor.GREY_50_PERCENT.index, (byte) 237, (byte) 237, (byte) 237);
        oddColorIndex = HSSFColor.GREY_50_PERCENT.index;
        palette.setColorAtIndex(HSSFColor.GREY_80_PERCENT.index, (byte) 255, (byte) 255, (byte) 255);
        evenColorIndex = HSSFColor.GREY_80_PERCENT.index;

        palette.setColorAtIndex(HSSFColor.GREY_80_PERCENT.index, (byte) 91, (byte) 155, (byte) 213);
        titleGBColorIndex = HSSFColor.GREY_80_PERCENT.index;


        headRowBGColor = new XSSFColor(new java.awt.Color(165, 165, 165));
        oddColor = new XSSFColor(new java.awt.Color(237, 237, 237));
        evenColor = new XSSFColor(new java.awt.Color(255, 255, 255));
        titleGBColor = new XSSFColor(new java.awt.Color(91, 155, 213));
    }

    /**
     * <pre>
     * 说明:支持非默认的字段类型转换
     * </pre>
     *
     * @param clazz 字段的类型
     * @param converter 字段转换的实现类
     * @author hailiang.xu
     * @since 2017年12月11日 下午4:10:32
     */
    public void registerConverter(Class<?> clazz, FieldValueConverter converter) {
        handler.registerConverter(clazz, converter);
    }

    /**
     * <pre>
     * 说明:支持非默认的字段类型转换
     * </pre>
     *
     * @param beans 数据集
     * @param filename 导出文件路径
     * @param targetClazz 数据集中存储对象的Class
     * @throws IOException
     * @throws InvalidFormatException
     * @author hailiang.xu
     * @since 2017年12月11日 下午4:10:32
     */
    public void parseBeansToExcel(List<E> beans, String filename, Class<E> targetClazz) throws IOException, InvalidFormatException {
        parseBeansToExcel(beans, filename, null, targetClazz);
    }

    /**
     * <pre>
     * 说明:导出excel
     * </pre>
     *
     * @param beans 数据集
     * @param filename 导出文件路径
     * @param title excel的顶部标题
     * @param targetClazz 数据集中存储对象的Class
     * @author hailiang.xu
     * @since 2017年12月11日 下午4:10:32
     */
    public void parseBeansToExcel(List<E> beans, String filename, String title, Class<E> targetClazz) throws IOException, InvalidFormatException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        Workbook workbook = parseBeansToWorkbook(beans, title, targetClazz);

        workbook.write(new FileOutputStream(file));
        workbook.close();
    }

    /**
     * 导出excel时要排除的字段
     */
    private List<String> excludes = new ArrayList<>();

    public Workbook parseBeansToWorkbook(List<E> beans, String title, Class<E> targetClazz) throws IOException, InvalidFormatException {
        /*File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }*/
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        TableConfig tableConfig = targetClazz.getAnnotation(TableConfig.class);
        if (tableConfig != null) {
            String[] exclude = tableConfig.exclude();
            if (exclude != null && exclude.length != 0) {
                excludes.addAll(Arrays.asList(exclude));
            }
        }

        setDefaultStyle(sheet);

        Field[] fields = targetClazz.getDeclaredFields();
        Class<?> superClazz = targetClazz.getSuperclass();
        Field[] superClazzFields = superClazz.getDeclaredFields();

        List<Field> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(fields));
        temp.addAll(Arrays.asList(superClazzFields));
        temp.sort((Field f1, Field f2) -> {
            CellConfig c1 = f1.getAnnotation(CellConfig.class);
            CellConfig c2 = f2.getAnnotation(CellConfig.class);
            int result = 0;
            if (c1 == null || c2 == null) {
                return result;
            }
            if (c1.priority() > c2.priority()) {
                result = 1;
            } else {
                if (c1.priority() < c2.priority()) {
                    result = -1;
                }
            }
            return result;
        });
        fields = temp.toArray(fields);
        Field.setAccessible(fields, true);

        if (StringUtils.isNotBlank(title)) {
            createTitle(workbook, sheet, fields, title);
        }

        createHeadRow(workbook, sheet, fields);

        beansToRow(workbook, sheet, beans, fields, targetClazz);

        /*workbook.write(new FileOutputStream(file));
        workbook.close();*/
        return workbook;
    }

    private void setDefaultStyle(Sheet sheet) {
        /**
         * 设置cell默认背景色，白色
         */
        sheet.getColumnStyle(Integer.MAX_VALUE).setFillBackgroundColor(HSSFColor.WHITE.index);
    }

    /**
     * <pre>
     * 说明:创建内容行
     * </pre>
     *
     * @param sheet
     * @param beans
     * @param fields
     * @param targetClazz
     * @author hailiang.xu
     * @since 2017年12月11日 下午4:19:21
     */
    public void beansToRow(Workbook workbook, Sheet sheet, List<E> beans, Field[] fields, Class<E> targetClazz) {
        if (beans == null) {
            return;
        }
        beans.forEach(bean -> {
            int rowIndex = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowIndex);
            for (Field field : fields) {
                CellConfig cellConfig = field.getAnnotation(CellConfig.class);
                if (!canExport(field, cellConfig) || cellConfig.omit()
                        || excludes.contains(field.getName())) {
                    continue;
                }

                int cellIndex = getLastCellNum(row);
                Cell cell = row.createCell(cellIndex);
                try {
                    Object val = field.get(bean);
                    XSSFColor cellBGColor = null;
                    if (val != null) {
                        JSONObject statusValue = JSON.parseObject(cellConfig.statusValue());
                        JSONObject sv = statusValue.getJSONObject(val.toString());
                        if (sv != null) {
                            Object tmp = sv.get("text");
                            if (tmp != null) {
                                val = tmp;

                                String color = sv.getString("bgColor");
                                if (StringUtils.isNotBlank(color)) {
                                    String[] rgbStr = color.split(",");

                                    cellBGColor = new XSSFColor(new java.awt.Color(Integer.valueOf(rgbStr[0]), Integer.valueOf(rgbStr[1]), Integer.valueOf(rgbStr[2])));
                                }
                            }
                        }
                        val = format(val, cellConfig);
                        cell.setCellValue(String.valueOf(val));
                    }
                    setBodyCellStyle(workbook.createCellStyle(), cell, rowIndex);
                    if (cellBGColor != null) {
                        setFillForegroundColor(cell.getCellStyle(), (short) 0, cellBGColor);
                    }
                    setAutoSizeColumn(sheet, cellIndex);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Object format(Object val, CellConfig cellConfig) {
        String pattern = cellConfig.pattern();
        /*String dataType = cellConfig.dataType();
        if (StringUtils.isNotBlank(dataType)
                && val != null) {
            if ("date".equalsIgnoreCase(dataType)) {
                if (StringUtils.isBlank(pattern)) {
                    pattern = "yyyy-MM-dd";
                }
                if (val instanceof LocalDateTime) {
                    val = DateTimeFormatter.ofPattern(pattern).format((LocalDateTime) val);
                } else if (val instanceof Date) {
                    val = new SimpleDateFormat(pattern).format((Date) val);
                }
            } else if ("currency".equalsIgnoreCase(dataType)) {
                if (pattern == null) {
                    pattern = "¤#,##0.00;-¤#,##0.00";
                }
                val = new DecimalFormat(pattern).format(val);
            }
        }*/
        DataType dataType = cellConfig.dataType();
        if (dataType != null
                && val != null) {
            switch (dataType) {
                case DATE:
                    if (StringUtils.isBlank(pattern)) {
                        pattern = "yyyy-MM-dd";
                    }
                    if (val instanceof LocalDateTime) {
                        val = DateTimeFormatter.ofPattern(pattern).format((LocalDateTime) val);
                    } else if (val instanceof Date) {
                        val = new SimpleDateFormat(pattern).format((Date) val);
                    }
                    break;
                case CURRENCY:
                    if (pattern == null) {
                        pattern = "¤#,##0.00;-¤#,##0.00";
                    }
                    val = new DecimalFormat(pattern).format(val);
                    break;
            }
        }
        return val;
    }

    private boolean canExport(Field field, CellConfig cellConfig) {
        if (cellConfig == null) {
            cellConfig = field.getAnnotation(CellConfig.class);
        }
        if (cellConfig == null) {
            return false;
        }
        return true;
    }

    private int getLastCellNum(Row row) {
        int cellIndex = row.getLastCellNum();
        if (cellIndex < 0) {
            cellIndex = 0;
        }
        return cellIndex;
    }

    private void setAutoSizeColumn(Sheet sheet, int cellIndex) {
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
        sheet.autoSizeColumn(cellIndex);
        float columnWidthInPixels = sheet.getColumnWidth(cellIndex);
        if (columnWidthInPixels > MAX_CELL_WIDTH) {
            sheet.setColumnWidth(cellIndex, MAX_CELL_WIDTH);
        } else if (columnWidthInPixels < MIN_CELL_WIDTH) {
            sheet.setColumnWidth(cellIndex, MIN_CELL_WIDTH);
        }
    }

    /**
     * <pre>
     * 说明:创建表头
     * </pre>
     *
     * @param sheet
     * @param fields
     * @author hailiang.xu
     * @since 2017年12月11日 下午4:16:56
     */
    private void createHeadRow(Workbook workbook, Sheet sheet, Field[] fields) {
        CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, 0);
        int rowIndex = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowIndex);
        CellStyle style = workbook.createCellStyle();


        setFillForegroundColor(style, headRowBGColorIndex, headRowBGColor);

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        for (Field field : fields) {
            CellConfig cellConfig = field.getAnnotation(CellConfig.class);
            if (cellConfig == null) {
                continue;
            }
            if (!cellConfig.omit()
                    && !excludes.contains(field.getName())) {
                int cellIndex = getLastCellNum(row);
                Cell cell = row.createCell(cellIndex);
                String cellVal = cellConfig.aliasName();
                if (StringUtils.isBlank(cellVal)) {
                    cellVal = field.getName();
                }
                cell.setCellValue(cellVal);
                setBorder(style, cell);
                cell.setCellStyle(style);
                setAutoSizeColumn(sheet, cellIndex);
            }
        }
    }

    /**
     * <pre>
     * 说明:创建表格标题
     * </pre>
     *
     * @param workbook
     * @param sheet
     * @param fields
     * @param title
     * @author hailiang.xu
     * @since 2017年12月11日 下午4:14:47
     */
    private void createTitle(Workbook workbook, Sheet sheet, Field[] fields, String title) {
        int cellCount = 0;
        for (Field field : fields) {
            CellConfig cellConfig = field.getAnnotation(CellConfig.class);
            if (canExport(field, cellConfig) && !cellConfig.omit()
                    && !excludes.contains(field.getName())) {
                cellCount++;
            }
        }
        int rowIndex = sheet.getLastRowNum();
        Row row = sheet.createRow(rowIndex);
        int cellIndex = getLastCellNum(row);
        Cell cell = row.createCell(cellIndex);
        int firstRow = rowIndex;
        int lastRow = rowIndex;
        int firstCol = cellIndex;
        int lastCol = cellCount - 1;
        CellRangeAddress rangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(rangeAddress);
        cell.setCellValue(title);

        setRegionBorder(rangeAddress, sheet);

        CellStyle style = cell.getCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 24);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);

        cell.setCellStyle(style);
        setFillForegroundColor(style, titleGBColorIndex, titleGBColor);
    }

    private void setBodyCellStyle(CellStyle style, Cell cell, int rowIndex) {
        if (rowIndex % 2 == 0) {
            setFillForegroundColor(style, oddColorIndex, oddColor);
        } else {
            setFillForegroundColor(style, evenColorIndex, evenColor);
        }
        setBorder(style, cell);
    }

    private void setBorder(CellStyle style, Cell cell) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        cell.setCellStyle(style);
    }

    private void setFillForegroundColor(CellStyle style, short colorIndex, XSSFColor color) {
        if (style instanceof XSSFCellStyle) {
            ((XSSFCellStyle) style).setFillForegroundColor(color);
        } else {
            style.setFillForegroundColor(colorIndex);
        }
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.WHITE.index);
    }

    private void setRegionBorder(CellRangeAddress rangeAddress, Sheet sheet) {
        RegionUtil.setBorderTop(BorderStyle.THIN.getCode(), rangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN.getCode(), rangeAddress, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN.getCode(), rangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN.getCode(), rangeAddress, sheet);
    }

    /**
     * <pre>
     * 说明:解析excel数据，组成成java bean集合
     * </pre>
     *
     * @param filename    文件名
     * @param targetClazz excel要转换成的目标Class
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @author hailiang.xu
     * @since 2017年10月30日 下午4:14:47
     */
    public List<E> parseExcelToBeans(String filename, Class<E> targetClazz)
            throws InvalidFormatException, IOException, InstantiationException,
            IllegalAccessException, FieldConverterException {
        InputStream inputStream = new FileInputStream(filename);
        return parseExcelToBeans(inputStream, targetClazz);
    }

    public List<E> parseExcelToBeans(InputStream inputStream, Class<E> targetClazz)
            throws InvalidFormatException, IOException, InstantiationException,
            IllegalAccessException, FieldConverterException {
        List<E> beans = new ArrayList<E>();
        Field[] fields = targetClazz.getDeclaredFields();
        Field.setAccessible(fields, true);

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        TableConfig tableConfig = targetClazz.getAnnotation(TableConfig.class);
        int startRowNum = START_ROW_NUM;
        if (tableConfig != null) {
            startRowNum = tableConfig.startRowNum();
        }
        int rowNums = sheet.getLastRowNum();

        if (startRowNum > rowNums) {
            throw new IllegalArgumentException("起始行下标大于最后行下标");
        }

        DataFormatter formatter = new DataFormatter();
        for (; startRowNum <= rowNums; startRowNum++) {
            E bean = (E) targetClazz.newInstance();
            beans.add(bean);
            rowToBean(sheet.getRow(startRowNum), bean, fields, formatter);
        }

        return beans;
    }

    /**
     * <pre>
     * 说明:row转java bean
     * </pre>
     *
     * @param row
     * @param bean
     * @param fields
     * @param formatter
     * @author hailiang.xu
     * @since 2017年10月30日 下午4:14:21
     */
    private void rowToBean(Row row, E bean, Field[] fields,
                           DataFormatter formatter)
            throws FieldConverterException {
        if (row == null) {
            return;
        }
        for (Field field : fields) {
            CellConfig cellConfig = field.getAnnotation(CellConfig.class);
            if (cellConfig == null || cellConfig.index() == -1) {
                continue;
            }

            Cell cell = row.getCell(cellConfig.index());

            String text = formatter.formatCellValue(cell);

            validateRequired(cellConfig, text, row.getRowNum());

            setFieldValue(field, bean, text);
        }
    }

    private void validateRequired(CellConfig cellConfig, String text, int rowNum) {
        if (cellConfig.required()) {
            if (text == null || "".equals(text)) {
                CellReference cellReference = new CellReference(
                        rowNum, cellConfig.index());
                throw new NullPointerException("单元格["
                        + cellReference.formatAsString() + "]为必填项");
            }
        }
    }

    /**
     * <pre>
     * 说明:将source设置到bean的属性中
     * </pre>
     *
     * @param field
     * @param bean
     * @param source
     * @author hailiang.xu
     * @since 2017年10月30日 下午4:11:04
     */
    private void setFieldValue(Field field, E bean, String source) throws FieldConverterException {
        try {
            Class<?> converterClass;
            if (field.getType().isArray()) {
                converterClass = Array.class;
            } else {
                converterClass = field.getType();
            }
            FieldValueConverter converter = handler.getFieldConverter(converterClass);
            if (converter != null) {
                Object obj = converter.toObject(source, field);
                field.set(bean, obj);
                return;
            }
            /**
             * 默认按String类型处理
             */
            field.set(bean, source);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}