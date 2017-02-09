package com.Centurylink.PWCM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserClass {

	static WebDriver driver;
	
    static String chromepath=".\\lib\\chromedriver.exe";
	static String iepath=".\\lib\\IEDriverServer64.exe";	
	
	public static WebDriver browserSelection(String browserName) throws InterruptedException
	{
	 if(browserName.equalsIgnoreCase("firefox"))
	  {
	   driver=new FirefoxDriver();
	  }
	 else if(browserName.equalsIgnoreCase("chrome"))
	  {
	   System.setProperty("webdriver.chrome.driver",chromepath);
	   ChromeOptions options = new ChromeOptions();
	   options.addArguments("--disable-extensions");
	   driver = new ChromeDriver(options);
	  }
	 else if(browserName.equalsIgnoreCase("ie"))
	  {
	     System.setProperty("webdriver.ie.driver",iepath);
	     DesiredCapabilities cap = new DesiredCapabilities().internetExplorer();//.internetExplorer();
         cap.setCapability("ignoreZoomSetting", true);
         cap.setCapability("ie.forceCreateProcessApi", true);
		 cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		 cap.setJavascriptEnabled(true);
		 cap.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
		 cap.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
		 cap.setCapability("ie.browserCommandLineSwitches", "-private");
		//cap.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, URL);
		// cap.internetExplorer().setCapability("ignoreProtectedModeSettings", true);
		// cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		 cap.setCapability("requireWindowFocus", true);
		// cap.setCapability("enablePersistentHover", false);
		 cap.setCapability("ie.ensureCleanSession", true);
		// cap.setCapability("nativeEvents", false);
		 driver = new InternetExplorerDriver(cap);
	  }
	 return driver;
	 }

}

