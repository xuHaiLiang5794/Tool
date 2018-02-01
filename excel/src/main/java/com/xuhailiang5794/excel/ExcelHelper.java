package com.xuhailiang5794.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.xuhailiang5794.excel.handler.FieldConverterHandler;
import com.xuhailiang5794.excel.annotation.TableConfig;
import com.xuhailiang5794.excel.handler.DefaultFieldConverterHandler;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.xuhailiang5794.excel.annotation.CellConfig;
import com.xuhailiang5794.excel.converter.FieldValueConverter;

public class ExcelHelper<E> {
	private final static int START_ROW_NUM = 1;

	private FieldConverterHandler handler = new DefaultFieldConverterHandler();

	public void registerConverter(Class<?> clazz, FieldValueConverter converter) {
		handler.registerConverter(clazz, converter);
	}

	/**
	 * 
	 * <pre>
	 * 说明:解析excel数据，组成成java bean集合
	 * </pre>
	 * @param filename 文件名
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
			IllegalAccessException, Exception {
		List<E> beans = new ArrayList<E>();
		Field[] fields = targetClazz.getDeclaredFields();
		Field.setAccessible(fields, true);
		InputStream inputStream = new FileInputStream(filename);

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
	 * 
	 * <pre>
	 * 说明:row转java bean
	 * </pre>
	 * @param row
	 * @param bean
	 * @param fields
	 * @param formatter
	 * @author hailiang.xu
	 * @since 2017年10月30日 下午4:14:21
	 */
	private void rowToBean(Row row, E bean, Field[] fields,
			DataFormatter formatter) throws Exception {
		for (Field field : fields) {
			CellConfig cellConfig = field.getAnnotation(CellConfig.class);
			if (cellConfig == null) {
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
	 * 
	 * <pre>
	 * 说明:将source设置到bean的属性中
	 * </pre>
	 * @param field
	 * @param bean
	 * @param source
	 * @author hailiang.xu
	 * @since 2017年10月30日 下午4:11:04
	 */
	private void setFieldValue(Field field, E bean, String source) throws Exception {
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
