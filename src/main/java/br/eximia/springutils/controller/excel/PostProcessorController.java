package br.eximia.springutils.controller.excel;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.eximia.springutils.excel.ExcelStyle;

@Component
@Scope("session")
@Qualifier("postProcessorController")
public class PostProcessorController {

	public void postProcessorXLS(Object document) {

		HSSFWorkbook wb = (HSSFWorkbook) document;
		ExcelStyle styleHeader = new ExcelStyle(wb);
		ExcelStyle styleBody = new ExcelStyle(wb);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			header.getCell(i).setCellStyle(styleHeader.setCellStyle(ExcelStyle.HEADER_LABEL_HORIZONTAL));
			sheet.autoSizeColumn(i);
		}
		//
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				row.getCell(i).setCellStyle(styleBody.setCellStyle(ExcelStyle.BODY_LABEL_HORIZONTAL));
			}
		}


	}

}
