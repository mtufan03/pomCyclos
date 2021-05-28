package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "menu_banking")
    private WebElement bankingLink;

    @FindBy(xpath = "//*[@href='/users/contacts']")
    private WebElement contactsLink;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public boolean isBankingVisible() {
        return bankingLink.isDisplayed();
    }

    public BankingPage clickBanking() {
        wait.until(ExpectedConditions.elementToBeClickable(bankingLink)).click();
        return new BankingPage(driver);
    }


}
