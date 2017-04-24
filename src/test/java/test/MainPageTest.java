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
    final static Logger logger = Logger.getLogger(MainPageTest.class);

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


    @Test(priority = 1)
    public void testVerifyHomePage() {
        logger.info("Main page test...");
        MainPage mainPage = new MainPage(driver);
        Assert.assertTrue(mainPage.verifyBasePageTitle(), "Home page title doesn't match");
        mainPage.clickMailLink();
    }

    @Test(priority = 2, dataProvider = "getExcelData")
    public void testVerifyUserLogin(String email, String password) {
        // Creating instance of loginPage
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        logger.info("Sign in page test...");
        Assert.assertTrue(loginPage.loginWithInValidEmail("09dsfsdfhhhh"), "Valid email.");
        loginPage.loginWithValidEmail(email);
        Assert.assertTrue(loginPage.loginWithInValidPassword("invalid"), "Valid password.");
        loginPage.loginWithVailidPassword(password);
    }

    @Test(priority = 3)
    public void testVerifyOpenedEmail() {
        // Creating instance of Opened Email Page
        OpenedEmail openedEmail = PageFactory.initElements(driver, OpenedEmail.class);
        logger.info("Open email Page");
        openedEmail.clickOnInbox();
        openedEmail.createDraftMessage();
        openedEmail.deleteDraftMessages();
    }
}
