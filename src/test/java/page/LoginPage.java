package page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {
    // Created Logger instance  for logging
    final static Logger logger = Logger.getLogger(LoginPage.class);
    // WebDriver object
    private WebDriver driver;
    // WebDriverWait object
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        //Set web driver
        this.driver = driver;
        // Create and initialize wait object
        wait = new WebDriverWait(driver, 20);
    }

    //Using FindBy for locating elements
    @FindBy(id = "login-username")
    private WebElement email;

    @FindBy(id = "login-signin")
    private WebElement next;

    @FindBy(id = "signIn")
    private WebElement signIn;

    private WebElement password;

    private void typeEmail(String text) {
        email.clear();
        email.sendKeys(text);
    }

    private void typePassword(String text) {
        password.clear();
        password.sendKeys(text);
    }

    public void clickSubmit() {
        next.click();
    }

    public boolean loginWithInValidPassword(String inValidPassword) {
        password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-passwd")));
        typePassword(inValidPassword);
        clickSubmit();
        return getErrorMessage().contains("Invalid password. Please try again.");
    }

    public boolean loginWithInValidEmail(String inValidEmail) {
        typeEmail(inValidEmail);
        clickSubmit();
        return getErrorMessage().contains("Sorry, we don't recognize this email.");
    }

    public void loginWithVailidPassword(String validPassword) {
        typePassword(validPassword);
        clickSubmit();
    }

    public void loginWithValidEmail(String email) {
        typeEmail(email);
        clickSubmit();
    }

    /**
     * Get error message for invalid email or password
     */

    public String getErrorMessage() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='mbr-login-error']")));
        logger.info("Get error message");
        return errorMsg.getText();
    }
}
