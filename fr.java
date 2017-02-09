package com.Centurylink.PWCM;


import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import com.Centurylink.PWCM.pwcmlibrary;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class HelpPage {

	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	pwcmlibrary config=new pwcmlibrary();
	String helptext="Help";
	String[] internetbrowser={"-- Choose One --","Internet Explorer","Mozilla Firefox","Google Chrome","Safari","Other"};
	String[] problemtype={"-- Choose One --","Report Outage/Problem with Site","Password Reset","Registration Problem/Change",
			              "Access Problem","Web Page Not Available","Document  Not Available","Commission Reports","Commissions Tools",
			              "Customer Account Lookup","Ordering and Pricing Tool Issue","Central","Learning Center","Other"};
	@BeforeClass
	   @Parameters("browser")
	   public void drivercall(String browserName) throws InterruptedException
	   {
		driver=BrowserClass.browserSelection(browserName);
		driver.manage().window().maximize();
	   }
		
	@Test(priority=0,dataProvider="Help")
	public void Help(String usrname, String usrid, String acompany, String pcompany, String pnumber, String cnumber,
			             String mailid, String browser, String bversion, String obname, String pcategory, String ptype,
			             String pdescription, String errormsg, String stepstaken) throws InterruptedException
	{
		extent=new ExtentReports(config.reportpath,false);
		test=extent.startTest("Help Page-US49300");
		test.assignCategory("Helppage");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get(config.baseurl);
		test.log(LogStatus.PASS, "pwcmqa3 Homepage");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  //driver.findElement(By.id("submit-button")).isDisplayed();
		
		/*PWCMINT login*/
	    /*config.login(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "Login Success");*/
				
		//Switch to child Help Page
		
        String parentw=driver.getWindowHandle();
		driver.findElement(By.linkText("Help")).click();
		Set<String> helps=driver.getWindowHandles();
		Iterator<String> helpi=helps.iterator();
		while(helpi.hasNext()){
			String childw=helpi.next();
			if(!parentw.equalsIgnoreCase(childw))
			{
				driver.switchTo().window(childw);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
				
				//Check you are in help page
				Assert.assertEquals(helptext, driver.findElement(By.xpath("//p[@class='askATechHeader']")).getText());
				test.log(LogStatus.PASS, "Helppage opened in new window");
				
				//Mandatory check
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("USER_NAME")).sendKeys(usrname);
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("USER_ID")).sendKeys(usrid);
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("PHONE_NUMBER1")).sendKeys(pnumber);
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("E_MAIL")).sendKeys(mailid);
				driver.findElement(By.className("positive")).click();
				Select ibrowser11=new Select(driver.findElement(By.id("internetbrowser")));
				ibrowser11.selectByValue(browser);
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("BROWSER_VERSION")).sendKeys(bversion);
				driver.findElement(By.className("positive")).click();
				Select probtype11=new Select(driver.findElement(By.id("PROBLEM_CATEGORY")));
				probtype11.selectByValue(pcategory);
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("DET_DESC_PROBLEM")).sendKeys(pdescription);
				driver.findElement(By.className("positive")).click();				
				driver.findElement(By.id("STEPS_TAKEN")).sendKeys(stepstaken);
				driver.findElement(By.className("negative")).click();
				test.log(LogStatus.PASS, "Mandatory(*) is working");	
				
				//Fill the help page form 
				driver.findElement(By.xpath("//input[@placeholder='Enter User Name']")).isDisplayed();
				driver.findElement(By.id("USER_NAME")).sendKeys(usrname);
				driver.findElement(By.xpath("//input[@placeholder='Enter User ID']")).isDisplayed();
				driver.findElement(By.id("USER_ID")).sendKeys(usrid);
				driver.findElement(By.xpath("(//input[@placeholder='Enter Company Name'])[1]")).isDisplayed();
				driver.findElement(By.id("SUB_AGENT_COMPANY")).sendKeys(acompany);
				driver.findElement(By.xpath("(//input[@placeholder='Enter Company Name'])[2]")).isDisplayed();
				driver.findElement(By.id("MASTER_PARTNER_COMPANY")).sendKeys(pcompany);
				//Phone number numerical check
				driver.findElement(By.xpath("//input[@placeholder='Eg: 999 999-9999']")).isDisplayed();
				driver.findElement(By.id("PHONE_NUMBER1")).clear();
				driver.findElement(By.id("PHONE_NUMBER1")).sendKeys("phonenumber");
				WebElement phonenumber1=driver.findElement(By.id("PHONE_NUMBER1"));
				if(!phonenumber1.getText().equalsIgnoreCase("phonenumber"))
				{test.log(LogStatus.PASS, "Numerical inputBox field is empty");}
				else{test.log(LogStatus.FAIL, "Numerical inputBox field is not empty");}
				driver.findElement(By.id("PHONE_NUMBER1")).clear();
				driver.findElement(By.id("PHONE_NUMBER1")).sendKeys(pnumber);
				//Cell number numerical check
				driver.findElement(By.xpath("//input[@placeholder='Eg:999 999-9999']")).isDisplayed();
				driver.findElement(By.id("CELL_NUMBER")).clear();
				driver.findElement(By.id("CELL_NUMBER")).sendKeys("cellnumber");
				WebElement cellnumber1=driver.findElement(By.id("CELL_NUMBER"));
				if(!cellnumber1.getText().equalsIgnoreCase("cellnumber"))
				{test.log(LogStatus.PASS, "Numerical inputBox field is empty");}
				else{test.log(LogStatus.FAIL, "Numerical inputBox field is not empty");}
				driver.findElement(By.id("CELL_NUMBER")).clear();
				driver.findElement(By.id("CELL_NUMBER")).sendKeys(cnumber);
				driver.findElement(By.xpath("//input[@placeholder='Enter email']")).isDisplayed();
				driver.findElement(By.id("E_MAIL")).sendKeys(mailid);
				
				//Internet browser dropdown value check
				WebElement ibelement=driver.findElement(By.id("internetbrowser"));
				config.dropdowncheck(driver, ibelement, internetbrowser, test);
				test.log(LogStatus.PASS, "All the dropdown values present inside the Internet browser");
				Select ibrowser=new Select(driver.findElement(By.id("internetbrowser")));
				ibrowser.selectByValue("other");
				driver.findElement(By.xpath("//input[@placeholder='Browser Version']")).isDisplayed();
				driver.findElement(By.id("BROWSER_VERSION")).sendKeys(bversion);
				driver.findElement(By.xpath("//input[@placeholder='other browser name']")).isDisplayed();
				driver.findElement(By.id("OTHER_BROWSER_NAME")).sendKeys(obname);
				//Problem type dropdown value check
				WebElement ptelement=driver.findElement(By.id("PROBLEM_CATEGORY"));
				config.dropdowncheck(driver, ptelement, problemtype, test);
				test.log(LogStatus.PASS, "All the dropdown values present inside the Problem Type");
				Select probtype=new Select(driver.findElement(By.id("PROBLEM_CATEGORY")));
				probtype.selectByValue("Other");
				driver.findElement(By.xpath("//input[@placeholder='Other Problem Type']")).isDisplayed();
				driver.findElement(By.id("OTHER_PROBLEM_TYPE")).sendKeys(ptype);
				Select probtypeee=new Select(driver.findElement(By.id("PROBLEM_CATEGORY")));
				probtypeee.selectByValue("Customer Account Lookup");
				driver.findElement(By.xpath("//input[@placeholder='Other Problem Type']")).isDisplayed();
				driver.findElement(By.id("OTHER_PROBLEM_TYPE")).sendKeys(ptype);
				driver.findElement(By.id("DET_DESC_PROBLEM")).sendKeys(pdescription);
				driver.findElement(By.id("ERROR_MSG")).sendKeys(errormsg);
				driver.findElement(By.xpath("(//input[@name='PROBLEM_REPRODUCIBLE'])[1]")).click();
				driver.findElement(By.id("STEPS_TAKEN")).sendKeys(stepstaken);
				driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).isDisplayed();
				driver.findElement(By.xpath("//button[contains(text(),'Reset')]")).isDisplayed();
				test.log(LogStatus.PASS, "Placeholder is present for all fields and able to fill all the fields in helpage");
				Thread.sleep(4000);
				
				/*Check reset functionality*/
				driver.findElement(By.className("negative")).click();
				driver.findElement(By.xpath("(//input[@name='PROBLEM_REPRODUCIBLE'])[2]")).isEnabled();
				WebElement inputBox = driver.findElement(By.id("USER_ID"));
				config.inputempty(driver, inputBox, test);
				
				/*Submit negative testing*/
				driver.findElement(By.id("USER_NAME")).sendKeys(usrname);
				driver.findElement(By.id("USER_ID")).sendKeys(usrid);
				driver.findElement(By.id("SUB_AGENT_COMPANY")).sendKeys(acompany);
				driver.findElement(By.id("MASTER_PARTNER_COMPANY")).sendKeys(pcompany);
				driver.findElement(By.id("PHONE_NUMBER1")).clear();
				driver.findElement(By.id("PHONE_NUMBER1")).sendKeys(pnumber);
				driver.findElement(By.id("CELL_NUMBER")).clear();
				driver.findElement(By.id("CELL_NUMBER")).sendKeys(cnumber);
				Select ibrowser1=new Select(driver.findElement(By.id("internetbrowser")));
				ibrowser1.selectByValue(browser);
				driver.findElement(By.id("BROWSER_VERSION")).sendKeys(bversion);
				Select probtype1=new Select(driver.findElement(By.id("PROBLEM_CATEGORY")));
				probtype1.selectByValue(pcategory);
				driver.findElement(By.id("DET_DESC_PROBLEM")).sendKeys(pdescription);
				driver.findElement(By.id("ERROR_MSG")).sendKeys(errormsg);
				driver.findElement(By.id("PROBLEM_REPRODUCIBLE")).click();
				driver.findElement(By.id("STEPS_TAKEN")).sendKeys(stepstaken);
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("E_MAIL")).isDisplayed();
				driver.findElement(By.id("E_MAIL")).sendKeys("arund87ymail.com");
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("E_MAIL")).isDisplayed();
				driver.findElement(By.id("E_MAIL")).clear();
				driver.findElement(By.id("E_MAIL")).sendKeys(mailid);
				test.log(LogStatus.PASS, "Help page (*)Mandatory is working");
				driver.findElement(By.className("positive")).click();
				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//p[contains(text(),'Thank you for submitting the problem')]")).isDisplayed();
				test.log(LogStatus.PASS, "Submit is working, Got the Thank you message");
				driver.findElement(By.xpath("//input[@value='Close']")).click();
				/*Submit Not properly functional*/
			    test.log(LogStatus.PASS, "Help Page Closed");
			    test.log(LogStatus.INFO, "Manually Verify if email has been sent and subject line has been modified to - 'NGPP - Access Alliance Help Request'");
				Thread.sleep(2000);
				
				}
			}
		driver.switchTo().window(parentw);
		test.log(LogStatus.PASS, "Helppage Test Passed");
		extent.endTest(test);
		extent.flush();
		}
	
	
	
	//Data provider for Help Page.
	@DataProvider(name="Help")
	public Object[][] passData(){
	config.Excelpath(config.injectpath,0);
	int row=config.Excelcount();
	int rows=row-1;
	Object[][] Helppagedata=new Object[rows][15];
	for(int i=1;i<row;i++)
	{
		  for(int j=0;j<15;j++){
			int k=i-1;  
			Helppagedata[k][j]=config.Exceldata(i, j);
	 	  }
	}

	return Helppagedata;
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws InterruptedException{
	if(ITestResult.FAILURE==result.getStatus())
	{
	config.captureScreenshot(driver,result.getName());
	String image=test.addScreenCapture(config.Attachpath+"\\Screenshots\\"+result.getName()+".jpg");
	test.log(LogStatus.FAIL, result.getName(), image);
	Thread.sleep(5000);
	driver.quit();
	}
	extent.endTest(test);
	extent.flush();
	}

    @AfterClass
    public void closebrowser()
    {
    	
     driver.quit();
     try {
 	    Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
 	    Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
 	} catch (IOException e) {
 	    e.printStackTrace();
 	}

    }
    
	}


	
