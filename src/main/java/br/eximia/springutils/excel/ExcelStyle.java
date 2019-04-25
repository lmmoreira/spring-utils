package br.eximia.springutils.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelStyle {
	HSSFWorkbook wb;
	HSSFFont font;
	HSSFCellStyle style;

	final public static short HEADER_LABEL_HORIZONTAL = 1;
	final public static short BODY_LABEL_HORIZONTAL = 2;

	public ExcelStyle(HSSFWorkbook wb) {
		this.wb = wb;
		font = wb.createFont();
		style = wb.createCellStyle();
	}

	public HSSFCellStyle setCellStyle(short index) {

		switch (index) {
		case 1:
			font.setColor(HSSFColor.BLUE.index);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints((short) 8);
			style.setFont(font);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setRotation((short) 0);
			style.setWrapText(true);
			break;
		case 2:
			font.setColor(HSSFColor.BLACK.index);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			font.setFontHeightInPoints((short) 8);
			style.setFont(font);
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			break;
		}

		return style;
	}
}