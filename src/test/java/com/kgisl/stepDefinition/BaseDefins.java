package com.kgisl.stepDefinition;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.kgisl.base.BaseUtil;
import com.kgisl.base.DB_Connection;
import com.kgisl.base.Data_Set;
import com.kgisl.base.ExcelUtility;
import com.kgisl.base.FrameworkConfig;
import com.kgisl.base.Hooks;
import com.kgisl.library.LibraryClass;
import com.kgisl.library.ReportOut;
//import com.kgisl.library.ReportUtility;
import com.kgisl.library.ReportUtility;

import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.Result;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;

public class BaseDefins extends BaseUtil {

	private ExcelUtility excelData;
	private DB_Connection db;
	private Data_Set report;
	private Hooks base;

	public String DBpath = System.getProperty("user.dir") + "\\TestData" + "\\sqlite\\";

	private Scenario myScenario;
	public static String StartTime;
	public int rownumber;
	private Connection con = null;
	public static FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
	public String fileName = config.DBname();
	public static String DBname = config.DBname();
	public static String Tomail = config.Tomail();
	public static String Subject = config.MailSubject();
	public static String Text = config.MailText();
	public static String Browsername;
	public static String Browserversion;
	public String ApplicationName = null;
	public Collection<String> TagName = null;

	public BaseDefins(Hooks base, DB_Connection db, Data_Set report, ExcelUtility excel) {
		this.base = base;
		this.db = db;
		this.report = report;
		this.excelData = excel;

	}

	@BeforeAll
	public static void connectDB() throws Exception {

		// System.out.println("Beforeall");
		DB_Connection db = new DB_Connection();
		StartTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new java.util.Date());
		db.createDB();
		db.createTable();

	}

	@Before
	public void launchApp(Scenario scenario) throws Exception {
		myScenario = scenario;
		myScenario.getId();

		TagName = myScenario.getSourceTagNames();

		System.out.println(myScenario.getSourceTagNames());
		System.out.println(myScenario.getName());

		if (TagName.contains("@BancsFinancial") == true) {
			ApplicationName = "Financial Endorsement";
		}
		if (TagName.contains("@BancsNonFinancial") == true) {
			ApplicationName = "Non_Financial Endorsement";
		}
		if (TagName.contains("@PolicyCancellation") == true) {
			ApplicationName = "Policy Cancellation";
		}
		if (TagName.contains("@BancsPolicyCreation") == true) {
			ApplicationName = "Policy Creation";
		}
		if (TagName.contains("@BancsRenewal") == true) {
			ApplicationName = "Policy Renewal";
		}
		if (TagName.contains("@BreakIn") == true) {
			ApplicationName = "Break-In";
		}
		if (TagName.contains("@CkycIntegration") == true) {
			ApplicationName = "BaNCS CKYC Integration";
		}
		if (TagName.contains("@CordysEndorsement") == true) {
			ApplicationName = "Endorsement Request Creation_Cordys";
		}
		if (TagName.contains("@CRM") == true) {
			ApplicationName = "CRM - Policy Details to SFDC";
		}

		if (TagName.contains("@ADP") == true) {
			ApplicationName = "ADP";
		}
		if (TagName.contains("@CSC") == true) {
			ApplicationName = "CSC";
		}
		if (TagName.contains("@V4Sync") == true) {
			ApplicationName = "V4";
		}
		if (TagName.contains("@CustomerClaims") == true) {
			ApplicationName = "CustomerClaims";
		}
		if (TagName.contains("@KioskEpolicy") == true) {
			ApplicationName = "ePolicy & Kiosk Old";
		}
		if (TagName.contains("@InfinityRenewal") == true) {
			ApplicationName = "New Portal motor_Renewal";
		}
		if (TagName.contains("@NeoCorePolicyCreation") == true) {
			ApplicationName = "E-Policy Portal (Neocore)";
		}

		if (TagName.contains("@Phoenix") == true) {
			ApplicationName = "Phoenix";
		}

		if (TagName.contains("@PolicySearchPortal") == true) {
			ApplicationName = "Policy Search Portal";
		}

		if (TagName.contains("@ProposalPrintPortal") == true) {
			ApplicationName = "Proposal Print Portal";
		}

		if (TagName.contains("@RCPportal") == true) {
			ApplicationName = "Remote Certificate Printing";
		}

		if (TagName.contains("@V4Master") == true) {
			ApplicationName = "V4 Master";
		}

		if (TagName.contains("@ZingAgentPro") == true) {
			ApplicationName = "Zing Agent Pro";
		}

		System.out.println("This is before Scenario: " + scenario.getName().toString());
		if (config.Mode().equalsIgnoreCase("Web")) {
			base.launchBrowser();
			Browsername = base.browserName;
			Browserversion = base.Browserversion;
		} else {
			base.LaunchMobileApp();
		}
	}

	@After
	public void tearDown(Scenario scenario) throws Exception {
		System.out.println("TestAfter");

		System.out.println(ApplicationName);

		Result failResult = null ;
		String FailureMessage ="";
		
		if (scenario.isFailed()) {
						 
			try {
			        // Get the delegate from the scenario
			        Field delegate = scenario.getClass().getDeclaredField("delegate");
			        delegate.setAccessible(true);
			        TestCaseState testCaseState = (TestCaseState) delegate.get(scenario);

			        // Get the test case results from the delegate
			        Field stepResults = testCaseState.getClass().getDeclaredField("stepResults");
			        stepResults.setAccessible(true);
			        List<Result> results = (List<Result>) stepResults.get(testCaseState);

			        for(Result result : results) {
			            if(result.getStatus().name().equalsIgnoreCase("FAILED")) {
			            	failResult = result;
			            	break;
			            }
			        }
			    } catch (NoSuchFieldException | IllegalAccessException e) {
			        e.printStackTrace();
			    }
			System.out.println(failResult.getError());
			
			FailureMessage = failResult.getError().toString();
			String ExceptionMsg[] = FailureMessage.split(":");
			FailureMessage = ExceptionMsg[0];
			ExceptionMsg = FailureMessage.split("[.]");
			FailureMessage= ExceptionMsg[ExceptionMsg.length-1];
			
			try {
				System.out.println(scenario + " test is failed");
				takeScreenShot(" test is failed");
			} catch (Exception e) {
				try {
					System.out.println(scenario + " test is failed");
					System.out.println(">>>>>>>>>>>>>>>>>UnException Alert opened.<<<<<<<<<<<<<<<<<");
				} catch (Exception ee) {
				}
			}
		}

		System.out.println(scenario.getId());

		try {
			String url = "jdbc:sqlite:" + DBpath + fileName + ".db";
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(url);
			con.setAutoCommit(false);

			String Insert_Query = "INSERT INTO Summary (TestCaseID, ApplicationName, ScenarioName, Status,TIMESTAMP, TestData, FailureReason) VALUES(?,?,?,?,?,?,?)";
			PreparedStatement SummaryStatement = con.prepareStatement(Insert_Query);
			String Startdate = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new java.util.Date());
			// statement.setString(0,myScenario.getId());
			SummaryStatement.setString(1, report.getTestcaseID());
			if (report.getReportAppName() == null) {
				SummaryStatement.setString(2, ApplicationName);
			} else {
				SummaryStatement.setString(2, report.getReportAppName());
			}

			SummaryStatement.setString(3, scenario.getName());
			SummaryStatement.setString(4, String.valueOf(scenario.getStatus()));
			SummaryStatement.setString(5, Startdate);
			if (excelData.TestDataInMap != null) {
				SummaryStatement.setString(6, excelData.TestDataInMap.toString().replace("null", "N/A"));
			} else {
				SummaryStatement.setString(6, "N/A");
			}
			if (failResult != null) {
				SummaryStatement.setString(7, FailureMessage);
			} else {
				SummaryStatement.setString(7, "N/A");
			}
			SummaryStatement.addBatch();

			SummaryStatement.executeBatch();
			con.setAutoCommit(true);

		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}

		try {
			String url = "jdbc:sqlite:" + DBpath + fileName + ".db";
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(url);

			con.setAutoCommit(false);
			String Insert_Query = "INSERT INTO Master (TestCaseID, ApplicationName, ScenarioName, Status, TIMESTAMP, TestData, FailureReason) VALUES(?,?,?,?,?,?,?)";
			PreparedStatement MasterStatement = con.prepareStatement(Insert_Query);
			String Startdate = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new java.util.Date());
			// statement.setString(0,myScenario.getId());
			MasterStatement.setString(1, report.getTestcaseID());
			if (report.getReportAppName() == null) {
				MasterStatement.setString(2, ApplicationName);
			} else {
				MasterStatement.setString(2, report.getReportAppName());
			}
			MasterStatement.setString(3, scenario.getName());
			MasterStatement.setString(4, String.valueOf(scenario.getStatus()));
			MasterStatement.setString(5, Startdate);
			if (excelData.TestDataInMap != null) {
				MasterStatement.setString(6, excelData.TestDataInMap.toString().replace("null", "N/A"));
			} else {
				MasterStatement.setString(6, "N/A");
			}
			if (failResult != null) {
				MasterStatement.setString(7, FailureMessage);
			} else {
				MasterStatement.setString(7, "N/A");
			}
			MasterStatement.addBatch();

			MasterStatement.executeBatch();
			con.setAutoCommit(true);
			// con.commit();
			Thread.sleep(1000);

		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}

		report.ResetVariables();
		base.getDriver().close();
		base.getDriver().quit();

	}

	public void takeScreenShot(String ssName) throws Exception {
		try {
			byte[] screenshot = ((TakesScreenshot) base.getDriver()).getScreenshotAs(OutputType.BYTES);
			myScenario.attach(screenshot, "image/png", ssName); // Stick it in the report
		} catch (WebDriverException somePlatformsDontSupportScreenshots) {
		} catch (ClassCastException cce) {
			cce.printStackTrace();
		}
	}

	public void takeScreenShotOfMobile(String ssName) throws Exception {
		try {
			byte[] screenshot = ((TakesScreenshot) base.getmDirver()).getScreenshotAs(OutputType.BYTES);
			myScenario.attach(screenshot, "image/png", ssName); // Stick it in the report
		} catch (WebDriverException somePlatformsDontSupportScreenshots) {
		} catch (ClassCastException cce) {
			cce.printStackTrace();
		}
	}

	public Integer aRandomOpenPortOnAllLocalInterfaces() {
		try (ServerSocket socket = new ServerSocket(0);) {
			return socket.getLocalPort();

		} catch (IOException e) {
			throw new RuntimeException("no open ports found for bootstrap");
		}
	}

	public void InsertBancsCKYCQuery(String CustomerName, String DOB, String DocumentType, String DocumentNumber,
			String Status) throws InterruptedException {

		try {
			String url = "jdbc:sqlite:" + DBpath + fileName + ".db";
			con = DriverManager.getConnection(url);

			con.setAutoCommit(false);
			String Insert_Query = "INSERT INTO BancsCKYCstatus (TestCaseID,CustomerName,DateOfIncorporation,DocumentType,DocumentNumber,status) VALUES(?,?,?,?,?,?);";
			PreparedStatement AgentStatement = con.prepareStatement(Insert_Query);
			AgentStatement.setString(1, report.getTestcaseID());
			AgentStatement.setString(2, CustomerName);
			AgentStatement.setString(3, DOB);
			AgentStatement.setString(4, DocumentType);
			AgentStatement.setString(5, DocumentNumber);
			AgentStatement.setString(6, Status);
			AgentStatement.addBatch();
			AgentStatement.executeBatch();
			con.setAutoCommit(true);
			// con.commit();
			Thread.sleep(1000);

		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}
	}

	public void InsertQuery(String applicationName, String policyNo) throws InterruptedException {

		try {
			String url = "jdbc:sqlite:" + DBpath + fileName + ".db";
			con = DriverManager.getConnection(url);

			con.setAutoCommit(false);
			String Insert_Query = "INSERT INTO policyNo (TestCaseID,applicationName,policyNo) VALUES(?,?,?);";
			PreparedStatement PolicyStatement = con.prepareStatement(Insert_Query);
			PolicyStatement.setString(1, report.getTestcaseID());
			PolicyStatement.setString(2, applicationName);
			PolicyStatement.setString(3, policyNo);
			PolicyStatement.addBatch();
			PolicyStatement.executeBatch();
			con.setAutoCommit(true);
			// con.commit();
			Thread.sleep(1000);

		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}
	}

	public void InsertBancsPolicyNumber(String applicationName, String PolicyNumber) throws InterruptedException {

		try {
			String url = "jdbc:sqlite:" + DBpath + fileName + ".db";
			con = DriverManager.getConnection(url);

			con.setAutoCommit(false);
			String Insert_Query = "INSERT INTO BancsPolicyNumber(TestCaseID,applicationName,PolicyNumber) VALUES(?,?,?);";
			PreparedStatement AgentStatement = con.prepareStatement(Insert_Query);
			AgentStatement.setString(1, report.getTestcaseID());
			AgentStatement.setString(2, applicationName);
			AgentStatement.setString(3, PolicyNumber);
			AgentStatement.addBatch();
			AgentStatement.executeBatch();
			con.setAutoCommit(true);
			// con.commit();
			Thread.sleep(1000);

		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}
	}

	@AfterAll
	public static void generateReport() throws Throwable {
		System.out.println("---------AfterAll----------------");
		ExcelUtility excel = new ExcelUtility();
		Hooks base = new Hooks();
		ReportUtility report = new ReportUtility();
		LibraryClass library = new LibraryClass(base);
		// sreport.generateJVMReport();
		excel.generateReports(StartTime, Browsername, Browserversion);
		excel.generatePolicyNoReports();
		excel.generateBancsPolNumber();
		excel.generatebancsCKYCReports();
		// excel.generatePDFreport();
		try {
			String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\TestData" + "\\sqlite\\" + DBname + ".db";
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(url);
			con.setAutoCommit(false);

			String Insert_Query = "INSERT INTO BrowserDetails (OSname, BrowserName, BrowserVersion) VALUES(?,?,?)";
			PreparedStatement SummaryStatement = con.prepareStatement(Insert_Query);
			String Startdate = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new java.util.Date());
			// statement.setString(0,myScenario.getId());

			SummaryStatement.setString(1, System.getProperty("os.name"));
			SummaryStatement.setString(2, Browsername);
			SummaryStatement.setString(3, Browserversion);

			SummaryStatement.addBatch();

			SummaryStatement.executeBatch();
			con.setAutoCommit(true);

		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}
	}

}
