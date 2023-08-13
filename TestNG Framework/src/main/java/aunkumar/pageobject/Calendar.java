package aunkumar.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import arunkumar.absractcom.CommonlyUsing;

public class Calendar extends CommonlyUsing{
	WebDriver Driver;

	public Calendar(WebDriver Driver) {
		super(Driver);
		PageFactory.initElements(Driver, this);
	}
	
	@FindBy(css =".fc-icon.fc-icon-left-single-arrow")
	WebElement goback;
	
    @FindBy(css =".fc-icon.fc-icon-right-single-arrow")
    WebElement gofront;
    
    @FindBy(xpath="//button[normalize-space()='today']")
    WebElement isenabled;
    
    @FindBy(css="div[class='fc-center'] h2")
    WebElement months;
    
    @FindBy(className="date-events")
    List<WebElement> alldata;
    
    public void gotoprevious() throws InterruptedException {
    	Thread.sleep(4000);
		int j = 0;
		while (j < 2) {
			Thread.sleep(4000);
			goback.click();
			j++;
			 }
    }
    public void gettingdata() {

		boolean wl = true;
		while (wl == true) {
			System.out.println(months.getText().toUpperCase());
			//System.out.println();
			System.out.println("--------------");
			System.out.println();
			
			for (WebElement w : alldata) {
				System.out.println(w.getAttribute("data-date"));
				System.out.println(w.getText());
				System.out.println();
				//Thread.sleep(9000);
			}
					gofront.click();
			wl =isenabled.isEnabled();
		}
    }
}
