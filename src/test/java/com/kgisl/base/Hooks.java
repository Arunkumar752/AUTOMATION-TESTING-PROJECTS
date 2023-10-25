package com.kgisl.base;

import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.manager.SeleniumManager;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

//import com.aventstack.extentreports.util.Assert;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class Hooks extends BaseUtil {

	public String platform,deviceName,platformVersion,App,UDID,appiumURL,packageName,activityName,device;
	public String  browser, environment, jenkinsuserName, jenkinspassword,mode,model,browserName,Browserversion;
	public String AppUrl;
	public AppiumDriverLocalService service = null;
	public String service_url;
	
	
	HashMap<String,String> Devicelist =new HashMap<String,String>();
	public FrameworkConfig config= ConfigFactory.create(FrameworkConfig.class);

	public String appId;

	public Hooks()  {

	}

	public int globalWait = config.SToWait1();
	public int implictwait = config.ImplictWait();
	public String appiumpath=config.Appiumpath();
	public String nodepath=config.NodePath();

	public WebDriver getDriver() {			
		if (config.Browser().equalsIgnoreCase("chrome")) {
			return driver;
		} else {
			return driver2;
		}	
	}


	public AppiumDriver getmDirver()  {
		return mDirver;
	}

	@SuppressWarnings("deprecation")
	public void launchBrowser() throws Exception {				 
		

			browser=config.Browser();
			environment =config.Environment();
		

		System.out.println("browser      :" +browser);  
		System.out.println("Environment  :" +environment);
		System.out.println(config.DriverAutodownload());
		
		if(environment.equalsIgnoreCase("UAT")){
//			AppUrl = config.BANCSAppURL();
//			username1=config.Username1();
//			password1 = config.Password1();
//			username2=config.Username2();
//			password2 = config.Password2();
//			username3=config.Username3();
//			password3 = config.Password3();
//			username4=config.Username4();
//			password4 = config.Password4();
//			username5=config.Username5();
//			password5 = config.Password5();

		}
		if(environment.equalsIgnoreCase("SQA")){
			AppUrl = config.SQAAppURL();

		}
		
		if (browser.equalsIgnoreCase("Firefox") && getDriver() == null) {
			if(config.DriverAutodownload()==true){
				//System.out.println(SeleniumManager.getInstance().getDriverPath("geckodriver"));
			}else{
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"/Drivers/geckodriver.exe");	
			}

			FirefoxOptions options = new FirefoxOptions();
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY);
			options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			if(config.Headless()==true) {
				options.addArguments("-headless");
			}
			driver2 = new FirefoxDriver(options);
			//driver2.manage().timeouts().implicitlyWait(Duration.ofMinutes(implictwait));
			driver2.manage().window().maximize();
			driver2.manage().deleteAllCookies();
			

		} else if (browser.equalsIgnoreCase("chrome") && getDriver() == null) {
			if (config.DriverAutodownload() == true) {
			//	System.out.println(SeleniumManager.getInstance().getDriverPath("chromedriver"));
			} else {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/Drivers/chromedriver.exe");
			}
			//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			if (config.Headless() == true) {
				options.addArguments("-headless");
				options.addArguments("--headless");
				options.addArguments("--disable-gpu");
				options.addArguments("--window-size=1920,1080");
				options.addArguments("--allow-insecure-localhost");
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("-guest");
			}
			options.addArguments("--ignore-ssl-errors");
			options.addArguments("-ignore-certificate-errors");
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
//			options.addArguments("--no-sandbox");
			options.addArguments("--incognito");
			options.addArguments("disable-popup-blocking");
			options.addArguments("--remote-allow-origins=*");
			//options.addArguments("-guest");
			try {
			driver = new ChromeDriver(options);
			}
			catch (SessionNotCreatedException e) {
	            // Handle the SessionNotCreatedException here
	            System.err.println("Not able to launch chrome " + e.getMessage());
	            // You can log the error or perform any necessary cleanup or actions
	            Assert.fail("Not able to launch chrome");
	        } 
			Capabilities cap=((RemoteWebDriver)driver).getCapabilities();
			browserName=cap.getBrowserName().toLowerCase();
			Browserversion=cap.getBrowserVersion().toString();
//			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.ImplictWait()));
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

	

		} else if (browser.equalsIgnoreCase("Edge") && getDriver() == null) {
			EdgeOptions edgeOptions = new EdgeOptions();
			if(config.DriverAutodownload()==true){
		//		System.out.println(SeleniumManager.getInstance().getDriverPath("msedgedriver"));
			}else{
				System.setProperty("webdriver.edge.driver", "Drivers//msedgedriver.exe");
			}

			if(config.Headless()==true) {
				edgeOptions.addArguments("-headless");
				
			}
			edgeOptions.addArguments("-guest");
			edgeOptions.addArguments("--remote-allow-origins=*");
			edgeOptions.setAcceptInsecureCerts(true);
			driver2 = new EdgeDriver(edgeOptions);
			//driver2.manage().timeouts().implicitlyWait(Duration.ofMinutes(implictwait));
			driver2.manage().window().maximize();
			driver2.manage().deleteAllCookies();

		} 
	}



	public void getDeviceDetails(){

		List<String>deviceDts=null;
		if(config.MobileRuntype().equalsIgnoreCase("Jenkins")){
			String Devices=System.getProperty("Device");
			deviceDts = Arrays.asList(Devices.split(","));
		}else{
			deviceDts=config.Device();
		}

		deviceName=deviceDts.get(0);
		platform=deviceDts.get(1);		
		platformVersion=deviceDts.get(2);
		if(!config.MobileRuntype().equalsIgnoreCase("cloud")){
			if(platform.equalsIgnoreCase("android")){
				packageName = config.MobileAppPackageName();
				activityName = config.MobileAppActivityName();
			}
			
		}

		System.out.println("=============================================================");	
		System.out.println("PlatForm Name    :"+platform);
		System.out.println("Device Name      :"+deviceName);
		System.out.println("PlatForm Version :"+platformVersion);
		System.out.println("=============================================================");
		if(!config.MobileRuntype().equalsIgnoreCase("cloud")){
			if(platform.equalsIgnoreCase("android")){
				System.out.println("Pacakage Name: " +packageName);
				System.out.println("Activity Name: " +activityName);
			}
			
		}
		System.out.println("============================================================");	
	}



	public void LaunchMobileApp_Real() throws Throwable {

		StartAppium(); 	   

		if(platform.equalsIgnoreCase("IOS")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			cap.setCapability(MobileCapabilityType.UDID, UDID);
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
			cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300000");
			//cap.setCapability(MobileCapabilityType.APP, "/Users/kgisl/Desktop/UICatalog.app");
			//cap.setCapability(MobileCapabilityType.APP, "/Users/kgisl/Desktop/App_Testing/Nsure Mobile.app");
			//cap.setCapability(MobileCapabilityType.APP, "/Users/kgisl/Desktop/Source/Updated/Nsure Mobile.ipa");
			cap.setCapability("bundleId", "io.ionic.lolcelife");
			//cap.setCapability("UpdatedWDAbundleId", "com.kgisl.WebDriverAgentLib");
			//cap.setCapability("derivedDataPath", "/Users/kgisl/Library/Developer/Xcode/DerivedData/WebDriverAgent-alwvnomvwrdtzoaxbbkniqrpcdpp");

			URL url =new URL(appiumURL);
			mDirver = new AppiumDriver(url,cap);			
			System.out.println("iOS App Launched suceessfully....");

		}else if (platform.equalsIgnoreCase("Android")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
			cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,packageName);
			cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,activityName);	

			URL url= new URL(service_url);			
			Thread.sleep(10000);      			        	
			mDirver = new AppiumDriver(url,cap);			
//			System.out.println(mDirver);
			Thread.sleep(10000);
			System.out.println("Android App Launched Suceessfully....");
		//	System.out.println(mDirver.getCapabilities());
		}

	}

	public void LaunchMobileApp() throws Exception {		
		StartAppium();
		List<String>deviceDts=null;
		deviceDts=config.Device();

		deviceName=deviceDts.get(0);
		platform=deviceDts.get(1);		
		platformVersion=deviceDts.get(2);					
		packageName = config.MobileAppPackageName();
		activityName =config.MobileAppActivityName();


		System.out.println("=============================================================");	
		System.out.println("PlatForm Name    :"+platform);
		System.out.println("Device Name      :"+deviceName);
		System.out.println("PlatForm Version :"+platformVersion);
		if(platform.equalsIgnoreCase("IOS")) {
			System.out.println("UDID             :"+UDID);
		}
		System.out.println("============================================================");	



		if(platform.equalsIgnoreCase("IOS")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			cap.setCapability(MobileCapabilityType.UDID, UDID);
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
			cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300000");
			//cap.setCapability(MobileCapabilityType.APP, "/Users/kgisl/Desktop/UICatalog.app");
			//cap.setCapability(MobileCapabilityType.APP, "/Users/kgisl/Desktop/App_Testing/Nsure Mobile.app");
			//cap.setCapability(MobileCapabilityType.APP, "/Users/kgisl/Desktop/Source/Updated/Nsure Mobile.ipa");
			cap.setCapability("bundleId", "com.kgisl.Nsure");
			cap.setCapability("UpdatedWDAbundleId", "com.kgisl.WebDriverAgentLib");
			cap.setCapability("derivedDataPath", "/Users/kgisl/Library/Developer/Xcode/DerivedData/WebDriverAgent-alwvnomvwrdtzoaxbbkniqrpcdpp");

			URL url =new URL(appiumURL);
			mDirver = new AppiumDriver(url,cap);			
			//driver =new IOSDriver<IOSElement>(url, cap);
			System.out.println("iOS App Launched suceessfully....");

		}else if (platform.equalsIgnoreCase("Android")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
			//cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"Appium");

			//cap.setCapability(MobileCapabilityType.APP, "/Users/kgisl/Desktop/Mobile_Testing/ApiDemos-debug.apk");
			//cap.setCapability(MobileCapabilityType.APP, "/Users/kgisl/Desktop/Nsure Mobile.apk");

			cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,packageName);
			cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,activityName);	

			URL url= new URL(service_url);

			Thread.sleep(10000);        	
			mDirver = new AppiumDriver(url,cap);			
			Thread.sleep(10000);
			System.out.println("Android App Launched Suceessfully....");

		}			
	}



	public Integer aRandomOpenPortOnAllLocalInterfaces() {
		try (
				ServerSocket socket = new ServerSocket(0);
				) {
			return socket.getLocalPort();

		} catch (IOException e) {
			throw new RuntimeException("no open ports found for bootstrap");
		}
	}


	public void StartAppium() throws InterruptedException, IOException {
		AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
		

			appiumServiceBuilder.usingDriverExecutable(new File(nodepath))
			.withAppiumJS(new File(appiumpath))
			.withIPAddress("127.0.0.1")
			.usingAnyFreePort()
			. withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/")
			//	               .withLogFile(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\log.txt"))
			;
		

		service = AppiumDriverLocalService  
				.buildService(appiumServiceBuilder);
		service.start();

		

		service_url = service.getUrl().toString();
		System.out.println("Service URL: "+service_url);
	}	

	public void RelaunchBrowser() {
		if (browser.equalsIgnoreCase("chrome")) {
			if(config.DriverAutodownload()==true){
	//			System.out.println(SeleniumManager.getInstance().getDriverPath("chromedriver"));
			}else{
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +"/Drivers/chromedriver.exe");	
			}

			ChromeOptions options = new ChromeOptions();	
			if(config.Headless()==true) {
				options.setHeadless(true);
				options.addArguments("--ignore-ssl-errors");
				options.addArguments("-ignore-certificate-errors");
				options.setPageLoadStrategy(PageLoadStrategy.EAGER);
				options.addArguments("--no-sandbox");
				options.addArguments("--remote-allow-origins=*");
			}
			options.addArguments("--ignore-ssl-errors");
			options.addArguments("-ignore-certificate-errors");
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			options.addArguments("--no-sandbox");
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);
//			driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(implictwait));
			driver.manage().window().maximize();			
		}
	}
}
