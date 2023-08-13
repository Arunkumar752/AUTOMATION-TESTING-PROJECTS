package aunkumar.pageobject;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import arunkumar.absractcom.CommonlyUsing;

public class ApplyLeave extends CommonlyUsing{
   
	public WebDriver Driver;
	
	public ApplyLeave(WebDriver Driver) {
		super(Driver);
		this.Driver=Driver;
		PageFactory.initElements(Driver, this);
		
	}
    @FindBy(xpath="//i[@class='menu-icon fa fa fa-calendar']")
    WebElement olmss;
    
    @FindBy(xpath="//span[normalize-space()='Calendar']")
    WebElement homee;
    
	
	@FindBy(xpath = "//button[normalize-space()='Leave']")
	WebElement leavebut;
	
	@FindBy(css = "#select2-chosen-15")
	WebElement wb;
	
	@FindBy(id = "Reason")
	WebElement reason;
	
	@FindBy(xpath = "(//button[normalize-space()='Cancel'])[1]")
	WebElement cancel;
	
	public void loginIntoApp() throws InterruptedException {
	    Driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		Thread.sleep(3000);
    	Actions a = new Actions(Driver);
    	a.moveToElement(olmss).build().perform();
        homee.click();
        waitForWebElementToAppear(leavebut);
        leavebut.click();
        Thread.sleep(3000);
       
    	//Select s = new Select(wb);
    	 wb.click();
    	 Thread.sleep(2000);
	//	s.selectByIndex(7);
		//reason.sendKeys("im not feeling well");
    	 cancel.click();
    	 cancel.click();

		}
	}


