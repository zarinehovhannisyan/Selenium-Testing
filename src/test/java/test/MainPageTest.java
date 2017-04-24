package test;

import base.BaseSetup;
import base.DataManager;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page.LoginPage;
import page.MainPage;
import page.OpenedEmail;

import java.io.IOException;

public class MainPageTest extends BaseSetup {
    private final static Logger logger = Logger.getLogger(MainPageTest.class);

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = getDriver();
    }

    @DataProvider
    public Object[][] getExcelData() throws InvalidFormatException, IOException {
        DataManager read = new DataManager();
        return read.readExcel("src/test/resources/TestData.xls", "Sheet1");
    }

    /**
     * This test go to https://yahoo.com/
     * Check main page title
     * Click to mail Link
     */
    @Test(priority = 0)
    public void testVerifyHomePage() {
        logger.info("Main page test...");
        MainPage mainPage = new MainPage(driver);
        Assert.assertTrue(mainPage.verifyBasePageTitle(), "Home page title doesn't match");
        mainPage.clickMailLink();
    }

    /**
     * Create Login Page object
     * Check invalid email
     * Check invalid password
     * Login to application
     */
    @Test(priority = 1, dataProvider = "getExcelData")
    public void testVerifyUserLogin(String email, String password) {
        // Create Login Page object
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        logger.info("Sign in page test...");
        Assert.assertTrue(loginPage.loginWithInValidEmail("09dsfsdfhhhh"), "Valid email.");
        // go the next page
        loginPage.loginWithValidEmail(email);
        Assert.assertTrue(loginPage.loginWithInValidPassword("invalid"), "Valid password.");
        //login to application
        loginPage.loginWithVailidPassword(password);
    }

    @Test(priority = 2)
    public void testVerifyOpenedEmail() {
        // Creating instance of Opened Email Page
        OpenedEmail openedEmail = PageFactory.initElements(driver, OpenedEmail.class);
        logger.info("Open email Page");
        openedEmail.clickOnInbox();
        openedEmail.createDraftMessage();
        openedEmail.deleteDraftMessages();
    }
}
