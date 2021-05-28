package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BankingPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath="//*[@class='nav-item-text' and contains(text(), 'Payment to user')]")
    private WebElement payUserLink;

    @FindBy(css=".btn.btn-icon.ml-2")
    private WebElement payeesList;

    @FindBy(linkText = "Active Walking")
    private WebElement activeWalking;

    @FindBy(xpath = "//*[@placeholder='0,00']")
    private WebElement amount;

    @FindBy(xpath = "//*[@title='Pay now']")
    private WebElement scheduling;

    @FindBy(linkText = "Pay now")
    private WebElement payNow;

    @FindBy(css = ".form-control")
    private WebElement description;

    @FindBy(css = ".d-inline-block.button")
    private WebElement nextButton;

    @FindBy(xpath = "//*[contains(text(), 'Confirm')]")
    private WebElement confirmButton;

    @FindBy(css = ".title-text.text-truncate.flex-grow-1.d-flex.align-items-center")
    private WebElement paymentConfirmation;

    @FindBy(css = ".invalid-feedback")
    private WebElement errorMessage;

    public BankingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public void payToUser(String amount, String description){
        payUserLink.click();
        payeesList.click();
        activeWalking.click();
        wait.until(ExpectedConditions.elementToBeClickable(this.amount)).sendKeys(amount);
        scheduling.click();
        payNow.click();
        this.description.sendKeys(description);
        nextButton.click();
    }

    public void confirmPayment(){
        confirmButton.click();
    }

    public String paymentConfirmationText(){
        return wait.until(ExpectedConditions.visibilityOf(paymentConfirmation)).getText();
    }

    public String getInvalidAmountError(){
        return errorMessage.getText();
    }
}
