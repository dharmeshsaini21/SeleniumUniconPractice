package com.monoly.PQT_Utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.ITestAnnotation;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.monoly.PQT_TestCases.BaseClass;


public class ExtentReport extends TestListenerAdapter {


	//implements IAnnotationTransformer

	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest extentTest;
	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp


	public void onStart(ITestContext testContext)
	{

		String repName="Monoly- "+timeStamp+".html";
		//specify location of the report
		htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+ "/test-output/"+repName);
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+ "/extent-config.xml");

		extent=new ExtentReports();

		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("User","Super Admin");
		extent.setSystemInfo("Environemnt","QA");
		extent.setSystemInfo("OA name","Dharmesh");


		htmlReporter.config().setDocumentTitle("Monoly"); // Tile of report
		htmlReporter.config().setReportName("Monoly UI Test Automation Report"); // name of the report
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); //location of the chart
		htmlReporter.config().setTheme(Theme.DARK);
	}

	public void onTestSuccess(ITestResult tr)
	{
		// create new entry in the report
		extentTest=extent.createTest(tr.getName());

		// send the passed information to the report with GREEN color highlighted
		extentTest.log(Status.PASS,MarkupHelper.createLabel(tr.getName(),ExtentColor.GREEN)); 

	}

	public void onTestFailure(ITestResult tr)
	{
		
		String name = tr.getName() + timeStamp;
		String testCaseName = tr.getName();
		String status = "Fail";
		
		// create new entry in the report
		extentTest=extent.createTest(tr.getName());
		
		// send the passed information to the report with GREEN color highlighted
		extentTest.log(Status.FAIL,MarkupHelper.createLabel(tr.getName(),ExtentColor.RED)); 
				
		// Calling the BaseClass method take Screen Shots.
		try {
			BaseClass.captureScreen(name, testCaseName);
			extentTest.error("Test case " +testCaseName +" is "+status +"==>>> Evidence is taken.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String screenshotPath=System.getProperty("user.dir")+"\\ScreenShots\\"+name+".png";

		File f = new File(screenshotPath); 

		if(f.exists())
		{
			try {
				extentTest.fail("Screenshot is below:" + extentTest.addScreenCaptureFromPath(screenshotPath));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}



	}

	public void onTestSkipped(ITestResult tr)
	{
		extentTest=extent.createTest(tr.getName()); // create new entry in the report
		extentTest.log(Status.SKIP,MarkupHelper.createLabel(tr.getName(),ExtentColor.ORANGE));

	}

	public void onFinish(ITestContext testContext)
	{
		extent.flush();
	}
	
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		 annotation.setRetryAnalyzer(RetryTestCaseOnFailure.class);
		
	}

}



