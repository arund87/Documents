package com.Centurylink.PWCM;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class pwcmlibrary {

	HSSFWorkbook worbk;
	HSSFSheet sheet1;
	public String reportpath=".\\Reporting\\Advreportpath.html";
	public String injectpath=".\\src\\main\\resources\\Injectfile.xls";
	public String Attachpath="C:\\git\\pwcm-auto-test";
	public String username="admin";
	public String password="admin";
	//public String baseurl="http://pwcmint:4502/content/pwcm-first-page/home.html";
	//public String toolsurl="http://pwcmint:4502/content/pwcm-first-page/test-page.html";
	public String baseurl="http://pwcmqa3:4503/content/partner/home.html";
	public String toolsurl="http://pwcmqa3:4503/content/partner/test-page.html";
	public String organizationurl="http://pwcmqa3:4503/content/ae/organization.html";
	public String adminpageurl="http://pwcmqa3:4503/content/ae/action.html";
	public String produsername="AWQI";
	public String prodpassword="Partner2015";
	public String intusername="bcej";
	public String intpassword="luke2122";
	public String autoitpath="C:\\git\\pwcm-auto-test\\lib\\Autoit";
	public String pdfpath="C:\\git\\pwcm-auto-test\\PDF";
	
	//Login function
	public void login(WebDriver driver)
	{
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("submit-button")).click();
		return;
	}
	
	//Production Login
	public void prodlogin(WebDriver driver)
	{
		driver.findElement(By.name("logon")).sendKeys(produsername);
		driver.findElement(By.name("password")).sendKeys(prodpassword);
		driver.findElement(By.xpath("//input[@type='image']")).click();
		return;
	}
	
	//INT Login
		public void intlogin(WebDriver driver)
		{
			driver.findElement(By.name("logon")).sendKeys(intusername);
			driver.findElement(By.name("password")).sendKeys(intpassword);
			driver.findElement(By.xpath("//input[@type='image']")).click();
			return;
		}
		
		//Method for drop down value check
		public void dropdowncheck(WebDriver driver, WebElement DDElement, String[] values, ExtentTest test)
		 {
		  Select selectDD = new Select(DDElement);
		  List<WebElement> optionsvalues = selectDD.getOptions();
		// make sure you found the right number of elements
		    if (values.length != optionsvalues.size()) {
		      test.log(LogStatus.WARNING, "Count of values in the drop down does not match the expected count");
		    }
		// make sure that the value of every <option> element equals the expected value
		   for (int i = 0; i < values.length; i++) {
		   String optionValue = optionsvalues.get(i).getText().replaceAll("\\s", "");
		   if (!optionValue.equals(values[i].replaceAll("\\s", ""))) {
		     test.log(LogStatus.WARNING, "Drop Down value is missing: " + optionValue);
		   }
		   }
		 return;
		 }
	
	    //To verify the Numerical inputBox Empty
		public void inputempty(WebDriver driver, WebElement inputBox, ExtentTest test)
		{
			String textInsideInputBox = inputBox.getAttribute("value");
			if(textInsideInputBox.isEmpty())
			{
			  test.log(LogStatus.PASS, "Numerical inputBox field is empty");
			}else {
				test.log(LogStatus.FAIL, "Numerical inputBox field is not empty");
				  }	
			return;
		}
		
		//Move cursor to active element inputBox
		public void movetoactiveelement(WebDriver driver, WebElement appelement, String inputtext, ExtentTest test)
		{
			WebElement currentElement = driver.switchTo().activeElement();
			if(currentElement.equals(appelement))
			{
			currentElement.sendKeys(inputtext);
			}else{
				test.log(LogStatus.FAIL, "Mandatory * not working for "+appelement);
			}
			return;			
		}
		
		//File attachment method
		public void fileupload(WebDriver driver,String filescript) throws Exception 
		{
			for(int a=1;a<=20;a++)
			{
			WebElement button=driver.findElement(By.xpath("//button[@class='browse btn btn-primary input-lg']"));
			button.click();
			Thread.sleep(3000);
			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			Runtime.getRuntime().exec(autoitpath+filescript+" "+pdfpath+"\\"+"0"+a+".pdf");
			Thread.sleep(3000);
			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			}
			return;
		}
		
		
		//locate the Excel file
		public void Excelpath(String path, int sheetnum)
		{
		try {
			FileInputStream fis=new FileInputStream(path);
			worbk=new HSSFWorkbook(fis);
			sheet1=worbk.getSheetAt(sheetnum);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		}
		
		//Get the text value from Excel 
		public String Exceldata(int rownum,int column) 
		{
			String daata1=sheet1.getRow(rownum).getCell(column).getStringCellValue();
			
			return daata1;
		}
		
		//Get the row count from Excel 
		public int Excelcount(){
			int rows = sheet1.getLastRowNum();
			rows=rows+1;
			return rows;
			}
		
	    //Method for Radio buttons
		  public void rdclick(WebDriver driver, String name, String rdselect){
				List<WebElement> CheckBox=driver.findElements(By.name(name));
				int iSize = CheckBox.size();
				for(int i=0; i < iSize ; i++ )
				      {
					   String sValue = CheckBox.get(i).getAttribute("value");
				          if (sValue.equalsIgnoreCase(rdselect))
				          {
				    	  CheckBox.get(i).click();
				          break;
				          }
				       }
				     return;
			    }
		  //Method for Screenshots
		  public void captureScreenshot(WebDriver driver, String Snap){
			  try {
			  TakesScreenshot clk=(TakesScreenshot)driver;
			  File source=clk.getScreenshotAs(OutputType.FILE);
			  FileUtils.copyFile(source,new File("./Screenshots/"+Snap+".jpg"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			  
		  }
		  
}
	

