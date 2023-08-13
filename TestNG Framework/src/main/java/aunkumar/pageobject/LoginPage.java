package aunkumar.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import arunkumar.absractcom.CommonlyUsing;

public class LoginPage extends CommonlyUsing{
WebDriver Driver;

    public LoginPage(WebDriver Driver){
    	super(Driver);
    	this.Driver = Driver;
    	PageFactory.initElements(Driver, this);
    }
    
    @FindBy(xpath="//input[@id='Username']")
    private WebElement UserName;
    
    @FindBy(id="Password")
    private WebElement PassWord;
    
    @FindBy(xpath = "(//button[@type='submit'])[1]")
    private WebElement Button;
    
    @FindBy(xpath = "//div[@class='error']")
    public WebElement errormes;
    
    @FindBy(css="img[alt='User Photo']")
    public WebElement isdisplayed;
    
    public boolean  LoginApplication(String username,String password) {
        UserName.sendKeys(username);
        PassWord.sendKeys(password);
        Button.click();
        try {
            if(isdisplayed.isDisplayed()==true) {
                System.out.println(isdisplayed.isDisplayed());
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception            return false;
        }
        return false;
    	
    }
    public void goTo()
	{
		Driver.get("https://talentx.kgisl.com/");
	}
    
    public String getErroMessage() {
    	return errormes.getText();
    	
    }
    public boolean isdispal() {
    	if (errormes.getText() == "Invalid Username/Password") {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public Boolean is_chec() {
        boolean checking = isdisplayed.isDisplayed();
        return checking;
    }

  }
    

