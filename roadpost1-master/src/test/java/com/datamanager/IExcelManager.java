package com.datamanager;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
/**
 * This interface has to be implemented by ExcelManager
 */

public interface IExcelManager {
	/**
	 * Purpose - This method returns the entire column data based on the columIndex
	 * @param sheetName - Name of the excel sheet
	 * @param columnIndex - Pass the column Index we want to retrieve
	 * @return - ColumnData as List<String>
	 */
     List<String> getExcelColumnData(String sheetName, int columnIndex);
    
    /**
	 * Purpose - This method returns the entire column data based on the column name
	 * @param sheetName - Name of the excel sheet
	 * @param columnName - Pass the columnName we want to retrieve
	 * @return - ColumnData as List<String>
	 */
    
	 List<String> getExcelColumnData(String sheetName, String columnName);
	/**
	 * Purpose - To get cell data from Excel sheet by passing row and column positions
	 * @param sheetName  - Name of the excel sheet
	 * @param columnIndex- Index of column
	 * @param rowIndex- Index of row
	 * @return - value of the specified cell as String
	 */

	
	 String getExcelCellData(String sheetName, int columnIndex, int rowIndex);
	/**
	 * Purpose - To get cell data from Excel sheet by passing Column name and row index
	 * @param sheetName  - Name of the Excel sheet
	 * @param columnName- Name of the column
	 * @param rowIndex- Index of row
	 * @return - value of the specified cell as String
	 */

	 String getExcelCellData(String sheetName, String columnName, int rowIndex);
	/**
	 * Purpose - To get sheetData
	 * @param sheetName- Name of the Excel sheet
	 * @return - returns sheet data as two dimensional String array 
	 */
	 String[][] getExcelSheetData(String sheetName);
	/**
	 * Purpose - To get row count 
	 * @param sheetName - Name of the sheet
	 * @return returns row count as integer
	 */
     int getRowCount(String sheetName);
    /**
     * Purpose - To get Column count
     * @param SheetName - Name of the sheet
     * @return -returns column count as integer
     */
     int getColumnCount(String SheetName);
    /**
     * Purpose - To add a row to the existing Excel sheet
     * @param sheetName - Name off the Excel sheet
     * @param rowData - Pass the data we want to insert in to the row
     */
	 void addExcelRowData(String sheetName, String[] rowData);
	/***
	 * Purpose - method helps in reading all the data available in workbook(with all sheets, rows
	 * @param xlFilePath  - path of an excel location
	 * @return LinkedHashMap with each sheet as key and value as corresponding sheet data
	 *
	 */
	LinkedHashMap readCompleteExcelFile(String xlFilePath);



}
