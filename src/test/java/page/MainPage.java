package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class created for getting page title and opening  mail page
 */
public class MainPage {
    private WebDriver driver;


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean verifyBasePageTitle() {
        return getPageTitle().contains("Yahoo");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickMailLink() {
        WebElement mailLink = driver.findElement(By.linkText("Mail"));
        mailLink.click();
    }
}
