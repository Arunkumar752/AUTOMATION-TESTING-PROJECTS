package arunkmar.TestCompo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import aunkumar.pageobject.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTestCommon {
	public WebDriver Driver;
	protected LoginPage li;
	XSSFCellStyle style;
	DataFormatter formatter = new DataFormatter();

	public WebDriver initializeDriver() throws IOException

	{
		// properties class

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\arunkumar\\resources\\GlobalData.properties");
		prop.load(fis);

		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");
		// prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			//System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			if (browserName.contains("headless")) {
				options.addArguments("headless");
			}
			Driver = new ChromeDriver(options);
			Driver.manage().window().setSize(new Dimension(1440, 900));// full screen

		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.Driver", "/Users/rahulshetty//documents//geckodriver");
			Driver = new FirefoxDriver();
			// Firefox
		} else if (browserName.equalsIgnoreCase("edge")) {
			// Edge
			System.setProperty("webdriver.edge.Driver", "edge.exe");
			Driver = new EdgeDriver();
		}
		

		Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		Driver.manage().window().maximize();
	
        

		return Driver;
	}
   

	@BeforeMethod(alwaysRun = true)
	public LoginPage mm() throws IOException {
		System.out.println("Runned befpre");
		Driver = initializeDriver();
		li = new LoginPage(Driver);
		li.goTo();
		return li;

	}
	@AfterMethod
	public void close_driver() {
		Driver.close();
	}

	public String ss(String testCaseName, WebDriver Driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) Driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}

	public List<HashMap<String, String>> getJsonDataToMaps(String filePath) throws IOException {
		// read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		// String to HashMap- Jackson Databind

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;

		// {map, map}

	}

	public void jdbc() throws SQLException {
		String host = "localhost";

		String port = "3306";

		Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/ss", "root",
				"Arukdon1234$");

		Statement s = con.createStatement();

		ResultSet rs = s.executeQuery("select * from empl;");
		

		while (rs.next())

		{
			System.out.println(rs.getString("nam"));

		}
	}
	@DataProvider
	public Object[][] exceldatas() throws IOException {
		FileInputStream fis = new FileInputStream("D:\\testfile.xlsx"); // Excel sheet file location get mentioned here
		XSSFWorkbook workbook = new XSSFWorkbook(fis); // get my workbook
		XSSFSheet worksheet = workbook.getSheetAt(0);// get my sheet from workbook
		XSSFRow Row = worksheet.getRow(0); // get my Row which start from 0

		int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows
		int ColNum = Row.getLastCellNum(); // get last ColNum
		

		Object Data[][] = new Object[RowNum - 1][ColNum]; // pass my count data in array

		for (int i = 0; i < RowNum - 1; i++) // Loop work for Rows
		{
			Row = worksheet.getRow(i + 1);

			for (int j = 0; j < ColNum; j++) // Loop work for colNum
			{
				XSSFCell cell = Row.getCell(j);  
				Data[i][j] = formatter.formatCellValue(cell);
				System.out.println(Data[0][0]);
				System.out.println(Data[0][1]);
                System.out.println(i);
                System.out.println(j);
                System.out.println("_________");
			}
		}
		return Data;
	}

	@DataProvider
	public Object[][] exceldata() throws IOException {
		int k;
		int l;
		FileInputStream fis = new FileInputStream("C:\\Users\\arunkumar.c\\Desktop\\m.xlsx"); // Excel sheet file location get mentioned here
		XSSFWorkbook workbook = new XSSFWorkbook(fis); // get my workbook
		XSSFSheet worksheet = workbook.getSheetAt(0);// get my sheet from workbook
		XSSFRow Row = worksheet.getRow(0); // get my Row which start from 0

		//int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows
		//int ColNum = Row.getLastCellNum(); // get last ColNum

		Object Data[][] = new Object[5][2]; // pass my count data in array

		for (int i = 2; i < 7; i++) // Loop work for Rows
		{
			Row = worksheet.getRow(i);
			k = i-2;
			for (int j = 6; j < 8; j++) // Loop work for colNum
			{   
				XSSFCell cell = Row.getCell(j);
				l =j-6;
				Data[k][l] = formatter.formatCellValue(cell);
				System.out.println(Data[k][l]);
			}
			k=0;
			l=0;
		}
		return Data;
	}
	
    @DataProvider
	public Object[][] getDataa() throws IOException {
	    
        FileInputStream fis = new FileInputStream(new File("C:\\Users\\arunkumar.c\\Desktop\\m.xlsx")); 
        // Excel sheet file location get mentioned here       
        XSSFWorkbook workbook = new XSSFWorkbook(fis); // get my workbook       
        XSSFSheet worksheet = workbook.getSheetAt(0);// get my sheet from workbook      
        XSSFRow Row = worksheet.getRow(0); // get my Row which start from 0        //
        int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows        //
       // int ColNum = Row.getLastCellNum(); // get last ColNum  
        int totalRowCount = 0;
        int row = 2;
        int maxRow = 7;
        int dataRow;
        int c;
        int o=0;
        int dataColumn;
        for(int p = 2;p<RowNum;p++) {
        	 Row = worksheet.getRow(p);
        	if (Row.getCell(0).getStringCellValue().contains("Y")) {
        		totalRowCount++;
        	}
        }
        Object Data[][] = new Object[totalRowCount][2];
      
		while(row < maxRow) {
            Row = worksheet.getRow(row);
            
            dataRow = row-2;
             c = 0;
            System.out.println(Row.getCell(c).getStringCellValue());
            if (Row.getCell(c).getStringCellValue().contains("YES")) {
            	dataRow =row-2-o;
            	row = row+1;
            }
            else {
            	//o++;
            	o = (row-2);
            	row=row+1;
//            	if(worksheet.getRow(2).getCell(0).getStringCellValue()=="N") {
//            		o=1;
//            	}
            //	o=0;
            	continue;	
            }
            for (int col = 6; col < 8; col++) {  
                XSSFCell cell = Row.getCell(col);
                dataColumn =col-6;
                Data[dataRow][dataColumn] = formatter.formatCellValue(cell);
               // System.out.println(dataRow);
                System.out.println(Data[0][0] + "this is 0 , 0");
                System.out.println(Data[0][1] + "this is 0 , 1");
               // System.out.println(dataColumn);
               // System.out.println(Data[dataRow][dataColumn]);
            }
            dataRow=0;
            dataColumn=0;
		}
		
        return Data;
        }
   
	public void setData(String username,String password,String Status ) throws IOException {
		 FileInputStream file = new FileInputStream(new File("C:\\Users\\arunkumar.c\\Desktop\\m.xlsx"));
         // Create Workbook instance holding reference to .xlsx file            
		 XSSFWorkbook workbook = new XSSFWorkbook(file);
         // Get first/desired sheet from the workbook            
		 XSSFSheet sheet = workbook.getSheetAt(0);
         // count number of active tows           
		 int totalRow = sheet.getPhysicalNumberOfRows() ;
         XSSFCellStyle pass=workbook.createCellStyle();
         pass.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
         pass.setFillPattern(FillPatternType.FINE_DOTS);
         XSSFCellStyle fail=workbook.createCellStyle();
         fail.setFillBackgroundColor(IndexedColors.RED.getIndex());
         fail.setFillPattern(FillPatternType.FINE_DOTS);
         // count number of active columns in row           
         if (Status.equals("PASS")) {
             style =pass;
         }
         else {
             style=fail;
         }
         for (int i = 2; i < 7; i++) {
             XSSFRow r = sheet.getRow(i);
             String ce = r.getCell(6).getStringCellValue();
             String cl = r.getCell(7).getStringCellValue();
             if (ce.equalsIgnoreCase(username)&& cl.equalsIgnoreCase(password)) {
                 r.createCell(9).setCellValue(Status);
                 r.getCell(9).setCellStyle(style);
                 file.close();
                 FileOutputStream outFile = new FileOutputStream(new File("C:\\Users\\arunkumar.c\\Desktop\\m.xlsx"));
                 workbook.write(outFile);
                 outFile.close();
                 break;
             }
             
         }
         
     }
}

