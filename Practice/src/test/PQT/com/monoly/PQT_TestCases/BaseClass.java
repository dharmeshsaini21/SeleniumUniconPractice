package com.monoly.PQT_TestCases;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.monoly.PQT_Utilities.ReadConfig;



public class BaseClass {


	ReadConfig readconfig=new ReadConfig();

	public String baseURL=readconfig.getApplicationURL();
	public String username=readconfig.getUsername();
	public String password=readconfig.getPassword();
	public static WebDriver driver;
	public static Logger logger;
	public int code; 

	@Parameters("browser")
	@BeforeMethod
	public void setup(@Optional("Chrome") String br)
	{			
		logger = Logger.getLogger("Monoly");
		PropertyConfigurator.configure("Log4j.properties");


		if(br.equals("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver",readconfig.getChromePath());
			driver=new ChromeDriver();
			logger.info("Chrome Browser Open");
		}
		else if(br.equals("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver",readconfig.getFirefoxPath());
			driver = new FirefoxDriver();
			logger.info("FireFox Browser Open");
		}
		else if(br.equals("IE"))
		{
			System.setProperty("webdriver.ie.driver",readconfig.getIEPath());
			driver = new InternetExplorerDriver();
			logger.info("IE Browser Open");
		}

		else if(br.equals("Edge"))
		{
			System.setProperty("webdriver.ie.driver",readconfig.edgePath());
			driver = new EdgeDriver();
			logger.info("Edge Browser Open");
		}
		
		else if(br.equals("Safari"))
		{
			driver = new SafariDriver();
			logger.info("Safari Browser Open");
		}

		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get(baseURL);
		driver.manage().window().maximize();
		logger.info("URL entered");



	}

	@AfterMethod
	public void tearDown()
	{
		driver.close();
		logger.info("Browser Closed");
	}


	public static void captureScreen(String name, String testCaseName ) throws IOException
	{

		TakesScreenshot evidence = (TakesScreenshot) driver;
		File source = evidence.getScreenshotAs(OutputType.FILE);
		File target = new File("./Evidences//" + name + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");



	}

	
}



