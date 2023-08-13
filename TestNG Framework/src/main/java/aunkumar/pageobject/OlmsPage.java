package aunkumar.pageobject;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import arunkumar.absractcom.CommonlyUsing;
import io.reactivex.rxjava3.functions.Action;


public class OlmsPage extends CommonlyUsing {
    WebDriver Driver;
    OlmsPage(WebDriver Driver){
        super(Driver);
        this.Driver=Driver;
        PageFactory.initElements(Driver, this);
    }
    @FindBy(xpath="//i[@class='menu-icon fa fa fa-calendar']")
    WebElement olms;
    @FindBy(xpath="//span[normalize-space()='Calendar']")
    WebElement home;
    
  
    
    public Calendar gettingmenu() throws InterruptedException {
    	Thread.sleep(3000);
    	Actions a = new Actions(Driver);
    	a.moveToElement(olms).build().perform();
        home.click();
        Calendar cl = new Calendar(Driver);
        return cl;  
    }
    
  
    
    
}