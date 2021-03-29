package extent.extent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class NopCommercetest {

	public WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	@BeforeTest
	public void setExtent() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myReport.html");
		htmlReporter.config().setDocumentTitle("Automation report");
		htmlReporter.config().setReportName("Functional Report");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Hostname", "LocalHost");
		extent.setSystemInfo("OS", "windows10");
        extent.setSystemInfo("TesterName", "Vijaya");		
	}
	@AfterTest
	public void endReport() {
		extent.flush();
	}
	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "C:\\Vijaya\\GCATAccount\\Softwares\\drivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("http://demo.nopcommerce.com/");
	}
    @Test
    public void noCOmmerceTitle() {
    	test = extent.createTest("noCommerceTitle");
    	String title= driver.getTitle();
    	System.out.println(title);
    	Assert.assertEquals(title, "nopCommerce demoq store");
    }
    @Test
    public void noCommerceLogo() {
    	test = extent.createTest("noCommerceLogo");
    	
    	Boolean status =driver.findElement(By.xpath("//img[@alt='nopCommerce demo store']")).isDisplayed();
    	Assert.assertTrue(status);
    }
    @Test
    public void noCommerceLogintest() {
    	test=extent.createTest("noCommerceLogo");
    	test.createNode("Login with Valid input");
    	Assert.assertTrue(true);
    	
    	test.createNode("Login with invalid input");
    	Assert.assertTrue(true);
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
    	if(result.getStatus() == ITestResult.FAILURE) {
    		test.log(Status.FAIL, "Test case failed is " + result.getName());
    		test.log(Status.FAIL, "Test Case failed is "+result.getThrowable());
    		String screenshotPath = NopCommercetest.getScreenshotAs(driver,result.getName());
    		test.addScreenCaptureFromPath(screenshotPath);
    	}else if(result.getStatus() == ITestResult.SKIP) {
    		test.log(Status.SKIP, "Test Case SkIPPES Is: "+result.getName());
    	}else if(result.getStatus() == ITestResult.SUCCESS) {
    		test.log(Status.PASS, "Test Case PASSED Is: "+result.getName());
  
    	}
    }
	private static String getScreenshotAs(WebDriver driver, String screenshotName) throws IOException {
		
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		
		String destination = System.getProperty("user.dir")+ "/Screenshot" + screenshotName +dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source,finalDestination);
		
		
		
		return destination;
	}
}
