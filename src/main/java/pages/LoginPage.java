package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//*[@class='invalid-feedback'])[1]")
    private WebElement errorField;

    @FindBy(css = ".notification-message")
    private WebElement incorrectUsrPass;

    @FindBy(xpath = "//*[@placeholder='User']")
    private WebElement username;

    @FindBy(xpath = "//*[@placeholder='Password']")
    private WebElement password;

    @FindBy(css = ".btn.d-flex")
    private WebElement submitButton;

    public void enterUsername(String username) {
        this.username.sendKeys(username);
    }

    public void enterPassword(String password) {
        this.password.sendKeys(password);
    }

    public DashboardPage clickSubmit() {
        submitButton.click();
        return new DashboardPage(driver);
    }

    public DashboardPage login(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        submitButton.click();
        return new DashboardPage(driver);
    }

    public String getUsernameErrorText() {
        return wait.until(ExpectedConditions.visibilityOf(errorField)).getText();
    }

    public String getPasswordErrorText() {
        return wait.until(ExpectedConditions.visibilityOf(errorField)).getText();
    }

    public String getincorrectUsrPassErrorText() {
        return wait.until(ExpectedConditions.visibilityOf(incorrectUsrPass)).getText();
    }
}
