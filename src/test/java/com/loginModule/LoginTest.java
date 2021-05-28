package com.loginModule;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import com.test.BaseTest;

public class LoginTest extends BaseTest {

    private String username;
    private String password;
    private String errorMessage;

    @BeforeTest
    @Parameters({"username", "password", "errorMessage"})
    public void setUpCredentials(String username, String password, String errorMessage) {
        this.username = username;
        this.password = password;
        this.errorMessage = errorMessage;
    }

    @Test
    public void loginPositive() {
        extentLogger = report.createTest("Positive Login");
        LoginPage lp = welcomePage.clickLogin();
        //LoginPage lp = new LoginPage(driver);
        DashboardPage dp = lp.login(username, password);
        Assert.assertTrue(dp.isBankingVisible());
    }

    @Test
    public void loginEmptyUsername() {
        extentLogger = report.createTest("Negative Login - username");
        LoginPage lp = welcomePage.clickLogin();
        //LoginPage lp = new LoginPage(driver);
        lp.login(username, password);
        Assert.assertEquals(lp.getUsernameErrorText(), errorMessage);
    }

    @Test
    public void loginEmptyPassword() {
        extentLogger = report.createTest("Negative Login - password");
        LoginPage lp = welcomePage.clickLogin();
        //LoginPage lp = new LoginPage(driver);
        lp.login(username, password);
        Assert.assertEquals(lp.getPasswordErrorText(), errorMessage);
    }

    @Test
    public void loginUsernamePasswordNoMatch() {
        extentLogger = report.createTest("Negative Login - Username/Password does not match");
        LoginPage lp = welcomePage.clickLogin();
        //LoginPage lp = new LoginPage(driver);
        lp.login(username, password);
        Assert.assertEquals(lp.getincorrectUsrPassErrorText(), errorMessage);
    }
}
