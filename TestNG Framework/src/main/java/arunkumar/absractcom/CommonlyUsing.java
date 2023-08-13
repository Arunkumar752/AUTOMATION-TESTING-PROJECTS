package arunkumar.absractcom;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonlyUsing {
     WebDriver Driver;
      
	public CommonlyUsing(WebDriver Driver) {
		this.Driver = Driver;
		PageFactory.initElements(Driver, this);
	}
	
	 @FindBy(css = "img[alt='User Photo']")
     WebElement checkLoginInto;

	public void waitForElementToAppear(By findBy) {

		WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));

	}
	
	public void waitForWebElementToAppear(WebElement findBy) {

		WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(findBy));

	}
	
	  public Boolean checkloginIntoo() {
		   waitForWebElementToAppear(checkLoginInto);
	      return  checkLoginInto.isDisplayed();
	    }
}
