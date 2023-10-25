package com.kgisl.controls;

import static org.testng.Assert.assertTrue;

import java.awt.Window;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.kgisl.base.BaseUtil;
import com.kgisl.base.Hooks;
import com.kgisl.library.LibraryClass;
import com.kgisl.library.ReportOut;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class WebControl extends BaseUtil {
	private Hooks base;
	private LibraryClass library;
	private ReportOut report;

	public WebControl(Hooks base, LibraryClass library, ReportOut report) {
		this.base = base;
		this.library = library;
		this.report = report;
	}

	// Enter Text methods
	public void EnterValueon(By element, String Value) {
		// clearText(element);
		base.getDriver().findElement(element).sendKeys(Value);
	}

	public void EnterValueonTab(By element, String Value) {
		base.getDriver().findElement(element).sendKeys(Value);
		base.getDriver().findElement(element).sendKeys(Keys.TAB);
	}

	public void EnterValueon(WebElement element, String Value) throws Throwable {
		// clearText(element);
		element.sendKeys(Value);
	}

	// clear the value from Text Box
	public void clearText(By Element) {
		library.waitForVisibilityOfElement(Element);
		base.getDriver().findElement(Element).clear();
	}

	public void clearText(WebElement Element) {
		library.waitForVisibilityOfElement(Element);
		Element.clear();
	}

	public void clearTextJSE(By Element) {
		JavascriptExecutor js = (JavascriptExecutor) base.getDriver();
		js.executeScript("arguments[0].value = '';", base.getDriver().findElement(Element));
	}

	public void ActionclearText(By Element) {
		String Delete = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		base.getDriver().findElement(Element).sendKeys(Delete);
	}

	// Click keys Events methods
	public void EnterKeyon(By element, Keys keyValue) {
		base.getDriver().findElement(element).sendKeys(keyValue);
	}

	public void EnterKeyon(WebElement element, Keys keyValue) {
		element.sendKeys(keyValue);
	}

	// Get Text from Elements
	public String GetTexton(By Element) {
		String Txtval = base.getDriver().findElement(Element).getText();
		return Txtval;
	}

	public String GetTexton(WebElement element) {
		library.waitForVisibilityOfElement(element);
		String Txtval = element.getText();
		return Txtval;
	}

	public WebElement GetElments(By Element, int Indx) throws Throwable {
		library.waitForVisibilityOfElement(Element);
		WebElement EleText = base.getDriver().findElements(Element).get(Indx);
		return EleText;
	}

	// Get the Attribute Value from Elements
	public String GetAttributeValueon(By Element, String Attribute) {
		String AtrVal = null;
		try {
			AtrVal = base.getDriver().findElement(Element).getAttribute(Attribute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AtrVal;
	}

	public void DropdownValueLoading(By Element) {
		WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(base.globalWait));
		wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(Element, By.tagName("option")));
	}

	public void DropdownSelectByText(By element, String text) {
		library.waitForVisibilityOfElement(element);
		DropdownValueLoading(element);
		Select select = new Select(base.getDriver().findElement(element));
		select.selectByVisibleText(text);
	}

	public void DropdownSelectByIndx(By element, int text) {
		Select select = new Select(base.getDriver().findElement(element));
		select.selectByIndex(text);
	}

	public void DropdownSelectByvalue(By element, String text) {
		try {
			Select select = new Select(base.getDriver().findElement(element));
			select.selectByValue(text);
		} catch (Exception ex) {
			System.err.println("Error :" + ex);
		}
	}

	// Click Elements
	public void Clickon(By element) {
		try {
			base.getDriver().findElement(element).click();
		} catch (Exception e) {
			System.out.println("Exception Message: " + e.getMessage());
		}
	}

	public void Clickon(WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			System.out.println("Exception Message: " + e.getMessage());
		}
	}

	// Click Elements using JavascriptExecutor
	public void clickJSEon(By element) {
		JavascriptExecutor executor = (JavascriptExecutor) base.getDriver();
		executor.executeScript("arguments[0].click();", base.getDriver().findElement(element));
	}

	// Scroll down the page
	public void scrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor) base.getDriver();
		jse.executeScript("window.scrollBy(0,250)", "");
	}

	public void scrollDownLong() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) base.getDriver();
		jse.executeScript("window.scrollBy(0,500)", "");
	}

	// Scroll up the page
	public void scrollUpMin() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) base.getDriver();
		jse.executeScript("window.scrollBy(0,-250)", "");
	}

	public void scrollUpMax() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) base.getDriver();
		jse.executeScript("window.scrollBy(0,-500)", "");
	}

	// Move the control to particular Elements
	public void MoveToElement(By Element) {
		Actions action = new Actions(base.getDriver());
		library.waitForClickableElement(Element);
		WebElement WebEl = base.getDriver().findElement(Element);
		action.moveToElement(WebEl).build().perform();
	}

	public void MoveToElement(WebElement element) throws Throwable {
		Actions action = new Actions(base.getDriver());
		library.waitForClickableElement(element);
		action.moveToElement(element).build().perform();
	}

	// To verify the visibility of Element status
	public boolean DisplayStatus(By Element) {
		boolean Stat = false;
		try {
			Stat = base.getDriver().findElement(Element).isDisplayed();
		} catch (Exception ex) {
			System.out.println("Given Element is not displayed : " + Element);
		}
		return Stat;
	}

	public boolean DisplayStatus(WebElement element) {
		boolean Stat = false;
		try {
			Stat = element.isDisplayed();
		} catch (Exception ex) {
			System.err.println("Given Element is not displayed : " + element);
		}
		return Stat;
	}

	public void ActionMoveToElement(By Element) throws Throwable {
		Actions action = new Actions(base.getDriver());
		library.waitForVisibilityOfElement(Element);
		WebElement Uploadm = base.getDriver().findElement(Element);
		action.moveToElement(Uploadm).build().perform();
		Thread.sleep(1000);
	}

	public void ActionMoveToElementClcik(By Element) throws Throwable {
		Actions action = new Actions(base.getDriver());
		library.waitForVisibilityOfElement(Element);
		WebElement WebEle = base.getDriver().findElement(Element);
		action.moveToElement(WebEle).click().build().perform();
		Thread.sleep(1000);
	}

	public void SelectFirstCheckboxinGrid(By GrdElement, int st) throws Throwable {
		library.waitForVisibilityOfElement(GrdElement);
		List<WebElement> GridRow = base.getDriver().findElement(GrdElement).findElements(By.tagName("tr"));
		List<WebElement> GridColum = GridRow.get(1).findElements(By.tagName("td"));
		WebElement Chkbox = GridColum.get(st).findElement(By.tagName("input"));
		Clickon(Chkbox);

	}

	public String GetValueXpathLabel(String XpathTxt) {
		String ValTxt = base.getDriver()
				.findElement(By.xpath("//div[contains(text(),'" + XpathTxt + "')]/following-sibling:: div")).getText();
		return ValTxt;
	}

	// Splits comman seperated text to list
	public ArrayList<Object> ConvertstringTolist(String text) {
		ArrayList<Object> list = new ArrayList<>();
		list = Lists.newArrayList(Splitter.on(",").split(text));
		return list;
	}

	public void AcceptAlert() throws Exception {
		String Content = "";
		WebDriverWait wait = new WebDriverWait(base.getDriver(), Duration.ofSeconds(base.globalWait));
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = base.getDriver().switchTo().alert();
		Thread.sleep(3000);
		alert.accept();
	}

	public void CancelAlert() throws Exception {
		String Content = "";
		Alert alert = base.getDriver().switchTo().alert();
		Thread.sleep(3000);
		alert.dismiss();
	}

	public void VerfiyAlertMsgTextAccept(String alertMessage) throws Exception {
		String Content = "";
		Alert alert = base.getDriver().switchTo().alert();
		Content = alert.getText();
		Content = Content.replaceAll("(\\r|\\n)", "");
		System.out.println(Content);
		assertTrue(Content.contains(alertMessage));
		Thread.sleep(3000);
		alert.accept();
	}
	// Grid Activity

	public void gridDoubleClick(WebElement rownumber) {
		Actions action = new Actions(base.getDriver());
		library.waitForClickableElement(rownumber);

		action.moveToElement(rownumber).doubleClick(rownumber).build().perform();

		/*
		 * try { action.moveToElement(rownumber).click(); rownumber.click();
		 * rownumber.click(); } catch (Exception e) {
		 * 
		 * }
		 */
	}

	public void verifyValueingrid(int mintblcolum, WebElement gridID, HashMap<Integer, String> givenTableValue) {
		boolean matchFound = false;
		String givenValueInGrid = null;
		// waitForInVisibilityOfElement(GirdloadingImg);
		WebElement table = gridID;
		List<WebElement> tblRows = table.findElements(By.tagName("tr"));
		int tblRowscount = tblRows.size();
		for (int row = 0; row < tblRowscount; row++) {
			List<WebElement> actualTblData = tblRows.get(row).findElements(By.tagName("td"));
			int tblDataCount = actualTblData.size();

			if (tblDataCount <= mintblcolum) {
				continue;
			}
			matchFound = true;
			Set<Integer> keys = givenTableValue.keySet();
			for (Integer key : keys) {
				givenValueInGrid = givenTableValue.get(key);

				// System.out.println("Given of " + key + " is: " +
				// givenValueInGrid);

				if (actualTblData.get(key) != null
						&& !givenTableValue.get(key).equals(actualTblData.get(key).getText())) {
					matchFound = false;
					break;
				}
			} // For Key Loop End

			if (matchFound == true) {
				Assert.assertTrue("The records have shown properly", matchFound);
				break;
			}

		} // Main For End
		if (matchFound == false) {
			givenTableValue.clear();
			Assert.fail("The records not shown properly");
			table.click();
		}
		givenTableValue.clear();

	}

	public void GridSelectRowAndClick(int mintblcolum, WebElement gridID, HashMap<Integer, String> givenTableValue) {
		boolean matchFound = false;
		String givenValueInGrid = null;
		// waitForInVisibilityOfElement(GirdloadingImg);
		WebElement table = gridID;
		List<WebElement> tblRows = table.findElements(By.tagName("tr"));
		int tblRowscount = tblRows.size();
		for (int row = 0; row < tblRowscount; row++) {
			List<WebElement> actualTblData = tblRows.get(row).findElements(By.tagName("td"));
			int tblDataCount = actualTblData.size();

			if (tblDataCount <= mintblcolum) {
				continue;
			}
			matchFound = true;
			Set<Integer> keys = givenTableValue.keySet();
			for (Integer key : keys) {
				givenValueInGrid = givenTableValue.get(key);
				if (actualTblData.get(key) != null
						&& !givenTableValue.get(key).equals(actualTblData.get(key).getText())) {
					matchFound = false;
					break;
				}
			} // For Key Loop End
			if (matchFound == true) {
				Assert.assertTrue("The records have shown properly", matchFound);
				tblRows.get(row).click();
				break;
			}
		} // Main For End
		if (matchFound == false) {
			givenTableValue.clear();
			Assert.fail("The records not shown properly");
			table.click();
		}
		givenTableValue.clear();

	}

	public void GridSelectRowAndDoubleClick(int mintblcolum, WebElement gridID,
			HashMap<Integer, String> givenTableValue) throws Throwable {
		boolean matchFound = false;
		String givenValueInGrid = null;
		// waitForInVisibilityOfElement(GirdloadingImg);
		WebElement table = gridID;
		List<WebElement> tblRows = table.findElements(By.tagName("tr"));
		int tblRowscount = tblRows.size();
		for (int row = 0; row < tblRowscount; row++) {
			List<WebElement> actualTblData = tblRows.get(row).findElements(By.tagName("td"));
			int tblDataCount = actualTblData.size();

			if (tblDataCount <= mintblcolum) {
				continue;
			}
			matchFound = true;
			Set<Integer> keys = givenTableValue.keySet();
			for (Integer key : keys) {
				givenValueInGrid = givenTableValue.get(key);
				if (actualTblData.get(key) != null
						&& !givenTableValue.get(key).equals(actualTblData.get(key).getText())) {
					matchFound = false;
					break;
				}
			} // For Key Loop End
			if (matchFound == true) {
				Assert.assertTrue("The records have shown properly", matchFound);
				WebElement rownumber = tblRows.get(row);
				Actions action = new Actions(base.getDriver());
				library.waitForClickableElement(rownumber);
				action.moveToElement(rownumber).click();
				action.doubleClick(rownumber).build().perform();
				break;
			}
		} // Main For End
		if (matchFound == false) {
			givenTableValue.clear();
			Assert.fail("The records not shown properly");
			table.click();
		}
		givenTableValue.clear();

	}

	public void checkPageIsReady() {
		JavascriptExecutor js = (JavascriptExecutor) base.getDriver();
		// Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			return;
		}
		// This loop will rotate for 25 times to check If page Is ready after every 1
		// second.
		// You can replace your value with 25 If you wants to Increase or
		// decrease wait time.
		for (int i = 0; i < 15; i++) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			// To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}

	public void acceptPopUp(By popUpOk_Button) throws Throwable {
		try {
			library.waitForClickableElement(popUpOk_Button);
			base.getDriver().findElement(popUpOk_Button).click();
		} catch (Exception e) {
			((JavascriptExecutor) base.getDriver()).executeScript("arguments[0].scrollIntoView(true);",
					base.getDriver().findElement(popUpOk_Button));
		}
	}

	public void handleMultipleWindows(WebDriver driver, String currentURL) {
		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			driver.switchTo().window(window);
			if (driver.getCurrentUrl().contains(currentURL)) {
				return;
			}
		}
	}

	public void scrollToView(By element) {
		// Find element by link text and store in variable "Element"
		boolean stat = base.getDriver().findElements(element).size() > 0;
		if (stat == true) {
			WebElement Element = base.getDriver().findElement(element);
			JavascriptExecutor js = (JavascriptExecutor) base.getDriver();
			// This will scroll the page till the element is found
			js.executeScript("arguments[0].scrollIntoView(true);", Element);
		}
	}

//	public void DatePicker_New(By selector, String getDate) {
//		try {
//		WebElement dateInput = driver.findElements(selector).get(0);
//		 dateInput.click();
//		 String[] date1 = Splitdate(getDate);
//		 String GetYear = date1[2];
//		String Getmonth = date1[1];
//		 String getDated = date1[0];
//		selectyear(GetYear, 1);
//		selectmonth(Getmonth);
//		 List<WebElement> datefield = driver.findElements(DATESELECT);
//		 datefield.get(Integer.parseInt(getDated.toString()) - 1).click();
//		} catch (Exception ex) {
//		ex.printStackTrace();
//		}
//	}

//	public void validate_the_message(WebElement element,Msg) throws Throwable {
//		Thread.sleep(2000);
//	   String Invalid_ICnum= element.getText();
//	   System.out.println("Error Msg is"+Invalid_ICnum);
//	   
//
//	}

	public void switchToNewWindow() throws Throwable {
		String currentWindow = base.getDriver().getWindowHandle();
		Iterator<String> windows = base.getDriver().getWindowHandles().iterator();
		while (windows.hasNext()) {
			String window = windows.next();
			if (!currentWindow.equalsIgnoreCase(window)) {

				base.getDriver().switchTo().window(window);
				base.getDriver().manage().window().maximize();
				break;
			}
		}
	}

	public void sendkeysByAction(By Element, String value) {
		Actions action = new Actions(base.getDriver());
		action.moveToElement(base.getDriver().findElement(Element)).click().sendKeys(value).pause(Duration.ofSeconds(2))
				.sendKeys(Keys.ENTER).build().perform();
	}

	public Boolean checkBoxSelected(By Element) throws Throwable {

		Boolean chckbox = base.getDriver().findElement(Element).isSelected();
		return chckbox;

	}

	public void GridselectRow(By locAllRows, By LocValue) throws Exception {

		// Get all the rows
		// By locAllRows =
		// By.xpath(".//*[contains(@class,'x-grid3-body')]//*[contains(@class,'x-grid3-row')]");
		List<WebElement> allRows = base.getDriver().findElements(locAllRows);

		for (WebElement row : allRows) {
			// Get the Emp. No.
			WebElement ActRow = row.findElement(LocValue);
			// Compare actual vs expected
			if (row.findElements(LocValue).size() > 0) {
				gridDoubleClick(ActRow);
				// row.click(); // Select row
				// System.out.println("Selected row " + (allRows.indexOf(row) + 1) + " having
				// Emp. No. " + expEmpNo);
				break; // exit the for loop
			}
		}
	}

	public boolean EnabledStatus(By Element) {
		boolean Stat = false;
		try {
			Stat = base.getDriver().findElement(Element).isEnabled();
		} catch (Exception ex) {
			System.out.println("Given Element is not enabled : " + Element);
		}
		return Stat;
	}

	public String GetSelctedOption(By element) throws Throwable {
		String option = "";
		try {
			library.waitForVisibilityOfElement(element);
			Select select = new Select(base.getDriver().findElement(element));
			option = select.getFirstSelectedOption().getText();

		} catch (Exception ex) {
			System.err.println("Error :" + ex);
		}
		return option;
	}

	public void actionDoubleClick(By Element) {
		Actions action = new Actions(base.getDriver());
		library.waitForClickableElement(Element);

		action.moveToElement(base.getDriver().findElement(Element)).doubleClick(base.getDriver().findElement(Element))
				.build().perform();
	}

	public void doubleClickWithJS(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) base.getDriver();
		executor.executeScript("arguments[0].dispatchEvent(new MouseEvent('dblclick', { bubbles: true }));", element);
	}

	public void actionClick(By Element) {
		Actions action = new Actions(base.getDriver());
		library.waitForClickableElement(Element);

		action.pause(Duration.ofSeconds(2)).moveToElement(base.getDriver().findElement(Element))
				.pause(Duration.ofSeconds(2)).click(base.getDriver().findElement(Element)).build().perform();
	}

	public void actionDropDown(By Element, String Text) {
		Actions action = new Actions(base.getDriver());
		library.waitForClickableElement(base.getDriver().findElement(Element));
		// action.moveToElement(base.getDriver().findElement(Element)).click().sendKeys(Text)
		action.moveToElement(base.getDriver().findElement(Element)).click().sendKeys(Text).build().perform();
		action.pause(Duration.ofSeconds(1)).build().perform();
		action.sendKeys(Keys.DOWN).pause(Duration.ofSeconds(1)).build().perform();
		action.sendKeys(Keys.ENTER).build().perform();

	}

	public void actionSelectDrpDwn(By Element, String Text) {
		Actions action = new Actions(base.getDriver());
		library.waitForClickableElement(base.getDriver().findElement(Element));
		// action.moveToElement(base.getDriver().findElement(Element)).click().sendKeys(Text)
		action.moveToElement(base.getDriver().findElement(Element)).pause(Duration.ofSeconds(1)).click().build()
				.perform();
		action.sendKeys(Text).build().perform();
		action.pause(Duration.ofSeconds(1)).sendKeys(Keys.ENTER).build().perform();

	}

	public void DatePicker(String date, By yearxpath, By monthxpath, By dayxpath) throws InterruptedException {
		System.out.println(date);
		String splitter[] = date.split("/");
		String inputString = splitter[0];
		String month = splitter[1];
		String year = splitter[2];

		int intString = Integer.parseInt(inputString);

		String daystring = String.valueOf(intString);
		int dayint = Integer.parseInt(daystring);
		String day = Integer.toString(dayint);

		library.waitForVisibilityOfElement(By.xpath("//button[@class='current']"));
		clickJSEon(By.xpath("//button[@class='current']"));
		if (report.getApplicationName().equalsIgnoreCase("V4") || report.getApplicationName().equalsIgnoreCase("CSC")
				|| report.getApplicationName().equalsIgnoreCase("ADP")) {
			library.waitForInVisibilityOfElement(By.xpath("//div[@class='la-ball-clip-rotate la-2x']"));
		}
		List<WebElement> years = base.getDriver().findElements(yearxpath);
		Thread.sleep(500);

		String startyr = null;
		String lastyr = null;
		startyr = years.get(0).getText();
		int yrint = Integer.parseInt(startyr);
		int yearint = Integer.parseInt(year);

		lastyr = years.get(years.size() - 1).getText();
		int lastyrint = Integer.parseInt(lastyr);
//		int lastyearint=Integer.parseInt(year);

		if (yrint > yearint) {
			do {
				clickJSEon(By.cssSelector("button[class='previous']"));
				years = base.getDriver().findElements(yearxpath);
				startyr = years.get(0).getText();
				yrint = Integer.parseInt(startyr);
			} while (yrint > yearint);

		}
		if (lastyrint < yearint) {
			do {
				clickJSEon(By.cssSelector("button[class='next']"));
				years = base.getDriver().findElements(yearxpath);
				startyr = years.get(years.size() - 1).getText();
				yrint = Integer.parseInt(startyr);
//				 yearint=Integer.parseInt(year);
			} while (yrint > yearint);
		}
		for (int i = 0; i < years.size(); i++) {
//		library.waitForVisibilityOfElement(years.get(i));
			if (years.get(i).getText().equals(year)) {
				if (report.getApplicationName().equalsIgnoreCase("V4")
						|| report.getApplicationName().equalsIgnoreCase("CSC")
						|| report.getApplicationName().equalsIgnoreCase("ADP")) {
					library.waitForInVisibilityOfElement(By.xpath("//div[@class='la-ball-clip-rotate la-2x']"));
				}
				library.waitForVisibilityOfElement(years.get(i));
				Thread.sleep(500);
				library.waitForClickableElement(years.get(i), 250);
				System.out.println(years.get(i).getText());
				years.get(i).click();
				break;
			}

		}
		List<WebElement> months = base.getDriver().findElements(monthxpath);
		Thread.sleep(500);
		for (int j = 0; j < months.size(); j++) {
//		library.waitForVisibilityOfElement(months.get(j));
			if (months.get(j).getText().contains(month)) {
				if (report.getApplicationName().equalsIgnoreCase("V4")
						|| report.getApplicationName().equalsIgnoreCase("CSC")
						|| report.getApplicationName().equalsIgnoreCase("ADP")) {
					library.waitForInVisibilityOfElement(By.xpath("//div[@class='la-ball-clip-rotate la-2x']"));
				}
				library.waitForVisibilityOfElement(months.get(j));
				library.waitForClickableElement(months.get(j), 250);
				System.out.println(months.get(j).getText());
				months.get(j).click();
				break;
			}
		}
		List<WebElement> days = base.getDriver().findElements(dayxpath);
		Thread.sleep(500);
		for (int k = 0; k < days.size(); k++) {
//		Thread.sleep(300);		
			if (report.getApplicationName().equalsIgnoreCase("V4")
					|| report.getApplicationName().equalsIgnoreCase("CSC")
					|| report.getApplicationName().equalsIgnoreCase("ADP")) {
				library.waitForInVisibilityOfElement(By.xpath("//div[@class='la-ball-clip-rotate la-2x']"));
			}
			library.waitForVisibilityOfElement(days.get(k));
			if (days.get(k).getText().equals(day)) {
				Thread.sleep(500);
				if (!days.get(k).getAttribute("class").equals("is-other-month")
						&& !days.get(k).getAttribute("class").equals("disabled is-other-month")) {
					if (report.getApplicationName().equalsIgnoreCase("V4")
							|| report.getApplicationName().equalsIgnoreCase("CSC")
							|| report.getApplicationName().equalsIgnoreCase("ADP")) {
						library.waitForInVisibilityOfElement(By.xpath("//div[@class='la-ball-clip-rotate la-2x']"));
					}
					library.waitForVisibilityOfElement(days.get(k));
					library.waitForClickableElement(days.get(k), 250);
					System.out.println(days.get(k).getText());
					Thread.sleep(500);
					scrollDown();
					Thread.sleep(500);
					days.get(k).click();
					break;
				}

			}
		}
//		Thread.sleep(3000);
	}

	public void autoSuggestDropdown(By element_txt, String select_txt) throws Exception {
		// EnterKeyon(element_txt, Keys.ENTER);
		// EnterValueon(element_txt, select_txt);
		List<WebElement> veh_list = base.getDriver().findElements(By.xpath("//ul//li//span"));
		for (WebElement element : veh_list) {
			String txt = GetTexton(element);
			if (select_txt.equals(txt)) {
				Clickon(element);
				break;
			}
		}
	}

	public static String removeLeadingZeroes(String str) {
		String strPattern = "^0+(?!$)";
		str = str.replaceAll(strPattern, "");
		return str;
	}

	public void DatePickerBANCS(String date, By yearxpath, By monthxpath, By dayxpath) throws InterruptedException {

		System.out.println(date);

		String splitter[] = date.split("/");

		String day = splitter[0];
		String month = splitter[1];
		String year = splitter[2];

		day = removeLeadingZeroes(day);

		System.out.println(day);

		System.out.println(month);

		System.out.println(year);

		// Click center of calendar
		clickJSEon(By.xpath("//div[@class='datepicker-days']//th[@class='datepicker-switch']"));
		// Click center of calendar to get year
		clickJSEon(By.xpath("//div[@class='datepicker-months']//th[@class='datepicker-switch']"));

		List<WebElement> years = base.getDriver().findElements(yearxpath);
		// By.xpath("//div[@class='datepicker-years']//span"));

		int i = 0;
		int lastYearValue = (years.size()) - 1;
		int Year = Integer.valueOf(year);

		System.out.println(Integer.parseInt(years.get(0).getText()));

		/*
		 * while(Year > (Integer.parseInt(years.get(0).getText()))|| Year <
		 * Integer.parseInt(years.get(lastYearValue).getText())) {
		 * if(Integer.valueOf(years.get(0).getText()) < Integer.valueOf(year)){
		 * clickJSEon(By.xpath("//div[@class='datepicker-years']//th[@class='prev']"));
		 * }
		 * 
		 * if(Integer.valueOf(years.get(lastYearValue).getText()) >
		 * Integer.valueOf(year)){
		 * clickJSEon(By.xpath("//div[@class='datepicker-years']//th[@class='next']"));
		 * } }
		 */

		years = base.getDriver().findElements(yearxpath);
		// By.xpath("//div[@class='datepicker-years']//span"));

		for (i = 0; i < years.size(); i++)

		{
			System.out.println(years.get(i).getText());

			if (years.get(i).getText().equals(year))

			{
				years.get(i).click();
				break;
			} else if (i == (years.size() - 1) && (!year.equals(years.get(i).getText()))) {
				clickJSEon(By.xpath("//div[@class='datepicker-years']//th[@class='prev']"));
				years = base.getDriver().findElements(yearxpath);
				i = 0;
			}

		}

		List<WebElement> months = base.getDriver().findElements(monthxpath);
		// By.xpath("//div[@class='datepicker-months']//span"));

		for (int j = 0; j < months.size(); j++)

		{

			System.out.println(months.get(j).getText());

			if (months.get(j).getText().equalsIgnoreCase(month))

			{

				months.get(j).click();

				break;

			}

		}

		List<WebElement> days = base.getDriver().findElements(dayxpath);
		// By.xpath("//div[@class='datepicker-days']/table/tbody/tr/td"));

		for (int k = 0; k < days.size(); k++)

		{

			System.out.println(days.get(k).getText());

			if (days.get(k).getText().equals(day))

			{

				if (!days.get(k).getAttribute("class").equals("old day")) {

					days.get(k).click();

					break;

				}

			}

		}

		Thread.sleep(3000);

	}

	// Scroll up the page
	public void scrollUpDown(int scroll) {
		JavascriptExecutor jse = (JavascriptExecutor) base.getDriver();
		jse.executeScript("window.scrollBy(0," + scroll + ")", "");
	}

	// scroll till page end
	public void scrollPageEnd() {
		JavascriptExecutor js = (JavascriptExecutor) base.getDriver();
		// Scroll down till the bottom of the page
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	// autosuggest dropdown
	public void autoSuggestDropdown(By element_txt, By elements, String select_txt) throws Exception {
//        EnterKeyon(element_txt, Keys.ENTER);
		Clickon(element_txt);
		Thread.sleep(1000);
		EnterValueon(element_txt, select_txt);
		List<WebElement> veh_list = base.getDriver().findElements(elements);
		for (WebElement element : veh_list) {
			String txt = GetTexton(element);
			if (select_txt.equals(txt)) {
				Clickon(element);
				break;
			}
		}
	}

	public void DatePickerINFINITY(String date, By yearxpath, By monthxpath, By dayxpath) throws InterruptedException {

		System.out.println(date);

		String splitter[] = date.split("/");

		String inputString = splitter[0];

		String month = splitter[1];

		String year = splitter[2];

		int intString = Integer.parseInt(inputString);

		String daystring = String.valueOf(intString);

		int dayint = Integer.parseInt(daystring);

		String day = Integer.toString(dayint);

		clickJSEon(By.cssSelector(".p-inputtext.p-component"));

		Thread.sleep(2000);

		clickJSEon(By.cssSelector("[class='p-datepicker-year p-link']"));

		int count = 0;

		for (int k = 0; k < Integer.MAX_VALUE; k++) {

			Thread.sleep(500);

			List<WebElement> yearsList = base.getDriver().findElements(yearxpath);

			for (int i = 1; i <= yearsList.size(); i++) {

				if (yearsList.get(i).getText().equals(year)) {

					System.out.println(yearsList.get(i).getText());

					yearsList.get(i).click();

					count++;

					break;

				} else {

					Clickon(By.cssSelector("[class='p-datepicker-next']"));

					yearsList.clear();

				}

			}

			if (count > 0) {

				break;

			}

		}

		List<WebElement> months = base.getDriver().findElements(monthxpath);

		for (int j = 0; j < months.size(); j++) {

			if (months.get(j).getText().equalsIgnoreCase(month)) {

				System.out.println(months.get(j).getText());

				months.get(j).click();

				break;

			}

		}

		List<WebElement> days = base.getDriver().findElements(dayxpath);

		for (int k = 0; k < days.size(); k++) {

			if (days.get(k).getText().equals(day)) {

				if (!days.get(k).getAttribute("class").equals("is-other-month")) {

					System.out.println(days.get(k).getText());

					days.get(k).click();

					break;

				}

			}

		}

		Thread.sleep(3000);

	}

	public void DatePickerSPAA(String date, By yearxpath, By monthxpath, By dayxpath) throws InterruptedException {
		System.out.println(date);
		String splitter[] = date.split("-");
		String inputString = splitter[0];
		String month = splitter[1];
		String year = splitter[2];
		int intString = Integer.parseInt(inputString);
		String daystring = String.valueOf(intString);
		int dayint = Integer.parseInt(daystring);
		String day = Integer.toString(dayint);
		Thread.sleep(2000);
		clickJSEon(By.xpath("//*[@class='datepicker-days']//*[@class='datepicker-switch']"));
		// Click center of calendar to get year
		clickJSEon(By.xpath("//*[@class='datepicker-months']//*[@class='datepicker-switch']"));

		List<WebElement> years = base.getDriver().findElements(yearxpath);
		int i = 0;
		int lastYearValue = (years.size()) - 1;
		int Year = Integer.valueOf(year);
		System.out.println(Integer.parseInt(years.get(0).getText()));
		years = base.getDriver().findElements(yearxpath);
		for (i = 0; i < years.size(); i++) {
			System.out.println(years.get(i).getText());
			if (years.get(i).getText().equals(year)) {
				years.get(i).click();
				break;
			} else if (i == (years.size() - 1) && (!year.equals(years.get(i).getText()))) {
				clickJSEon(By.xpath("//div[@class='datepicker-years']//th[@class='prev']"));
				years = base.getDriver().findElements(yearxpath);
				i = 0;
			}
		}
		List<WebElement> months = base.getDriver().findElements(monthxpath);
		// By.xpath("//div[@class='datepicker-months']//span"));
		for (int j = 0; j < months.size(); j++) {
			System.out.println(months.get(j).getText());
			if (months.get(j).getText().equalsIgnoreCase(month)) {
				months.get(j).click();
				break;
			}
		}
		List<WebElement> days = base.getDriver().findElements(dayxpath);
		for (int k = 0; k < days.size(); k++) {
			System.out.println(days.get(k).getText());
			if (days.get(k).getText().equals(day)) {
				if (!days.get(k).getAttribute("class").equals("active day")) {
					days.get(k).click();
					break;
				}
			}
		}
		Thread.sleep(3000);
	}

	public void SwitchTab(int tab) {
		ArrayList<String> tabs = new ArrayList<String>(base.getDriver().getWindowHandles());
		base.getDriver().switchTo().window(tabs.get(tab));
	}

	public void DragAndDrop(WebElement from, WebElement To) {

		Actions dragAndDrop = new Actions(base.getDriver());
		dragAndDrop.clickAndHold(from).moveToElement(To).release().build().perform();

	}

	public void SuggestDropdown(By element_txt, String select_txt) throws Exception {
		// EnterKeyon(element_txt, Keys.ENTER);
		// EnterValueon(element_txt, select_txt);
		List<WebElement> veh_list = base.getDriver().findElements(By.xpath("//ul//li//span"));
		for (WebElement element : veh_list) {
			String txt = GetTexton(element);
			if (select_txt.equals(txt)) {
				Clickon(element);
				break;
			}
		}
	}

	public void SuggestDropdown(By element_txt, By element_lst, String select_txt) throws Exception {
		// EnterKeyon(element_txt, Keys.ENTER);
		// EnterValueon(element_txt, select_txt);
		List<WebElement> veh_list = base.getDriver().findElements(element_lst);
		for (WebElement element : veh_list) {
			String txt = GetTexton(element);
			if (select_txt.equals(txt)) {
				Clickon(element);
				break;
			}
		}
	}
}
