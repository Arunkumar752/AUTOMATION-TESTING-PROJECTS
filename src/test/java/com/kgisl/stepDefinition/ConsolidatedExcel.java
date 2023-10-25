package com.kgisl.stepDefinition;

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

import com.kgisl.base.FrameworkConfig;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

public class ConsolidatedExcel {

	private static String workingDir = System.getProperty("user.dir");

	private XSSFWorkbook wb;
	public XSSFSheet sh;
	public int rownum;
	public int rowcount;
	public ArrayList<Integer> rowValueList;
	List<String> HeaderList = new ArrayList<String>();
	public String ExcelVal = null;

	public static String ProjectName = null;

	private int rowNum;
	private int headerRow;

	public Map<String, String> TestDataInMap;
	static FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
	// private String Summaryfilepath = workingDir +
	// "\\OutputData\\Integration.xlsx";
	private static String Summaryfilepath = workingDir + "\\Integration.xlsx";
	public static String DBpath = System.getProperty("user.dir") + "/Chola/";
	public Statement st;
	public String DBurl = null;
	public static String fileName = config.DBname();
	public String EndTime = null;
	static Connection con = null;
	public static int TotalDB = 0;

	public static void main(String[] args)
			throws Exception, InterruptedException, SQLException, IOException, Exception {
		// TotalDB = countFilesWithStartName(System.getProperty("user.dir"),fileName);
		mergeDB();
		generateReports();

	}

	public static void generateReports()
			throws InterruptedException, SQLException, FileNotFoundException, IOException, Exception {
		Thread.sleep(1000);

		if (config.RunType().equalsIgnoreCase("LOCAL")) {
			ProjectName = config.Project();
		} else {
			ProjectName = System.getProperty("Project");
		}

		String url = "jdbc:sqlite:" + workingDir + "/master.db";

		System.out.println(url);
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection(url);
		
		String OSname = null;
		String browser = null;
		String browserversion = null;
		String ExecutionStart ="";
		String ExecutionEnd = "";
		
		
		String StartedTime = "SELECT MIN(timestamp) AS least_timestamp FROM Summary;";
		String EndedTime = "SELECT MAX(timestamp) AS highest_timestamp FROM Summary;";
		
		Statement stmt5 = con.createStatement();
		Statement stmt6 = con.createStatement();
		
		ResultSet rs5 = null;
		ResultSet rs6 = null;
		
		
		try {
			rs5 = stmt5.executeQuery(StartedTime);
			//System.out.println("Result set: " + rs4);
			while (rs5.next()) {
				ExecutionStart = rs5.getString("least_timestamp");
				System.out.println("StartTime: "+ExecutionStart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		try {
			rs6 = stmt6.executeQuery(EndedTime);
			while (rs6.next()) {
				ExecutionEnd = rs6.getString("highest_timestamp");
				System.out.println("EndTime: "+ExecutionEnd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		//Get Duration
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
		        // Parse the input dates
		        LocalDateTime startDate = LocalDateTime.parse(ExecutionStart, dateFormatter);
		        LocalDateTime endDate = LocalDateTime.parse(ExecutionEnd, dateFormatter);

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
		
		
		
		
		
		
		
		//// Excel updates
		File Summary = new File(Summaryfilepath);
		String sql = "select * from Summary;";

		// String AppNameQuery = "SELECT Distinct(ApplicationName) FROM Summary;";
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

		String BrowserDetails = "SELECT * from BrowserDetails";

				
		Statement stmt = con.createStatement();
		Statement stmt2 = con.createStatement();
		Statement stmt3 = con.createStatement();
		Statement stmt4 = con.createStatement();
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;

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

		workbook.setSheetOrder("Application Summary", 1);
		workbook.setSheetOrder("Detailed Report", 2);

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

		try {
			rs4 = stmt4.executeQuery(BrowserDetails);
			System.out.println("Result set: " + rs4);
		} catch (Exception e) {

		}
		try {
			OSname = rs4.getString("OSname");
			browser = rs4.getString("BrowserName");
			browserversion = rs4.getString("BrowserVersion");
		} catch (Exception e) {
			// TODO: handle exception
		}

		// ProjectName
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
		cell1.setCellValue(OSname);
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
		cell4.setCellValue(ExecutionStart);
		cell4.setCellStyle(setStyle(workbook));

		Cell cell5 = Sheet2.getRow(6).createCell(1);
		if (cell5 != null && cell5.getCellType() != CellType.BLANK) {
			cell5.setBlank();
		}
		cell5.setCellValue(ExecutionEnd);
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
		File ExcelReport = new File(workingDir + "\\ExcelOutput\\Integration" + timeStamp + ".xlsx");

		XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

		FileOutputStream fos = new FileOutputStream(Summary);
		workbook.write(fos);
		workbook.close();
		fos.close();
		con.close();
		FileUtils.copyFile(Summary, ExcelReport);
	}

	public static CellStyle setStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		return cellStyle;

	}

	public static void countFilesWithStartName(String folderPath, String startName) throws IOException {
		Path dir = Paths.get(folderPath);
		int count = 0;

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path entry : stream) {
				if (Files.isRegularFile(entry) && entry.getFileName().toString().startsWith(startName)) {

				}
			}
		}

	}

	public static void mergeDB() throws Exception {

		// Master database name
		String masterDatabaseName = "master.db";
		String url = "jdbc:sqlite:" + masterDatabaseName;

		String sql1 = "CREATE TABLE Summary (\n" + " TestID INTEGER,\n" + "  TestCaseID text,\n "
				+ " ApplicationName text,\n" + " ScenarioName text,\n" + " Status text,\n" + " TIMESTAMP text,\n "
				+ "TestData text, \n" +"FailureReason text\n" +"timestamp DATETIME DEFAULT CURRENT_TIMESTAMP\n);";
		String sql4 = "CREATE TABLE BrowserDetails (\n" + " TestID INTEGER,\n" + "  OSname text,\n "
				+ " BrowserName text,\n" + " BrowserVersion text \n);";

		// connect();
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection(url);
		Statement stmt = con.createStatement();

		try {
			stmt.execute("DROP TABLE Summary;");
			stmt.execute(sql1);

		} catch (Exception e) {
			System.out.println("table not already exist");
			// stmt.execute("DROP TABLE Summary;");
			stmt.execute(sql1);

		}
		try {
			stmt.execute("DROP TABLE BrowserDetails;");
			stmt.execute(sql4);

		} catch (Exception e) {
			System.out.println("table not already exist");
			// stmt.execute("DROP TABLE Summary;");
			stmt.execute(sql4);

		}

		try {
			// Connect to the master database
			Connection masterConnection = DriverManager.getConnection("jdbc:sqlite:" + masterDatabaseName);

			// Get a list of all files in the directory
			File dir = new File(DBpath);
			File[] databaseFiles = dir.listFiles();

			// Iterate through the database files
			for (File databaseFile : databaseFiles) {
				// Check if the file name starts with the specified prefix
				if (databaseFile.getName().startsWith(fileName)) {
					// Attach the database to the master database
					String attachSQL = "ATTACH DATABASE '" + databaseFile.getAbsolutePath() + "' AS attachedDB;";
					Statement attachStatement = masterConnection.createStatement();
					attachStatement.execute(attachSQL);
					attachStatement.close();

					// Copy data from the attached database to the master database
					String copySummary = "INSERT INTO main.Summary SELECT * FROM attachedDB.Summary;";
					String copyBrowser = "INSERT INTO main.BrowserDetails SELECT * FROM attachedDB.BrowserDetails;";
					Statement copyStatement = masterConnection.createStatement();
					copyStatement.execute(copySummary);
					copyStatement.execute(copyBrowser);
					copyStatement.close();

					// Detach the attached database
					String detachSQL = "DETACH DATABASE attachedDB;";
					Statement detachStatement = masterConnection.createStatement();
					detachStatement.execute(detachSQL);
					detachStatement.close();
				}
			}

			// Close the master database connection
			masterConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
