package arunkumar.Test;

import org.testng.annotations.Test;

import arunkmar.TestCompo.BaseTestCommon;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;


import aunkumar.pageobject.OlmsPage;

public class LoginTestC extends BaseTestCommon {
	int j =1;
	 boolean checking;
	 boolean v;
	
	@Test(dataProvider = "getDataa",priority   = 1,enabled= true)
	public void checkValid(String username,String password) throws IOException {
		
		
        Boolean b = li.LoginApplication(username, password);
        //Assert.assertEquals("Invalid Username/Password",li.getErroMessage());        System.out.println(username);
        
        if(b==true) {
            System.out.println("written");
            setData(username,password,"PASS");
        }
        else {
            setData(username,password, "FAIL");
        }
    //  set_data(username,"PASS");       
        Assert.assertTrue(li.is_chec());
     }
	

	@Test(dataProvider = "getData",priority = 2,enabled = false )
	public void checkInvalid(HashMap<String,String> input) throws IOException {
		li.LoginApplication(input.get("userName"),input.get("password"));
		
		Assert.assertEquals("Invalid Username/Password",li.getErroMessage());
	}
	//HashMap<String,String> input    dataProvider = "getData"
	@DataProvider
	public Object[][] getData() throws IOException
	{

		List<HashMap<String,String>> data = getJsonDataToMaps(System.getProperty("user.dir")+"\\src\\test\\java\\arunkumar\\data\\PurchaseOrder.json");
		return new Object[][]  {{data.get(0)}, {data.get(1) },{data.get(2) } };	
	}
}
