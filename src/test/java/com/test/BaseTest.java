package com.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pages.WelcomePage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver;
    public WebDriverWait wait;
    public WelcomePage welcomePage;
    public static ExtentReports report;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentTest extentLogger;



    @BeforeTest
    public void beforeTest(ITestContext ctx) throws MalformedURLException {
        // Reports setup
        report = new ExtentReports();
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/testReports/report.html");
        report.attachReporter(htmlReporter);
        htmlReporter.config().setReportName("Test Report");
        report.setSystemInfo("Tester", "Ismail");
        report.setSystemInfo("Environment", "com/test");

        // BROWSER => chrome / firefox  //Suppose someone will give a variable for browser
        // HUB_HOST => localhost / 10.0.1.3 / hostname  //And again assume that someone will pass the hub variable, what if no pass those information?
        //So, we will create a default
        String host = "localhost";
        DesiredCapabilities dc;

        if (System.getProperty("BROWSER") != null &&
                System.getProperty("BROWSER").equalsIgnoreCase("firefox")) {
            dc = DesiredCapabilities.firefox();
        } else {
            dc = DesiredCapabilities.chrome();
        }

        if (System.getProperty("HUB_HOST") != null) {
            host = System.getProperty("HUB_HOST");
        }

        String testName = ctx.getCurrentXmlTest().getName();

        String completeUrl = "http://" + host + ":4444/wd/hub";
        dc.setCapability("name", testName);
        this.driver = new RemoteWebDriver(new URL(completeUrl), dc); //first of the parameters looks for where to find the remote driver
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        report.flush();
    }

    @BeforeMethod
    public void setUp() {
        driver.get("https://demo.cyclos.org/ui/home");
        welcomePage = new WelcomePage(driver);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentLogger.fail(result.getName());
            String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File file = ts.getScreenshotAs(OutputType.FILE);
            String target = System.getProperty("user.dir") + "/testReports/" + result.getName() + date + ".png";
            File finalDestination = new File(target);

            FileUtils.copyFile(file, finalDestination);
            extentLogger.addScreenCaptureFromPath(result.getName()+date+".png");
            extentLogger.fail(result.getThrowable());
        }
        driver.quit();

    }

}
