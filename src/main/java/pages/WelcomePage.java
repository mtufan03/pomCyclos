package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WelcomePage {

    private WebDriver driver;

    @FindBy(id="login-link")
    private WebElement loginButton;

    @FindBy(id="register-link")
    private WebElement registerButton;

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage clickLogin(){
        loginButton.click();
        return new LoginPage(driver);
    }

//    public RegisterPage clickRegister(){
//        registerButton.click();
//        return new RegisterPage(driver);
//    }
}
