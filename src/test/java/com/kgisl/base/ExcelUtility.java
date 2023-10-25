package com.kgisl.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class ExcelUtility {

	private String workingDir = System.getProperty("user.dir");

	private XSSFWorkbook wb;
	public XSSFSheet sh;
	public int rownum;
	public int rowcount;
	public ArrayList<Integer> rowValueList;
	List<String> HeaderList = new ArrayList<String>();
	public String ExcelVal = null;
	public String ProjectName =null;

	private int rowNum;
	private int headerRow;

	public Map<String, String> TestDataInMap;
	FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
	private String Summaryfilepath = workingDir + "\\OutputData\\Summary.xlsx";
	private String PolicyNofilepath = workingDir + "\\OutputData\\PolicyNumber.xlsx";
	private String BancsPolNumberPath = workingDir + "\\OutputData\\BancsPolicyNumber.xlsx";
	private String bancsCKYCpath = workingDir + "\\OutputData\\BancsCKYC_VerificationStatus.xlsx";
	public String DBpath = System.getProperty("user.dir") + "/TestData" + "/sqlite/";
	public Statement st;
	public String DBurl = null;
	public String fileName = config.DBname();
	public String EndTime = null;
	Connection con = null;

	public void getTestDataInmap(String testfilepath, String sheetName, String TestcaseID) throws Exception {

		TestDataInMap = new TreeMap<String, String>();
		rowValueList = new ArrayList<Integer>();

		File file = new File(testfilepath);
		boolean matchFound = false;

		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			// XSSFSheet sh = wb.getSheetAt(0);
			sh = wb.getSheet(sheetName);

		}
		headerRow = 0;

		System.out.println("Row Number: " + sh.getLastRowNum());
		// Add row index of the testcase with executionflag not equal to 'N' into the
		// arraylist
		for (int j = 0; j < sh.getLastRowNum() + 1; j++) {
			Row row2 = sh.getRow(j);
			if (row2 != null) {
				Cell cell2 = sh.getRow(j).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				Cell cell3 = sh.getRow(j).getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String TestID = cell2.getStringCellValue();
				String ExecutionFlag = cell3.getStringCellValue();

				if (TestcaseID.equalsIgnoreCase(TestID) && headerRow == 0) {
					headerRow = j - 1;
				}
				if (TestcaseID.equalsIgnoreCase(TestID) && (!ExecutionFlag.equalsIgnoreCase("N"))) {
					matchFound = true;
					rowNum = j;
					rowValueList.add(rowNum);
					if (j < sh.getLastRowNum()) {
						if (sh.getRow(j + 1) != null) {
							cell2 = sh.getRow(j + 1).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							TestID = cell2.getStringCellValue();
							if (!TestcaseID.equalsIgnoreCase(TestID)) {
								break;
							}
						}
					}
				}
			}
		}
		if (matchFound == false) {
			Assert.fail("Given testcase is not provided for execution");
		}

		Row row1 = sh.getRow(headerRow);
		int noOfColumns = row1.getPhysicalNumberOfCells();
		String HeaderValue = null;

		for (int iterateRow = 0; iterateRow < rowValueList.size(); iterateRow++) {

			for (int column = 0; column < noOfColumns; column++) {
				try {

					HeaderValue = null;
					Cell Header = row1.getCell(column, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					HeaderValue = Header.getStringCellValue();

					Cell cellvalue = sh.getRow(rowValueList.get(iterateRow)).getCell(column,
							Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					ExcelVal = null;
					if (cellvalue == null) {
						ExcelVal = null;
					} else {
						switch (cellvalue.getCellType()) {
						case BLANK:
							// ExcelVal = "" ;
							break;
						case NUMERIC:
							ExcelVal = String.valueOf(cellvalue.getNumericCellValue());
							ExcelVal = ExcelVal.split("\\.")[0];
							break;
						case STRING:
							ExcelVal = cellvalue.getStringCellValue();
							ExcelVal = ExcelVal.replaceAll("^\"|\"$", "");
							break;
						case BOOLEAN:
							ExcelVal = String.valueOf(cellvalue.getBooleanCellValue());
							break;
						case FORMULA:

							// ExcelVal = String.valueOf(cellvalue.getCellFormula());

							if (cellvalue.getCachedFormulaResultType() == CellType.NUMERIC) {
								ExcelVal = String.valueOf(cellvalue.getNumericCellValue());
								ExcelVal = ExcelVal.split("\\.")[0];
							}
							if (cellvalue.getCachedFormulaResultType() == CellType.STRING) {
								ExcelVal = cellvalue.getStringCellValue();
								ExcelVal = ExcelVal.replaceAll("^\"|\"$", "");
							}
							if (cellvalue.getCachedFormulaResultType() == CellType.BOOLEAN) {
								ExcelVal = String.valueOf(cellvalue.getBooleanCellValue());
							}
							if (cellvalue.getCachedFormulaResultType() == CellType.BLANK) {
								ExcelVal = "";
							}

							break;
						default:
							ExcelVal = null;
							break;
						}

					}
				}

				catch (Exception e) {

				}
				if (ExcelVal != null) {

					if (rowValueList.size() > 1 && iterateRow >= 1) {

						TestDataInMap.put(HeaderValue + iterateRow, ExcelVal);
						// System.out.println(object[i]);

					} else {

						TestDataInMap.put(HeaderValue, ExcelVal);
						// throw new ThrowsException("Test ID not available");
					}
				}
			}

		}
		System.out.println("test");

	}

	public void getTestDataInmapforzing(String testfilepath, String sheetName, String TestcaseID) throws Exception {

		// Map<String, String>
		TestDataInMap = new TreeMap<String, String>();
		rowValueList = new ArrayList<Integer>();
		// System.setProperty("ROW","18");

		File file = new File(testfilepath);
		boolean matchFound = false;

		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			// XSSFSheet sh = wb.getSheetAt(0);
			sh = wb.getSheet(sheetName);

		}
		headerRow = 0;

		System.out.println("Row Number: " + sh.getLastRowNum());
//Add row index of the testcase with executionflag not equal to 'N' into the arraylist 
		for (int j = 0; j < sh.getLastRowNum() + 1; j++) {
			Row row2 = sh.getRow(j);
			if (row2 != null) {
				Cell cell2 = sh.getRow(j).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				Cell cell3 = sh.getRow(j).getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String TestID = cell2.getStringCellValue();
				String ExecutionFlag = cell3.getStringCellValue();

				if (TestcaseID.equalsIgnoreCase(TestID) && headerRow == 0) {
					headerRow = j - 1;
				}
				if (TestcaseID.equalsIgnoreCase(TestID) && (!ExecutionFlag.equalsIgnoreCase("N"))) {
					matchFound = true;
					rowNum = j;
					rowValueList.add(rowNum);
					if (j < sh.getLastRowNum()) {
						if (sh.getRow(j + 1) != null) {
							cell2 = sh.getRow(j + 1).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							TestID = cell2.getStringCellValue();
							if (!TestcaseID.equalsIgnoreCase(TestID)) {
								break;
							}
						}
					}
				}
			}
		}
		if (matchFound == false) {
			Assert.fail("Given testcase is not provided for execution");
		}

		Row row1 = sh.getRow(headerRow);
		int noOfColumns = row1.getPhysicalNumberOfCells();
		String HeaderValue = null;

		for (int iterateRow = 0; iterateRow <= rowValueList.size(); iterateRow++) {

			for (int column = 0; column < noOfColumns; column++) {
				try {

					HeaderValue = null;
					Cell Header = row1.getCell(column, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					HeaderValue = Header.getStringCellValue();

					Cell cellvalue = sh.getRow(rowValueList.get(iterateRow)).getCell(column,
							Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					ExcelVal = null;
					if (cellvalue == null) {
						ExcelVal = null;
					} else {
						switch (cellvalue.getCellType()) {
						case BLANK:
//  		         ExcelVal = "" ;
							break;
						case NUMERIC:
							ExcelVal = String.valueOf(cellvalue.getNumericCellValue());
							ExcelVal = ExcelVal.split("\\.")[0];
							break;
						case STRING:
							ExcelVal = cellvalue.getStringCellValue();
							ExcelVal = ExcelVal.replaceAll("^\"|\"$", "");
							break;
						case BOOLEAN:
							ExcelVal = String.valueOf(cellvalue.getBooleanCellValue());
							break;
						case FORMULA:
//  			   ExcelVal = cellvalue.getCellFormula();
							if (cellvalue.getCachedFormulaResultType() == CellType.NUMERIC) {
								ExcelVal = String.valueOf(cellvalue.getNumericCellValue());
								ExcelVal = ExcelVal.split("\\.")[0];
							}
							if (cellvalue.getCachedFormulaResultType() == CellType.STRING) {
								ExcelVal = cellvalue.getStringCellValue();
								ExcelVal = ExcelVal.replaceAll("^\"|\"$", "");
							}
							if (cellvalue.getCachedFormulaResultType() == CellType.BOOLEAN) {
								ExcelVal = String.valueOf(cellvalue.getBooleanCellValue());
							}
							if (cellvalue.getCachedFormulaResultType() == CellType.BLANK) {
								ExcelVal = "";
							}
							break;
						default:
							ExcelVal = null;
							break;
						}

					}
				}

				catch (Exception e) {

				}
				if (ExcelVal != null) {

					// if(rowValueList.size()>1 && iterateRow >=1) {

					TestDataInMap.put(HeaderValue + iterateRow, ExcelVal);
					// System.out.println(object[i]);

					/*
					 * } else {
					 * 
					 * TestDataInMap.put(HeaderValue,ExcelVal); //throw new
					 * ThrowsException("Test ID not available"); }
					 */
				}
			}

		}
		System.out.println("test");

	}

	public void generateReports(String startTime, String browser, String browserversion)
			throws InterruptedException, SQLException, FileNotFoundException, IOException, Exception {
		Thread.sleep(1000);
		
		if(config.RunType().equalsIgnoreCase("LOCAL")){
			ProjectName = config.Project();
		}
		else {
			ProjectName =System.getProperty("Project");
		}

		String EndTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new java.util.Date());		
		//Get Duration
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        // Parse the input dates
        LocalDateTime startDate = LocalDateTime.parse(startTime, dateFormatter);
        LocalDateTime endDate = LocalDateTime.parse(EndTime, dateFormatter);

        // Calculate the duration between the two dates
        Duration duration = Duration.between(startDate, endDate);

        // Format the duration into the desired format
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        String formattedDuration = String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);

        System.out.println("Duration: " + formattedDuration);

        
     // Split the input duration string
        String[] parts = formattedDuration.split("[\\s:,]+");

        // Parse days, hours, minutes, and seconds
        long daysDuration = Long.parseLong(parts[0]);
        long hoursDuration = Long.parseLong(parts[2]);
        long minutesDuration = Long.parseLong(parts[3]);
        long secondsDuration = Long.parseLong(parts[4]);

        // Calculate total hours
        long totalHours = TimeUnit.DAYS.toHours(daysDuration) + hoursDuration;

        // Format the duration as "hh:mm:ss"
        String formattedHours = String.format("%02d:%02d:%02d", totalHours, minutesDuration, secondsDuration);

        System.out.println("Formatted Duration: " + formattedDuration);
        
        ////Excel updates
		File Summary = new File(Summaryfilepath);
		String sql = "select * from Summary;";
		
		//String AppNameQuery = "SELECT Distinct(ApplicationName) FROM Summary;";
		String AppStatus = "SELECT ApplicationName, COUNT(*) AS total_count,\r\n"
				+ "    COUNT(CASE WHEN Status = 'PASSED' THEN 1 ELSE NULL END) AS pass_count,\r\n"
				+ "    COUNT(CASE WHEN Status = 'FAILED' THEN 1 ELSE NULL END) AS fail_count,\r\n"
				+ "    ROUND((COUNT(CASE WHEN Status = 'PASSED' THEN 1 ELSE NULL END) * 100.0 / COUNT(*)), 2) AS pass_percentage,\r\n"
				+ "    ROUND((COUNT(CASE WHEN Status = 'FAILED' THEN 1 ELSE NULL END) * 100.0 / COUNT(*)), 2) AS fail_percentage\r\n"
				+ "FROM Summary GROUP BY ApplicationName;";
		
		String OverAllCount = "SELECT COUNT(*) AS total_count,\r\n"
				+ "    COUNT(CASE WHEN Status = 'PASSED' THEN 1 ELSE NULL END) AS pass_count,\r\n"
				+ "    COUNT(CASE WHEN Status = 'FAILED' THEN 1 ELSE NULL END) AS fail_count,\r\n"
				+ "    ROUND((COUNT(CASE WHEN Status = 'PASSED' THEN 1 ELSE NULL END) * 100.0 / COUNT(*)), 2) AS pass_percentage,\r\n"
				+ "    ROUND((COUNT(CASE WHEN Status = 'FAILED' THEN 1 ELSE NULL END) * 100.0 / COUNT(*)), 2) AS fail_percentage\r\n"
				+ "FROM Summary";
		
		
		String url = "jdbc:sqlite:" + DBpath + fileName + ".db";
		
		System.out.println(url);

		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection(url);
		Statement stmt = con.createStatement();
		Statement stmt2 = con.createStatement();
		Statement stmt3 = con.createStatement();
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		

		try {
			rs = stmt.executeQuery(sql);
			System.out.println("Result set: " + rs);
		} catch (Exception e) {

		}
		
		// Write report in Excel
		FileInputStream fis = new FileInputStream(Summary);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		workbook.removeSheetAt(workbook.getSheetIndex("Detailed Report"));
		workbook.removeSheetAt(workbook.getSheetIndex("Application Summary"));
		
		// XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet Sheet3 = workbook.createSheet("Application Summary");
		XSSFSheet Sheet = workbook.createSheet("Detailed Report");
				
		workbook.setSheetOrder("Application Summary",1);
		workbook.setSheetOrder("Detailed Report",2);
		
		XSSFSheet Sheet2 = workbook.getSheet("Summary");

		XSSFRow row = Sheet.createRow(0);

		row.createCell(0).setCellValue("Application Name");
		row.createCell(5).setCellValue("TestDataID");
		row.createCell(2).setCellValue("ScenarioName");
		row.createCell(3).setCellValue("Status");
		row.createCell(4).setCellValue("Failure Reason");
		row.createCell(1).setCellValue("Date");
		row.createCell(6).setCellValue("TestData");

		
		  XSSFRow row3 = Sheet3.createRow(0);
		  
		  row3.createCell(0).setCellValue("Application Name");
		  row3.createCell(1).setCellValue("Total TestCase");
		  row3.createCell(2).setCellValue("Pass");
		  row3.createCell(3).setCellValue("Pass %");
		  row3.createCell(4).setCellValue("Fail");
		  row3.createCell(5).setCellValue("Fail %");
		 
		

		
		int r = 1;
		int r1 = 1;

		while (rs.next()) {
			String Application = rs.getString("ApplicationName");
			String TestCaseID = rs.getString("TestCaseID");
			String ScenarioName = rs.getString("ScenarioName");
			String Status = rs.getString("Status");
			String FailureReason = rs.getString("FailureReason");
			String StartTime = rs.getString("TIMESTAMP");
			String TestData = rs.getString("TestData");
			// Timestamp Timestamp = rs.getTimestamp("TIMESTAMP");
			Thread.sleep(500);
//			System.out.println("TestCaseId: " + TestCaseID);
//			System.out.println("ScenarioName " + ScenarioName);
//			System.out.println("Status " + Status);
//			System.out.println("Date " + StartTime);

			row = Sheet.createRow(r++);
			row.createCell(0).setCellValue(Application);
			if(TestCaseID == null) {
				row.createCell(5).setCellValue("N/A");
			}
			else {
			row.createCell(5).setCellValue(TestCaseID);
			}
			row.createCell(2).setCellValue(ScenarioName);
			row.createCell(3).setCellValue(Status);
			row.createCell(1).setCellValue(StartTime);
			row.createCell(4).setCellValue(FailureReason);
			row.createCell(6).setCellValue(TestData);

		}
		
		try {
			rs2 = stmt2.executeQuery(AppStatus);
			System.out.println("Result set: " + rs);
		} catch (Exception e) {

		}
		while (rs2.next()) {
			String Application = rs2.getString("ApplicationName");
			int Total = rs2.getInt("total_count");
			int Pass = rs2.getInt("pass_count");
			int Fail = rs2.getInt("fail_count");
			double Pass_Percent = rs2.getDouble("pass_percentage");
			double Fail_Percent = rs2.getDouble("fail_percentage");
			
			row3 = Sheet3.createRow(r1++);
			row3.createCell(0).setCellValue(Application);
			row3.createCell(1).setCellValue(Total);
			row3.createCell(2).setCellValue(Pass);
			row3.createCell(3).setCellValue(Pass_Percent);
			row3.createCell(4).setCellValue(Fail);
			row3.createCell(5).setCellValue(Fail_Percent);

		}
		
		try {
			rs3 = stmt3.executeQuery(OverAllCount);
			System.out.println("Result set: " + rs3);
		} catch (Exception e) {

		}
		while (rs3.next()) {
			
			int Total = rs3.getInt("total_count");
			int Pass = rs3.getInt("pass_count");
			int Fail = rs3.getInt("fail_count");
			double Pass_Percent = rs3.getDouble("pass_percentage");
			double Fail_Percent = rs3.getDouble("fail_percentage");
			
			row3 = Sheet3.createRow(r1++);
			
			row3.createCell(1).setCellValue(Total);
			row3.createCell(2).setCellValue(Pass);
			row3.createCell(3).setCellValue(Pass_Percent);
			row3.createCell(4).setCellValue(Fail);
			row3.createCell(5).setCellValue(Fail_Percent);

		}

		//ProjectName
		Cell projectname = Sheet2.getRow(1).createCell(1);
		if (projectname != null && projectname.getCellType() != CellType.BLANK) {
			projectname.setBlank();
		}

		// cell1.setCellValue(report.getOSname());
		projectname.setCellValue(ProjectName);
		projectname.setCellStyle(setStyle(workbook));
		
		
		Cell cell1 = Sheet2.getRow(2).createCell(1);
		if (cell1 != null && cell1.getCellType() != CellType.BLANK) {
			cell1.setBlank();
		}

		// cell1.setCellValue(report.getOSname());
		cell1.setCellValue(System.getProperty("os.name"));
		cell1.setCellStyle(setStyle(workbook));

		Cell cell2 = Sheet2.getRow(3).createCell(1);
		if (cell2 != null && cell2.getCellType() != CellType.BLANK) {
			cell2.setBlank();
		}
		cell2.setCellValue(browser);
		cell2.setCellStyle(setStyle(workbook));

		Cell cell3 = Sheet2.getRow(4).createCell(1);
		if (cell3 != null && cell3.getCellType() != CellType.BLANK) {
			cell3.setBlank();
		}
		cell3.setCellValue(browserversion);
		cell3.setCellStyle(setStyle(workbook));

		Cell cell4 = Sheet2.getRow(5).createCell(1);
		if (cell4 != null && cell4.getCellType() != CellType.BLANK) {
			cell4.setBlank();
		}
		cell4.setCellValue(startTime);
		cell4.setCellStyle(setStyle(workbook));

		Cell cell5 = Sheet2.getRow(6).createCell(1);
		if (cell5 != null && cell5.getCellType() != CellType.BLANK) {
			cell5.setBlank();
		}
		cell5.setCellValue(EndTime);
		cell5.setCellStyle(setStyle(workbook));
		
		Cell cell6 = Sheet2.getRow(7).createCell(1);
		if (cell6 != null && cell6.getCellType() != CellType.BLANK) {
			cell6.setBlank();
		}
		cell6.setCellValue(formattedHours);
		cell6.setCellStyle(setStyle(workbook));

		CellRangeAddress region = new CellRangeAddress(1, 7, 1, 1);
		RegionUtil.setBorderBottom(BorderStyle.MEDIUM, region, Sheet2);
		RegionUtil.setBorderTop(BorderStyle.MEDIUM, region, Sheet2);
		RegionUtil.setBorderLeft(BorderStyle.MEDIUM, region, Sheet2);
		RegionUtil.setBorderRight(BorderStyle.MEDIUM, region, Sheet2);
		String timeStamp = new SimpleDateFormat("ddMMMyy_HHmmss").format(Calendar.getInstance().getTime());
		File ExcelReport = new File(workingDir + "\\ExcelOutput\\Summary" + timeStamp + ".xlsx");
		
		 XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

		FileOutputStream fos = new FileOutputStream(Summary);
		workbook.write(fos);
		workbook.close();
		fos.close();
		con.close();
		FileUtils.copyFile(Summary, ExcelReport);
	}

	public void generatePolicyNoReports()
			throws InterruptedException, SQLException, FileNotFoundException, IOException, Exception {
		Thread.sleep(1000);

		File policy = new File(PolicyNofilepath);
		String sql = "select * from policyNo;";
		String url = "jdbc:sqlite:" + DBpath + fileName + ".db";
		System.out.println(url);

		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection(url);
		Statement stmt = con.createStatement();
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			System.out.println("Result set: " + rs);
		} catch (Exception e) {

		}

		// Write report in Excel
		FileInputStream fis = new FileInputStream(policy);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		workbook.removeSheetAt(workbook.getSheetIndex("PolicyNo"));
		// XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet Sheet = workbook.createSheet("PolicyNo");
		XSSFRow row = Sheet.createRow(0);

		row.createCell(0).setCellValue("TestDataID");
		row.createCell(1).setCellValue("ApplicationName");
		row.createCell(2).setCellValue("PolicyNo");

		int r = 1;

		while (rs.next()) {
			String TestCaseID = rs.getString("TestCaseID");
			String ApplicationName = rs.getString("applicationName");
			String policyNo = rs.getString("policyNo");
			Thread.sleep(500);

			row = Sheet.createRow(r++);
			row.createCell(0).setCellValue(TestCaseID);
			row.createCell(1).setCellValue(ApplicationName);
			row.createCell(2).setCellValue(policyNo);

		}

		FileOutputStream fos = new FileOutputStream(policy);
		workbook.write(fos);
		workbook.close();
		fos.close();
		con.close();
	}

	public void generateBancsPolNumber()
			throws InterruptedException, SQLException, FileNotFoundException, IOException, Exception {
		Thread.sleep(1000);

		File policy = new File(BancsPolNumberPath);
		String sql = "select * from BancsPolicyNumber;";
		String url = "jdbc:sqlite:" + DBpath + fileName + ".db";
		System.out.println(url);

		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection(url);
		Statement stmt = con.createStatement();
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			System.out.println("Result set: " + rs);
		} catch (Exception e) {

		}

		// Write report in Excel
		FileInputStream fis = new FileInputStream(policy);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		workbook.removeSheetAt(workbook.getSheetIndex("PolicyNumber"));
		// XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet Sheet = workbook.createSheet("PolicyNumber");
		XSSFRow row = Sheet.createRow(0);

		row.createCell(0).setCellValue("TestData ID");
		row.createCell(1).setCellValue("Application Name");
		row.createCell(2).setCellValue("Policy Number");

		int r = 1;

		while (rs.next()) {
			String TestCaseID = rs.getString("TestCaseID");
			String ApplicationName = rs.getString("applicationName");
			String AgentNo = rs.getString("PolicyNumber");
			Thread.sleep(500);

			row = Sheet.createRow(r++);
			row.createCell(0).setCellValue(TestCaseID);
			row.createCell(1).setCellValue(ApplicationName);
			row.createCell(2).setCellValue(AgentNo);

		}
		String timeStamp = new SimpleDateFormat("ddMMMyy_HHmmss").format(Calendar.getInstance().getTime());
		File ExcelReport = new File(workingDir + "\\ExcelOutput\\BancsPolicyNumber" + timeStamp + ".xlsx");

		FileOutputStream fos = new FileOutputStream(policy);
		workbook.write(fos);
		workbook.close();
		fos.close();
		con.close();
		FileUtils.copyFile(policy, ExcelReport);
	}
	
	public void generatebancsCKYCReports()
			throws InterruptedException, SQLException, FileNotFoundException, IOException, Exception {
		Thread.sleep(1000);

		File ckycStatus = new File(bancsCKYCpath);
		String sql = "select * from BancsCKYCstatus;";
		String url = "jdbc:sqlite:" + DBpath + fileName + ".db";
		System.out.println(url);

		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection(url);
		Statement stmt = con.createStatement();
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			System.out.println("Result set: " + rs);
		} catch (Exception e) {

		}

		// Write report in Excel
		FileInputStream fis = new FileInputStream(ckycStatus);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		workbook.removeSheetAt(workbook.getSheetIndex("CKYC_Status"));
		// XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet Sheet = workbook.createSheet("CKYC_Status");
		XSSFRow row = Sheet.createRow(0);

		row.createCell(0).setCellValue("TestDataID");
		row.createCell(1).setCellValue("CustomerName");
		row.createCell(2).setCellValue("DateOfIncorporation");
		row.createCell(3).setCellValue("DocumentType");
		row.createCell(4).setCellValue("DocumentNumber");
		row.createCell(5).setCellValue("status");

		int r = 1;

		while (rs.next()) {
			String TestCaseID = rs.getString("TestCaseID");
			String CustomerName = rs.getString("CustomerName");
			String DateOfIncorporation = rs.getString("DateOfIncorporation");
			String DocumentType = rs.getString("DocumentType");
			String DocumentNumber = rs.getString("DocumentNumber");
			String status = rs.getString("status");
			Thread.sleep(500);

			row = Sheet.createRow(r++);
			row.createCell(0).setCellValue(TestCaseID);
			row.createCell(1).setCellValue(CustomerName);
			row.createCell(2).setCellValue(DateOfIncorporation);
			row.createCell(3).setCellValue(DocumentType);
			row.createCell(4).setCellValue(DocumentNumber);
			row.createCell(5).setCellValue(status);

		}

		FileOutputStream fos = new FileOutputStream(ckycStatus);
		workbook.write(fos);
		workbook.close();
		fos.close();
		con.close();
	}

	public CellStyle setStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		return cellStyle;

	}
}