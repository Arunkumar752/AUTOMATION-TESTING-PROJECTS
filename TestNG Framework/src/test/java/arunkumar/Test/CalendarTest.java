package arunkumar.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import arunkmar.TestCompo.BaseTestCommon;
import aunkumar.pageobject.Calendar;
import aunkumar.pageobject.ApplyLeave;
import aunkumar.pageobject.OlmsPage;
import io.github.bonigarcia.wdm.WebDriverManager;


public class CalendarTest extends BaseTestCommon {
     
//      @Test(retryAnalyzer=Retry.class)
//      public void loginTe() {
//    	  li.loginIntoApp("arunkumar.c", "Kgisl@1234");
//    	 
//      }
//	@Test(priority = 3,enabled =false)
//	   public void gettingCalendarData() throws InterruptedException {
//		   OlmsPage olp = li.LoginApplication("arunkumar.c", "Kgisl@1234");
//		   Calendar cl = olp.gettingmenu();
//		   
//		   cl.gotoprevious();
//		   Thread.sleep(4000);
//		   cl.gettingdata();
//		   
//	   }
	@Test(priority = 2,enabled=false)
	public void applyLeave() throws InterruptedException {
		li.LoginApplication("arunkumar.c", "Kgisl@1234");
		ApplyLeave li = new ApplyLeave(Driver);
		li.loginIntoApp();
	}
}
