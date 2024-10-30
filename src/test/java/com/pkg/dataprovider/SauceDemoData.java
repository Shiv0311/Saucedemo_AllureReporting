package com.pkg.dataprovider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class SauceDemoData {
	
	@DataProvider(name = "create")
	public Object[][] userDetails() throws EncryptedDocumentException, IOException {
		List<Object[]> dataList = new ArrayList<>();

		File f = new File("D:\\InputDetails\\SauceDemoLoginData.xlsx");
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet0 = wb.getSheetAt(0);
		System.out.println("RowSize = "+sheet0.getLastRowNum());
		System.out.println("CellSize = "+sheet0.getRow(0).getLastCellNum());

		for (int i = 1; i <= sheet0.getLastRowNum(); i++) {
			Row row = sheet0.getRow(i);
			if (row != null) {
				Object[] rowData = new Object[sheet0.getRow(0).getLastCellNum()];
				for (int j = 0; j < sheet0.getRow(0).getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					if (cell != null) {
						switch (cell.getCellType()) {
						case STRING:
							rowData[j] = cell.getStringCellValue();
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								rowData[j] = cell.getDateCellValue();
							} else {
								rowData[j] = cell.getNumericCellValue();
							}
							break;
						case BOOLEAN:
							rowData[j] = cell.getBooleanCellValue();
							break;
						case FORMULA:
							rowData[j] = cell.getCellFormula();
							break;
						default:
							rowData[j] = null;
							break;
						}
					} else {
						rowData[j] = null;
					}
				}

				dataList.add(rowData);
			}
		}
		System.out.println(dataList.size());
		Object[][] dataArray = new Object[dataList.size()][];
		dataArray = dataList.toArray(dataArray);

		return dataArray;
	}


}
