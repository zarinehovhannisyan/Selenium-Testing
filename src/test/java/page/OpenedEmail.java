package page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class OpenedEmail {
    final static Logger logger = Logger.getLogger(OpenedEmail.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    private WebElement inbox;
    //private WebElement emptyMessage;
    private WebElement compose;

    public OpenedEmail(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
    }


    public void clickOnInbox() {
        logger.info("Click on inbox ul ...");
        inbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Inbox")));
        inbox.click();
    }


    public void createDraftMessage() {
        clickOnCompose();
        setEmail();
        setSubject();
        clickOnClose();
        clickOnInbox();
    }

    public void deleteDraftMessages() {
        clickOnDraft();
        clickOnSelectAll();
        clickOnAll();
        clickOnDelete();
    }

    private void clickOnCompose() {
        compose = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='icon-text']")));
        compose.click();
    }

    private void setEmail() {
        WebElement to = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("to-field")));
        to.sendKeys("test@yahoo.com");
    }

    private void setSubject() {
        WebElement subject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("subject-field")));
        subject.sendKeys("Test");
    }

    private void clickOnClose() {
        WebElement close = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='icon icon-close']")));
        close.click();
    }

    private void clickOnDraft() {
        WebElement draft = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-drafts']")));
        draft.click();
    }

    private void clickOnSelectAll() {
        WebElement selectAll = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-select-dd")));
        selectAll.click();
    }

    private void clickOnAll() {
        WebElement allLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("All")));
        allLink.click();
    }

    private void clickOnDelete() {
        WebElement delete_btn = driver.findElement(By.id("btn-delete"));
        delete_btn.click();
        if (driver.findElements(By.id("okModalOverlay")).size() > 0) {
            WebElement okBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okModalOverlay")));
            okBtn.click();
        }
    }
}
