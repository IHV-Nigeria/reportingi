/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openmrs.module.nmrsreports.api.dao;

/**
 *
 * @author ihvn
 */
/*
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

public class ExcelExport2 extends AbstractXlsxView {
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
	        HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// define excel file name to be exported
		
		// read data provided by controller
		@SuppressWarnings("unchecked")
		JSONArray headerGroup = (JSONArray) model.get("headers");
		JSONArray body = (JSONArray) model.get("body");
		String title = (String) model.get("title");
		
		response.addHeader("Content-Disposition", "attachment;fileName=" + title + ".xlsx");
		
		// create one sheet
		Sheet sheet = workbook.createSheet(title);
		
		// create row0 as a header
		int rowCounter = 0;
		
		int columnCounter = 0;
		
		CellStyle style = workbook.createCellStyle();
		
		XSSFCellStyle cs1 = (XSSFCellStyle) workbook.createCellStyle();
		//cs1.setFillForegroundColor(new XSSFColor(new java.awt.Color(100,100,100)));          
		//cs1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		style.setWrapText(true);
		style.setRightBorderColor(IndexedColors.WHITE.getIndex());
		style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
		XSSFFont font = (XSSFFont) workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Arial");
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBold(true);
		font.setItalic(false);
		style.setFont(font);
		
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		
		int defaultWidth = 5000;
		
		//we are doing this to be able to have multiple header rows with the ability to have merged cells
		int currColumn = 0;
		for (int i = 0; i < headerGroup.length(); i++) {
			
			JSONArray headers = headerGroup.getJSONArray(i);
			Row row0 = sheet.createRow(rowCounter);
			
			int highestRowMerged = 0;
			for (int j = 0; j < headers.length(); j++) {
				
				JSONObject header = headers.getJSONObject(j);
				int numRows = header.has("rows") ? header.getInt("rows") : 0;
				int numCols = header.has("cols") ? header.getInt("cols") : 0;
				int startCol = header.has("col") ? header.getInt("col") : 0;//the header can tell us where it wants to be placed
				int startRow = header.has("row") ? header.getInt("row") : rowCounter;//the header can tell us where it wants to be placed
				String headerTitle = header.getString("title");
				
				int firstRow = startRow;
				int lastRow = firstRow + numRows;
				
				currColumn = (startCol > 0) ? startCol : columnCounter++;
				int firstCol = currColumn;
				int lastCol = currColumn + numCols;
				columnCounter = lastCol + 1;//do this incase we are merging one or more cells
				
				if (lastRow > firstRow || lastCol > firstCol)//ensure that we are merging at least two or more cells
					sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
				Cell cell = row0.createCell(currColumn);
				sheet.setColumnWidth(currColumn, defaultWidth * (numRows + 1));//set the width in multiples of 5000. (incase the cells are merged)
				cell.setCellStyle(style);
				cell.setCellValue(headerTitle);
				
				//lets get the highest row that was merged, if any
				rowCounter = startRow;//this is necessary in case we have moved to another row
				highestRowMerged = Math.max(lastRow, highestRowMerged);
			}
			rowCounter = rowCounter + highestRowMerged;
			if (rowCounter == 0)
				rowCounter++;
			
		}
		
		//int rowNum = 1;
		for (int i = 0; i < body.length(); i++) {
			Row row = sheet.createRow(rowCounter);
			JSONArray subBody = body.getJSONArray(i);
			for (int j = 0; j < subBody.length(); j++) {
				row.createCell(j).setCellValue(subBody.get(j).toString());
				
				//row.createCell(j).setCellValue("hleoo");
			}
			
			rowCounter++;
		}
		
	}
	
	//@Override
	protected void buildExcelDocument2(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
	        HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// define excel file name to be exported
		response.addHeader("Content-Disposition", "attachment;fileName=InvoiceData.xlsx");
		
		// read data provided by controller
		@SuppressWarnings("unchecked")
		JSONArray list = (JSONArray) model.get("body");
		
		// create one sheet
		Sheet sheet = workbook.createSheet("Invoice");
		
		// create row0 as a header
		Row row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("ID");
		row0.createCell(1).setCellValue("NAME");
		row0.createCell(2).setCellValue("LOCATION");
		row0.createCell(3).setCellValue("AMOUNT");
		
		// create row1 onwards from List<T>
		int rowNum = 1;
		/*for(User spec : list) {
		    Row row = sheet.createRow(rowNum++);
		    row.createCell(0).setCellValue(spec.getId());
		    row.createCell(1).setCellValue(spec.getUsername());
		}*/

//	}
//}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.List;
import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.view.document.AbstractXlsxView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.json.JSONArray;
//import org.json.JSONObject;
import org.openmrs.module.nmrsreports.ARTParams;

//import org.springframework.web.servlet.view.document.AbstractXlsxView;

/*
public class ExcelExport2 extends AbstractXlsxView {
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
	        HttpServletResponse response) throws Exception {
		
		// define excel file name to be exported
		String title = (String) model.get("title");
		response.addHeader("Content-Disposition", "attachment;fileName=" + title + ".xlsx");
		
		// read data provided by controller
		@SuppressWarnings("unchecked")
		JSONArray headerGroup = (JSONArray) model.get("headers");
		JSONArray body = (JSONArray) model.get("body");
		
		// create one sheet
		Sheet sheet = workbook.createSheet(title);
		
		// create row0 as a header
		int rowCounter = 0;
		
		CellStyle style = createHeaderStyle(workbook);
		
		int defaultWidth = 5000;
		// Create header rows based on the headerGroup
		for (int i = 0; i < headerGroup.length(); i++) {
			JSONArray headers = headerGroup.getJSONArray(i);
			Row headerRow = sheet.createRow(rowCounter);
			
			int columnCounter = 0;
			for (int j = 0; j < headers.length(); j++) {
				JSONObject header = headers.getJSONObject(j);
				String headerTitle = header.getString("title");
				Cell cell = headerRow.createCell(columnCounter);
				sheet.setColumnWidth(columnCounter, defaultWidth);
				cell.setCellStyle(style);
				cell.setCellValue(headerTitle);
				columnCounter++;
			}
			rowCounter++;
		}
		
		// Create data rows
		for (int i = 0; i < body.length(); i++) {
			Row row = sheet.createRow(rowCounter);
			JSONArray rowData = body.getJSONArray(i);
			for (int j = 0; j < rowData.length(); j++) {
				row.createCell(j).setCellValue(rowData.getString(j));
			}
			rowCounter++;
		}
	}

	private CellStyle createHeaderStyle(Workbook workbook) {
		XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
		style.setWrapText(true);
		style.setRightBorderColor(IndexedColors.WHITE.getIndex());
		style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
		XSSFFont font = (XSSFFont) workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Arial");
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBold(true);
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.CENTER);
		return style;
	}
}
*/

/*
public class ExcelExport2 extends AbstractXlsxView {
	
	@Override
	public void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
	        HttpServletResponse response) throws Exception {
		List<ARTParams> artLinelistData = (List<ARTParams>) model.get("artLinelistData");
		String title = (String) model.get("title");
		
		// Create sheet and headers
		Sheet sheet = workbook.createSheet(title);
		createHeaders(sheet);
		
		// Populate data
		int rowNum = 1;
		for (ARTParams param : artLinelistData) {
			Row row = sheet.createRow(rowNum++);
			populateRow(row, param);
		}
		
		// Set response headers
		response.setHeader("Content-Disposition", "attachment; filename=" + title + ".xlsx");
	}
	
	private void createHeaders(Sheet sheet) {
		// Implement header creation
	}
	
	private void populateRow(Row row, ARTParams param) {
		// Implement row population
	}
}
*/
