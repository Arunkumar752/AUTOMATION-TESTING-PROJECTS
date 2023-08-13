package arunkumar.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Check {
	public static void main(String[] args) throws InterruptedException {
		 WebDriverManager.chromedriver().setup();
		    WebDriver drive = new ChromeDriver();
		    drive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    drive.get("https://talentx.kgisl.com/");
		    drive.findElement(By.xpath("//input[@id='Username']")).sendKeys("arunkumar.c");
		    drive.findElement(By.id("Password")).sendKeys("Kgisl@1234");
		    drive.findElement(By.xpath("(//button[@type='submit'])[1]")).click();
		    WebElement olms = drive.findElement(By.xpath("//i[@class='menu-icon fa fa fa-calendar']"));
			Actions a = new Actions(drive);
	    	a.moveToElement(olms).build().perform();
		    drive.findElement(By.xpath("//span[normalize-space()='Calendar']")).click();
		    Thread.sleep(4000);
		    
		    int j = 0;
		    while (j < 3) {
		    	Thread.sleep(4000);
		        drive.findElement(By.cssSelector(
		                ".fc-icon.fc-icon-left-single-arrow"))
		                .click();
		        j++;
		         }
		        //Thread.sleep(4000);
		        boolean wl = true;
		        while (wl == true) {
		            System.out.println(drive.findElement(By.cssSelector("div[class='fc-center'] h2")).getText().toUpperCase());
		            //System.out.println();
		            System.out.println("--------------");
		            System.out.println();
		            List<WebElement> all = drive.findElements(By.className("date-events"));
		            for (WebElement w : all) {
		                System.out.println(w.getAttribute("data-date"));
		                System.out.println(w.getText());
		                System.out.println();
		                //Thread.sleep(9000);
		            }
		            drive.findElement(By.cssSelector(
		                    ".fc-icon.fc-icon-right-single-arrow"))
		                    .click();
		            wl = drive.findElement(By.xpath("//button[normalize-space()='today']")).isEnabled();
		        }
		        
//		     
		     }
		        
		    }
	

