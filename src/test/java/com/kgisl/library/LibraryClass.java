package com.kgisl.library;

import java.io.File;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.kgisl.base.BaseUtil;
import com.kgisl.base.FrameworkConfig;
import com.kgisl.base.Hooks;


public class LibraryClass extends BaseUtil {
	private Hooks base;
	public Statement st;
	private static final String CHAR_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String sourcePath = System.getProperty("user.dir");
	public String downloadPath = System.getProperty("user.home");
	private String workingDir = System.getProperty("user.dir");
	public String pdfpath = workingDir + "\\test output\\PdfReport\\ExtentPdf.pdf";
	public String Excelpath = workingDir + "\\OutputData\\Summary.xlsx";
	FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);

	public LibraryClass(Hooks base) {
		this.base = base;
	}

	public void waitForInVisibilityOfElement(By invisbleElement) {
		try {
			WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(base.globalWait));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(invisbleElement));
		} catch (Exception ignored) {

		}
	}

	public void waitForInVisibilityOfElement(By invisbleElement, int secondsToWait) {
		WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(secondsToWait));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(invisbleElement));
	}

	public void waitForClickableElement(By clickableElement) {
		WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(base.globalWait));
		wait.until(ExpectedConditions.elementToBeClickable(clickableElement));
	}

	public void waitForClickableElement(By clickableElement, int secondsToWait) throws Throwable {
		WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(secondsToWait));
		wait.until(ExpectedConditions.elementToBeClickable(clickableElement));
	}

	public void waitForClickableElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(base.globalWait));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitForClickableElement(WebElement element, int secondsToWait) {
		WebDriverWait wait = new WebDriverWait(base.getDriver(),  Duration.ofSeconds(secondsToWait));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
		
	public void waitForVisibilityOfElement(By visbleElement) {
		WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(base.globalWait));
		wait.until(ExpectedConditions.visibilityOfElementLocated(visbleElement));
	}

	public void waitForVisibilityOfElement(WebElement Element) {
		WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(base.globalWait));
		wait.until(ExpectedConditions.visibilityOf(Element));
	}

	public void waitForVisibilityOfElement(By visbleElement, int secondsToWait) {
		WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(secondsToWait));
		wait.until(ExpectedConditions.visibilityOfElementLocated(visbleElement));
	}

	// Mobile Methods

	public void mobileWaitForVisibilityOfElement(By visbleElement) throws Throwable {
		WebDriverWait wait = new WebDriverWait(base.getmDirver(), Duration.ofSeconds(base.globalWait));
		wait.until(ExpectedConditions.visibilityOfElementLocated(visbleElement));
	}

	public void mobileWaitForClickableElement(By clickableElement) throws Throwable {
		WebDriverWait wait = new WebDriverWait(base.getmDirver(), Duration.ofSeconds(base.globalWait));
		wait.until(ExpectedConditions.elementToBeClickable(clickableElement));
	}

	public void mobileWaitForInVisibilityOfElement(By invisbleElement) throws Throwable {
		/*
		 * Thread.sleep(2000); String getTextofPopup =
		 * base.mGetDriver().findElement(invisbleElement).getText();
		 * System.out.println("Popup Message:********"+getTextofPopup);
		 */
		WebDriverWait wait = new WebDriverWait(base.getmDirver(), Duration.ofSeconds(base.globalWait));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(invisbleElement));
	}
	// ----------------------------------------------------------------------------------
	// Common Methods
	// ----------------------------------------------------------------------------------

	public void deleteAllFiles(String deletedFolderName) {
		String home = downloadPath;
		String projectPath = sourcePath;
		File sourceFile = new File(sourcePath + "/" + deletedFolderName + "/");
		String name = sourceFile.getName();
		boolean fileExists = sourceFile.exists();
		if (fileExists == true) {
			File[] listFiles = sourceFile.listFiles();
			for (File file : listFiles) {
				file.delete();
			}
		}
	}

	public void deleteFileFromDownlaods(String searchDoc) {
		String home = downloadPath;
		String fileName = searchDoc;
		// name of source file
		File sourceFile = new File(home + "/Downloads/" + fileName);
		String name = sourceFile.getName();
		boolean fileExists = sourceFile.exists();
		if (fileExists == true) {
			sourceFile.delete();
		}
	}

	public double convertToDoubleAndRoundOffTwoDecimal(String toConvert) throws ParseException {
		if (toConvert == null) {
			toConvert = "0.00";
		}
		NumberFormat nf1 = NumberFormat.getInstance();
		DecimalFormat df = new DecimalFormat("0.00");
		Number numberValue = nf1.parse(toConvert);
		Double doubleValue = numberValue.doubleValue();
		double roundOff = (double) Math.round(doubleValue * 100.00) / 100.00;

		String formate = df.format(roundOff);
		// double finalValue = (Double)df.parse(formate) ;
		double finalValue = Double.parseDouble(formate);

		return finalValue;
	}

	public int covertStringToInteger(String convertionString) {
		int convertIntValue = Integer.parseInt(convertionString);
		return convertIntValue;
	}

	public String convertIntegerToString(int convertionValue) {
		String convertStringtValue = String.valueOf(convertionValue);
		return convertStringtValue;
	}

	public String convertdoubleToString(double convertionValue) {
		String convertStringtValue = String.valueOf(convertionValue);
		return convertStringtValue;
	}

	public double setRoundingMode(double digit, String Type) {
		double d = 0;
		DecimalFormat df = new DecimalFormat("#.##");
		switch (Type) {
		case "UP":
			df.setRoundingMode(RoundingMode.CEILING);
		case "DOWN":
			df.setRoundingMode(RoundingMode.DOWN);
		case "MIED":
			df.setRoundingMode(RoundingMode.CEILING);
		default:
			Assert.fail("Given Option not avialble.");
		}
//	df.setRoundingMode(RoundingMode.CEILING);
		for (Number n : Arrays.asList(digit)) {
			d = Double.parseDouble(df.format(n.doubleValue()));
		}
		return d;
	}

	// DB Activity
	public String DBVerification(String sqlQuery, String ColumnToGet, String expectedValue) throws SQLException {
		expectedValue = null;
		ResultSet resultSet = null;
		resultSet = st.executeQuery(sqlQuery);

		while (resultSet.next()) {
			expectedValue = resultSet.getString(ColumnToGet);
		}
		return expectedValue;
	}

	public void executeUpdateQuery(String sqlquery) {
		try {
			int ret = st.executeUpdate(sqlquery);
			if (ret > 0)
				System.out.println("Rows impacted :" + ret);
			else
				System.out.println("Not Updated");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String generateRandomNumbers(int count) {
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(count);
		String number = String.format("%06d", num);
		return number;
	}

	// ----------------------------------------------------------------------------------
	// ----Date Functions

	public String todaysDate() {
		Date todays = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String date = formatter.format(todays);
		String todaysDate = date;
		return todaysDate;
	}

	public String todaysDate(String Format) {
		Date todays = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(Format);
		String date = formatter.format(todays);
		String todaysDate = date;
		return todaysDate;
	}

	public String getDateByDaysCount(int daysIncreaseCount) throws ParseException {
		String sourceDate = Datewithmonth(); // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to
													// calendar
		calendar.add(Calendar.DATE, daysIncreaseCount); // number of days to add
		String destDate = sdf.format(calendar.getTime());
		return destDate;

	}

	public String IncreaseByMonth(int monthIncreaseCount) throws ParseException {
		String sourceDate = Datewithmonth(); // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to
													// calendar
		calendar.add(Calendar.MONTH, monthIncreaseCount); // number of days to add
		String destDate = sdf.format(calendar.getTime());
		return destDate;

	}

	public String IncreaseByYear(int yearIncreaseCount) throws ParseException {
		String sourceDate = Datewithmonth(); // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to
													// calendar
		calendar.add(Calendar.YEAR, yearIncreaseCount); // number of days to add
		String destDate = sdf.format(calendar.getTime());
		return destDate;

	}

	public boolean calculateleapyear() {
		boolean chkleapyear;
		Calendar cal = Calendar.getInstance();
		// Date today = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		cal.add(Calendar.YEAR, 1);
		String nextyear = sdf.format(cal.getTime());
		if ((Integer.parseInt(nextyear) % 4 == 0 ? true : false)) {
			chkleapyear = true;
		} else {
			chkleapyear = false;
		}
		return chkleapyear;
	}

	public static long daysBetween(Date one, Date two) {
		long difference = (one.getTime() - two.getTime()) / 86400000;
		return Math.abs(difference + 1);
	}

	public String incrementADateByOneday(int daysIncreaseCount) throws ParseException {
		String sourceDate = todaysDate(); // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to // calendar
		calendar.add(Calendar.DATE, daysIncreaseCount); // number of days to add
		String destDate = sdf.format(calendar.getTime());
		return destDate;
	}
	
	public String incrementADateByOneday(int daysIncreaseCount, String givenDate,String Format) throws ParseException {
		String sourceDate = givenDate; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat(Format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to // calendar
		calendar.add(Calendar.DATE, daysIncreaseCount); // number of days to add
		String destDate = sdf.format(calendar.getTime());
		return destDate;
	}
	

	public String incrementADateByOneday(int daysIncreaseCount, String givenDate) throws ParseException {
		String sourceDate = givenDate; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to // calendar
		calendar.add(Calendar.DATE, daysIncreaseCount); // number of days to add
		String destDate = sdf.format(calendar.getTime());
		return destDate;
	}
	
	public String incrementByDay(int daysIncreaseCount, String givenDate) throws ParseException {
		String sourceDate = givenDate; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to // calendar
		calendar.add(Calendar.DATE, daysIncreaseCount); // number of days to add
		String destDate = sdf.format(calendar.getTime());
		return destDate;
	}

	public String changedateFormate(String data, String actualformate, String expectedformate)
			throws ParseException {
		DateFormat outputFormat = new SimpleDateFormat(expectedformate, Locale.US);
		DateFormat inputFormat = new SimpleDateFormat(actualformate, Locale.US);
		Date date = inputFormat.parse(data);
		String outputText = outputFormat.format(date);
		return outputText;
	}

	public String todaysDateWithTime() {
		Date todays = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = formatter.format(todays);
		String todaysDate = date;
		return todaysDate;

	}

	public String oldyear(int decreaseCount) {
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, decreaseCount);
		String oldDate = dateFormat.format(cal.getTime());
		return oldDate;
	}

	public String upcomingmonths(int increaseCount) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, increaseCount);
		String nextmonth = dateFormat.format(cal.getTime());
		return nextmonth;
	}

	// ----------------------------------------------------------------------------------

	private int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

	public String generateRandomString(int RANDOM_STRING_LENGTH) {
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	public int RandomNumber(int start, int max) {
		if (max < start) {
			Assert.fail("Given value start value is greater than end value");
		}
		int b = (int) (Math.random() * (max - start + 1) + start);
		return b;
	}

	public void removeattribute(By element) throws Exception {
		((JavascriptExecutor) base.getDriver()).executeScript("arguments[0].removeAttribute('readonly','readonly')",
				base.getDriver().findElement(element));
	}

	public void waitForAlert(int secondsToWait) throws Exception {
		WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(secondsToWait));
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public String String_to_Date(String InputDate) throws Throwable {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = formatter.parse(InputDate);
		String Outputdate = dateFormat.format(date);
		return Outputdate;
	}

	public void sendmail(String Tomail, String Subject, String Text) throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", config.EmailServer());
		props.put("mail.smtp.socketFactory.port", config.MailPort());
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", config.MailPort());
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(config.EmailUser(), decodestr(config.EmailPassword()));
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(config.Frommail()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Tomail));
			message.setSubject(Subject);
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(Text);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("=====Email Sent=====");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendmailWithAttachment(String ToMail, String Subject, String Text) throws Exception {
//			Thread.sleep(20000);
		Properties props = new Properties();
		props.put("mail.smtp.host", config.EmailServer());
		props.put("mail.smtp.socketFactory.port", config.MailPort());
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", config.MailPort());
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(config.EmailUser(), decodestr(config.EmailPassword()));
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(config.Frommail()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ToMail));
			message.setSubject(Subject);
			BodyPart messageBodyPart1 = new MimeBodyPart();
			// Set the body of email
			messageBodyPart1.setText(Text);

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			MimeBodyPart messageBodyPart3 = new MimeBodyPart();

//	            String paths=workingDir+"\\Reports";
//	            String Folderpath ="D:/Testing/Mani/workspace/UiDemo/Reports";
//	            File directoryPath = new File(paths);
//	  	      String contents[] = directoryPath.list();	      
//	  	      int j = contents.length-1;
//	  	      System.out.println(contents[j]);
//	  	    String path =contents[j];
//	  	    String location=paths+"\\"+path+"\\test output\\PdfReport\\ExtentPdf.pdf";
//	  	    System.out.println(location);

			DataSource source = new FileDataSource(pdfpath);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(pdfpath);

			DataSource source1 = new FileDataSource(Excelpath);
			messageBodyPart3.setDataHandler(new DataHandler(source1));
			messageBodyPart3.setFileName(Excelpath);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			multipart.addBodyPart(messageBodyPart3);
			message.setContent(multipart);
			// finally send the email
			Transport.send(message);
			System.out.println("=====Email Sent=====");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public String decodestr(String encpswd) {
		byte[] decoded = Base64.decodeBase64(encpswd);
		return new String(decoded);

	}

	public String Datewithmonth() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now);
		return date;
	}

	// frame load explicit condition
	public void switchFrame(String FrameName) {

		WebDriverWait w = new WebDriverWait(base.getDriver(), Duration.ofSeconds(base.globalWait));
		w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameName));
	}
	
	public void switchFrameElement(By element) {
		base.getDriver().switchTo().frame(base.getDriver().findElement(element));
	}

	public boolean switch_to_required_window(String url) throws Exception {
		Thread.sleep(2000);
		String parentWindow = base.getDriver().getWindowHandle();
		Set<String> windows = base.getDriver().getWindowHandles();
		// String url="Agent/commonutils/searchmakemodel.do";
		for (String w : windows) {
			String currenturl = base.getDriver().switchTo().window(w).getCurrentUrl();
			if (currenturl.contains(url)) {
				return true;
			}
		}
		return false;
	}

	public void removeattribute(By element, String Attr, String Val) {

		((JavascriptExecutor) base.getDriver()).executeScript(

				"arguments[0].removeAttribute('" + Attr + "','" + Val + "')", base.getDriver().findElement(element));

	}
	
	public void addAttribute(By element, String Attr, String Val) {

		((JavascriptExecutor) base.getDriver()).executeScript(

				"arguments[0].setAttribute('" + Attr + "','" + Val + "')", base.getDriver().findElement(element));

	}

	public void listDrpValues(By Element) {
		Select s = new Select(base.getDriver().findElement(Element));
		List<WebElement> op = s.getOptions();
		int size = op.size();
		for (int i = 0; i < size; i++) {
			String options = op.get(i).getText();
			System.out.println(options);
		}
	}

	public String DateincrementDecrement(int daysIncreaseCount) throws ParseException {
		// String sourceDate = todaysDate(); // Start date
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to //
		// calendar
		calendar.add(Calendar.DATE, daysIncreaseCount); // number of days to add
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String destDate = sdf.format(calendar.getTime());
		return destDate;
	}

	public String DateincrementDecrement(int daysIncreaseCount, String sourceDate, String format)
			throws ParseException {
		// String sourceDate = todaysDate(); // Start date
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to // calendar
		calendar.add(Calendar.DATE, daysIncreaseCount); // number of days to add
		SimpleDateFormat sdformat = new SimpleDateFormat(format);
		String destDate = sdformat.format(calendar.getTime());
		return destDate;
	}

	public String DateincrementDecrement(int daysIncreaseCount, String format) throws ParseException {
		String sourceDate = todaysDate(format); // Start date
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to // calendar
		calendar.add(Calendar.DATE, daysIncreaseCount); // number of days to add
		SimpleDateFormat sdformat = new SimpleDateFormat(format);
		String destDate = sdformat.format(calendar.getTime());
		return destDate;
	}

	public String IncrementYear(int yearcount) throws ParseException {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, yearcount); // number of days to add
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String Year = sdf.format(calendar.getTime());
		return Year;
	}

	public String GetYear(String date) throws ParseException {
		String sourceDate = date; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to // calendar
		int year = calendar.get(Calendar.YEAR);
		String destDate = String.valueOf(year);
		return destDate;
	}

	public String generateRandomNumbers(int digit, int count) {
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(count);
		String number = String.format("%0" + digit + "d", num);
		return number;
	}

	public String changedateFormat(String date, String expectedformat) throws ParseException {
		String sourceDate = date; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat(expectedformat);
		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(sdf.parse(sourceDate)); // parsed date and setting to //
		String destDate = sdf.format(calendar.getTime());
		return destDate;
	}	
	public void waitForVisibilityOfURL( String URL) {
        WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(base.globalWait));
        wait.until(ExpectedConditions.urlContains(URL));
    }
	
	public String GetVehicleNumberBaNCS() {
		 String allowedLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        String allowedNumbers = "0123456789";

	        // Initialize a Random object
	        Random random = new Random();

	        // Generate random letters for the first two positions
	        StringBuilder firstTwoLetters = new StringBuilder();
	        for (int i = 0; i < 2; i++) {
	            int randomIndex = random.nextInt(allowedLetters.length());
	            char randomChar = allowedLetters.charAt(randomIndex);
	            firstTwoLetters.append(randomChar);
	        }

	        // Generate random numbers for the next two positions
	        StringBuilder nextTwoNumbers = new StringBuilder();
	        for (int i = 0; i < 2; i++) {
	            int randomIndex = random.nextInt(allowedNumbers.length());
	            char randomChar = allowedNumbers.charAt(randomIndex);
	            nextTwoNumbers.append(randomChar);
	        }

	        // Generate random letters for the next two positions
	        StringBuilder nextTwoLetters = new StringBuilder();
	        for (int i = 0; i < 2; i++) {
	            int randomIndex = random.nextInt(allowedLetters.length());
	            char randomChar = allowedLetters.charAt(randomIndex);
	            nextTwoLetters.append(randomChar);
	        }

	        // Generate random numbers for the final two positions
	        StringBuilder finalTwoNumbers = new StringBuilder();
	        for (int i = 0; i < 4; i++) {
	            int randomIndex = random.nextInt(allowedNumbers.length());
	            char randomChar = allowedNumbers.charAt(randomIndex);
	            finalTwoNumbers.append(randomChar);
	        }

	        // Combine all parts to form the vehicle number
//	        String vehicleNumber = firstTwoLetters.toString() + nextTwoNumbers.toString()
//	                + nextTwoLetters.toString() + finalTwoNumbers.toString();
	        String vehicleNumber = "TN" + nextTwoNumbers.toString()
            + nextTwoLetters.toString() + finalTwoNumbers.toString();

	        // Output the generated vehicle number
	        System.out.println("Random Vehicle Number: " + vehicleNumber);
			return vehicleNumber;
	}
	
	
}
