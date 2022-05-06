package cn.prozhu.ssm.util.excel;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import cn.prozhu.ssm.util.DjUtil;
import cn.prozhu.ssm.util.PropUtil;
import cn.prozhu.ssm.util.StringUitl;

public class DjExcelCreator {
	private WritableSheet wsheet;

	private final int EXCEL_MAX_ROW = 65536; // 单个sheet 最大行数

	private WritableWorkbook wwb;

	private String fileName; // 文件名称, 默认按时间生产

	private String title; // 标题

	private List<String> columnTitleList; // 列标题

	private List<String> columnList;

	private Integer[] columnsWidth;// 列宽

	private int sheetIndex = 0;// 当前操作sheet

	private int rowIndex = 2;// 当前操作数据行

	private int startCol = 0;// 数据开始列

	private boolean hasSequence = false;

	private int startRow = 2;// 数据开始行

	private WritableCellFormat titleCellFormat;// 标题格式
	private WritableCellFormat headCellFormat;// 列名格式
	private WritableCellFormat stringCellFormat;// 字符串格式

	private List<String> sumColumns = new ArrayList<>();// 需合计的列

	private Map<String, Method> methodMap = new HashMap<>();// 反射Field缓存

	private Map<String, SimpleDateFormat> dateFormatMap = new HashMap<>();
	private Map<String, CellType> colCellTypeMap = new HashMap<>();

	private Map<String, WritableCellFormat> colNumberFormat = new HashMap<>();
	private Map<String, String> renderPropMap = new HashMap<>();
	private Map<String, DjExcelDataRender> renderMap = new HashMap<>();

	public static enum CellType {
		STRING, NUMBER, DATE;
	}

	public DjExcelCreator(String[] colNames, String[] colKeys, String title) throws Exception {
		this.title = title;
		this.columnTitleList = Arrays.asList(colNames);
		this.columnList = Arrays.asList(colKeys);
		init();
	}

	/**
	 * 初始化
	 * 
	 * @param cls
	 * @throws Exception
	 */
	private void init() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		this.fileName = "_" + format.format(new Date()) + ".xls";

		// 标题样式
		WritableFont titleFont = new WritableFont(WritableFont.createFont("微软雅黑"), 20, WritableFont.BOLD);
		titleCellFormat = new WritableCellFormat(titleFont); // 设置样式，字体
		titleCellFormat.setAlignment(Alignment.CENTRE); // 平行居中
		titleCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中

		// 列名样式
		WritableFont headFont = new WritableFont(WritableFont.createFont("微软雅黑"), 9, WritableFont.BOLD);
		headCellFormat = new WritableCellFormat(headFont); // 设置样式，字体
		headCellFormat.setAlignment(Alignment.CENTRE); // 平行居中
		headCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中

		// 字符串类型数据 样式
		WritableFont stringFont = new WritableFont(WritableFont.createFont("微软雅黑"), 9, WritableFont.NO_BOLD);
		stringCellFormat = new WritableCellFormat(stringFont); // 设置样式，字体
		stringCellFormat.setAlignment(Alignment.CENTRE); // 平行居中
		stringCellFormat.setWrap(true);
		stringCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中

	}

	/**
	 * web excel文件流输出
	 * 
	 * @param list
	 * @throws Exception
	 */
	public <T> void createForWeb(List<T> list, HttpServletResponse response) throws Exception {
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

		this.createExcel(response.getOutputStream(), list);
	}

	/**
	 * 创建excel
	 * 
	 * @param os
	 * @param list
	 * @throws Exception
	 */
	private <T> void createExcel(OutputStream os, List<T> list) throws Exception {
		try {
			wwb = Workbook.createWorkbook(os);

			if (list.size() > 0) {
				boolean isMap = list.get(0) instanceof Map;

				if (list.get(0) instanceof Map) {
					initMapColumnType((Map<String, Object>) list.get(0));
				} else {
					initModelColumnType(list.get(0));
				}

				boolean hasSum = sumColumns.size() > 0; // 是否含 合计行
				int maxRow = EXCEL_MAX_ROW - 2 - (hasSum ? 1 : 0); // 单个sheet最大数据行
				for (int i = 0, loop = list.size() / maxRow; i <= loop; i++) {
					newSheet();// 新建sheet
					int endRow = (i == loop ? (list.size() % (maxRow + 1)) : maxRow) + startRow; // 数据结束行
					int j;
					int cnt = 1; // 数据序号
					for (j = startRow; j < endRow; j++) {
						Object obj = list.get(j - startRow + i * maxRow);
						if (hasSequence) {
							// 写入序号
							setCell(0, cnt, CellType.STRING);
						}
						for (String key : columnList) {
							if (isMap) {
								setCell(key, ((Map<String, Object>) obj).get(key));
							} else {
								setCell(key, methodMap.get(key).invoke(obj));
							}
						}
						cnt++;
						rowIndex++;
					}
					// 添加合计行
					if (hasSum) {
						wsheet.addCell(new Label(0, j, "合计", headCellFormat));
						for (String Columns : sumColumns) {
							int col = columnList.indexOf(Columns);
							String colKey = getColumnKey(col);
							Formula formual = new Formula(col + startCol, j, "SUM(" + colKey
									+ DjUtil.toStr(startRow + 1) + ":" + colKey + DjUtil.toStr(endRow) + ")");
							wsheet.addCell(formual);
						}
					}
				}

			} else {
				// 无数据 新建sheet
				newSheet();
			}

			wwb.write();
			os.flush();
		} finally {
			if (wwb != null) {
				wwb.close();
			}
		}
	}

	/**
	 * 普通 模型 的 数据 写入 excel
	 * 
	 * @param list
	 * @throws Exception
	 */
	private void initModelColumnType(Object obj) throws Exception {
		Class cls = obj.getClass();
		for (String colKey : columnList) {
			// 对象反射 缓存至Map
			Method method = cls.getMethod(
					"get" + colKey.replaceFirst(colKey.substring(0, 1), colKey.substring(0, 1).toUpperCase()));
			methodMap.put(colKey, method);

			setColumnType(colKey, method.invoke(obj));
		}
	}

	/**
	 * Map<String, Object> 的 数据 写入 excel
	 * 
	 * @param list
	 * @throws Exception
	 */
	private void initMapColumnType(Map<String, Object> map) throws Exception {
		// 分析每列数据类型，及格式
		for (String colKey : columnList) {
			setColumnType(colKey, map.get(colKey));
		}
	}

	/**
	 * 设定列的单元格类型
	 * 
	 * @param colKey
	 * @param obj
	 * @throws Exception
	 */
	private void setColumnType(String colKey, Object obj) throws Exception {
		if (!colCellTypeMap.containsKey(colKey)) {
			// 根据类型，解析各列的数据格式类型
			if (obj instanceof java.lang.Integer || obj instanceof java.lang.Long) {
				this.addNumberCol(colKey, "0");
				colCellTypeMap.put(colKey, CellType.NUMBER);
			} else if (obj instanceof java.lang.Number) {
				this.addNumberCol(colKey, "0.00");
				colCellTypeMap.put(colKey, CellType.NUMBER);
			} else if (obj instanceof Date) {
				// 默认日期格式
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dateFormatMap.put(colKey, df);
				colCellTypeMap.put(colKey, CellType.DATE);
			} else {
				colCellTypeMap.put(colKey, CellType.STRING);
			}
		}
	}

	/**
	 * 新建sheet
	 * 
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private void newSheet() throws RowsExceededException, WriteException {
		wsheet = wwb.createSheet(title + DjUtil.toStr(sheetIndex), sheetIndex);
		sheetIndex++;
		rowIndex = 2;
		SheetSettings ss = wsheet.getSettings();
		ss.setVerticalFreeze(2); // 设置行冻结前2行

		if (columnsWidth == null || columnsWidth.length == 0) {
			CellView cv = new CellView();
			cv.setAutosize(true);// 自动宽度
			cv.setSize(10000); // 最小宽度
			for (int i = 0; i < columnTitleList.size(); i++) {
				wsheet.setColumnView(i, cv);
			}
		} else {
			for (int i = startCol; i < startCol + columnsWidth.length; i++) {
				wsheet.setColumnView(i, columnsWidth[i - startCol]);
			}
		}
		if (hasSequence) {
			wsheet.setColumnView(0, 10);
		}

		// 设定标题
		Label titleLabel = new Label(0, 0, title, titleCellFormat);
		wsheet.mergeCells(0, 0, columnTitleList.size() - 1 + startCol, 0); // 合并单元格
		wsheet.addCell(titleLabel);
		wsheet.setRowView(0, 1000); // 设置第一行的高度

		if (hasSequence) {
			wsheet.addCell(new Label(0, 1, "序号", headCellFormat));
		}
		for (int i = startCol, length = columnTitleList.size() + startCol; i < length; i++) {
			wsheet.addCell(new Label(i, 1, columnTitleList.get(i - startCol), headCellFormat));
		}
	}

	/**
	 * 写入单元格内容
	 * 
	 * @param colKey
	 * @param value
	 * @throws Exception
	 */
	private void setCell(String colKey, Object value) throws Exception {
		int col = columnList.indexOf(colKey) + startCol;
		CellType type = colCellTypeMap.get(colKey);
		if (renderPropMap.containsKey(colKey)) {
			value = PropUtil.getDataDictionaryProperty(renderPropMap.get(colKey) + "." + value);
			type = CellType.STRING;
		} 
		
		if (renderMap.containsKey(colKey)) {
			value = renderMap.get(colKey).render(value);
			type = CellType.STRING;
		}
		
		this.setCell(col, value, type);
	}

	/**
	 * 设写入单元格内容
	 * 
	 * @param col
	 *            列
	 * @param data
	 *            数据
	 * @param type
	 *            类型
	 * @throws Exception
	 */
	private void setCell(int col, Object value, CellType type) throws Exception {
		switch (type) {
		case STRING:
			wsheet.addCell(new Label(col, rowIndex, DjUtil.toStr(value), stringCellFormat));
			break;
		case DATE:
			wsheet.addCell(new Label(col, rowIndex, dateFormatMap.get(columnList.get(col - startCol)).format(value),
					stringCellFormat));
			break;
		case NUMBER:
			if (value != null) {
				wsheet.addCell(new Number(col, rowIndex, DjUtil.toDouble(value),
						colNumberFormat.get(columnList.get(col - startCol))));
			}
			break;
		default:
			wsheet.addCell(new Label(col, rowIndex, DjUtil.toStr(value), stringCellFormat));
			break;
		}
	}

	/**
	 * 设定列数字 格式
	 * 
	 * @param column
	 * @param format 如0, 0.00等
	 * @throws Exception
	 */
	public void addNumberCol(String column, String format) throws Exception {
		NumberFormat nf = new NumberFormat(format);// 设置数字格式
		WritableCellFormat cellFormat = new WritableCellFormat(nf);// 设置表单格式
		cellFormat.setAlignment(Alignment.CENTRE); // 平行居中
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
		colCellTypeMap.put(column, CellType.NUMBER);
		colNumberFormat.put(column, cellFormat);
	}

	/**
	 * 设定列数字 格式
	 * 
	 * @param columns
	 * @param format 如0, 0.00等
	 * @throws Exception
	 */
	public void addNumberCol(String[] columns, String format) throws Exception {
		for (String str : columns) {
			this.addNumberCol(str, format);
		}
	}

	/**
	 * 设定列数字 格式
	 * 
	 * @param column
	 * @param format
	 * @throws Exception
	 */
	public void addDateCol(String column, String format) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(format);
		colCellTypeMap.put(column, CellType.DATE);
		dateFormatMap.put(column, df);
	}

	/**
	 * 添加合计的列
	 * 
	 * @param sumColumns
	 * @param format
	 * @throws Exception
	 */
	public void addSumColumn(String[] sumColumns) throws Exception {
		for (String str : sumColumns) {
			if (!columnTitleList.contains(str)) {
				this.addSumColumn(str);
			}
		}
	}

	/**
	 * 添加合计的列
	 * 
	 * @param column
	 * @param format
	 * @throws Exception
	 */
	public void addSumColumn(String column) throws Exception {
		if (columnList.contains(column)) {
			sumColumns.add(column);
		}
	}

	/**
	 * 添加渲染的列
	 * 
	 * @param column
	 * @param propKey
	 * @throws Exception
	 */
	public void addPropRenderColumn(String column, String propKey) throws Exception {
		if (columnList.contains(column)) {
			colCellTypeMap.put(column, CellType.STRING);
			renderPropMap.put(column, propKey);
		}
	}
	
	/**
	 * 添加渲染的列
	 * 
	 * @param column
	 * @param propKey
	 * @throws Exception
	 */
	public void addRenderColumn(String column, DjExcelDataRender render) throws Exception {
		if (columnList.contains(column)) {
			colCellTypeMap.put(column, CellType.STRING);
			renderMap.put(column, render);
		}
	}

	/**
	 * Excel 列转化 0->A...
	 * 
	 * @param columnNum
	 * @return
	 */
	private String getColumnKey(Integer columnNum) {
		if (columnNum / 26 > 0) {
			char[] c1 = new char[] { (char) (64 + columnNum / 26), (char) (65 + columnNum % 26) };
			return new String(c1);
		} else {
			char c2 = (char) (65 + columnNum % 26);
			return String.valueOf(c2);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 设定列宽
	 * @param columnsWidth
	 */
	public void setColumnsWidth(Integer[] columnsWidth) {
		this.columnsWidth = columnsWidth;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		if (!StringUitl.isNullOrEmpty(fileName)) {
			if (!fileName.endsWith(".xls")) {
				this.fileName = fileName + ".xls";
			} else {
				this.fileName = fileName;
			}
		}
	}

	/**
	 * 是否增加序号行; 默认为false
	 * 
	 * @param hasSequence
	 */
	public void setSequenceRow(boolean hasSequence) {
		if (hasSequence) {
			startCol = 1;
		} else {
			startCol = 0;
		}
		this.hasSequence = hasSequence;
	}

	/**
	 * 在本地创建Excel
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	 * public <T> String createForLocal(List<T> list) throws Exception { String
	 * path = DjExcelCreator.getSaveDirectoryPath() + fileName; File file = new
	 * File(path); if (!file.isFile()) { file.createNewFile(); } try
	 * (OutputStream os = new FileOutputStream(file)) { this.createExcel(os,
	 * list); }
	 * 
	 * return null; }
	 */

	/**
	 * 获取excel保存的文件夹目录
	 * 
	 * @return
	 */
	/*
	 * public static String getSaveDirectoryPath() { return
	 * ServletActionContext.getServletContext().getRealPath("") + "/excel/"; }
	 */

}
