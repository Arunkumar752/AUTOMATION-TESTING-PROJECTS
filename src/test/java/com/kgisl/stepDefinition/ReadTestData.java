package com.kgisl.stepDefinition;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.aeonbits.owner.ConfigFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aventstack.extentreports.App;
import com.kgisl.base.Data_Set;
import com.kgisl.base.ExcelUtility;
import com.kgisl.base.FrameworkConfig;
import com.kgisl.library.ReportOut;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

public class ReadTestData {

	private String TestId = null;
	private String sheetName = null;
	private Map<String, String> InputData;

	private Data_Set Data;
	private ExcelUtility readExcel;
	private ReportOut report;	
	private String workingDir = System.getProperty("user.dir");
	public String testfilepath;
	FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);

	public ReadTestData(Data_Set DataSet, ExcelUtility ReadExcel, ReportOut report) {
		this.Data = DataSet;
		this.readExcel = ReadExcel;
		this.report = report;
	}

	@Given("Read the input data for the {string} Application")
	public void ReadInputData(String Application, DataTable sheet) throws Exception {
		report.setApplicationName(Application);				

		InputData = new HashMap<String, String>();
		InputData = sheet.asMap(String.class, String.class);

		TestId = InputData.get("TestData");
		System.out.println("TestCase ID:" + TestId);

		Data.setTestcaseID(TestId);

		if (InputData.get("sheetName") != null && !InputData.get("sheetName").isEmpty()) {
			sheetName = InputData.get("sheetName");
		} else {
			sheetName = config.DataSheetName();
		}
		Data.setSheet(sheetName);
		System.out.println("sheetName :" + sheetName);
		
		testfilepath = workingDir + "\\TestData\\" + Application + "_TestData.xlsx";
		if(Application.equalsIgnoreCase("CSC")||Application.equalsIgnoreCase("ADP")||Application.equalsIgnoreCase("V4")||Application.equalsIgnoreCase("Phoenix")) {
			FileInputStream fs = new FileInputStream(testfilepath);		
	    	XSSFWorkbook workbook = new XSSFWorkbook(fs);
	    	XSSFSheet sheet1 = workbook.getSheet("DateFormat");    	
	    	Row row = sheet1.getRow(1);
	    	Cell cell = row.getCell(0);
	    	String Dateformat=cell.getStringCellValue();
	    	report.setPhoenixDateFormat(Dateformat);
	    	workbook.close();
		}

		System.out.println("testfilepath: " + testfilepath);

		readExcel.getTestDataInmap(testfilepath, sheetName, TestId);
		
		if (Application.equalsIgnoreCase("CSC") || Application.equalsIgnoreCase("ADP")
				|| Application.equalsIgnoreCase("V4") || Application.equalsIgnoreCase("Phoenix")) {
			FileInputStream fs = new FileInputStream(testfilepath);
			XSSFWorkbook workbook = new XSSFWorkbook(fs);
			XSSFSheet sheet1 = workbook.getSheet("DateFormat");
			Row row = sheet1.getRow(1);
			Cell cell = row.getCell(0);
			String Dateformat = cell.getStringCellValue();
			report.setPhoenixDateFormat(Dateformat);
			workbook.close();
		}

		if (Application.equalsIgnoreCase("BANCS") || Application.equalsIgnoreCase("New Portal motor_Renewal")
				|| Application.equalsIgnoreCase("Policy Search Portal")
				|| Application.equalsIgnoreCase("Proposal Print Portal")
				|| Application.equalsIgnoreCase("Remote Certificate Printing")
				|| Application.equalsIgnoreCase("CustomerClaims")) {
			Data.setReportAppName(Application);
		}
		if (Application.equalsIgnoreCase("NEOCORE")) {
			Data.setReportAppName("E-Policy Portal (Neocore)");
		}
		if (sheetName.contains("CKYC")) {
			Data.setReportAppName("BaNCS CKYC Integration");
		}
		if (Application.equalsIgnoreCase("Cordys")) {
			Data.setReportAppName("Endorsement Request Creation_Cordys");
		}
	}
}


