package com.paymentModule;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.BankingPage;
import pages.DashboardPage;
import pages.LoginPage;
import com.test.BaseTest;

public class PaymentTest extends BaseTest {

    private String amount;
    private String errorMessage;

    @BeforeTest
    @Parameters({"amount"})
    public void setUpAmount(String amount){
        this.amount = amount;
        this.errorMessage = errorMessage;
    }

    @Test
    public void testPaymentPositive(){
        extentLogger = report.createTest("Positive Payment");
        LoginPage lp = welcomePage.clickLogin();
        DashboardPage dp= lp.login("demo", "1234");
        BankingPage bp = dp.clickBanking();
        bp.payToUser(amount, "friend gift");
        bp.confirmPayment();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String paymentText = bp.paymentConfirmationText();
        Assert.assertEquals(paymentText, "Payment performed");
    }

    @Test
    public void paymentAmountNegative() {
        /*
        -empty, 'This field is required'
        -0 (zero), 'Amount must be a positive number.'
        -more than daily allowed (500), 'Amount must be less or equal to 500,00 IU's.'
         */
        extentLogger = report.createTest("Negative payment amount test");
        LoginPage loginPage = welcomePage.clickLogin();
        DashboardPage cdp = loginPage.login("demo", "1234");
        BankingPage bp = cdp.clickBanking();
        bp.payToUser(amount, "bills");
        System.out.println("errorMessage = " + errorMessage);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String error = bp.getInvalidAmountError();
        Assert.assertEquals(error, errorMessage);
    }

//    @DataProvider
//    public Object[][] paymentAmountNegativeData() {
//        Object[][] testData = {
//                {" ", "This field is required"},
//                {"0", "Amount must be a positive number."},
//                {"500.01", "Amount must be less or equal to 500,00 IU's."},
//        };
//        return testData;
//    }


}
