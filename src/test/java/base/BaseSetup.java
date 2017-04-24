package base;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

/**
 * This class created for general functionality of web browser and a web driver
 */
public class BaseSetup {
    // Get driver path
    private static final String pathToTheResourcesDir = "src\\test\\resources\\drivers\\";
    // Created Logger instance  for logging
    final static Logger logger = Logger.getLogger(BaseSetup.class);
    // Created static variables for browsers
    public static final int CHROME = 1;
    public static final int FIREFOX = 2;
    // WebDriver object
    private WebDriver driver;

    // Get web driver
    public WebDriver getDriver() {
        return driver;
    }

    // Set driver according browser type
    private void setDriver(int browserType, String appURL) {
        switch (browserType) {
            case CHROME:
                driver = initChromeDriver(appURL);
                break;
            case FIREFOX:
                driver = initFirefoxDriver(appURL);
                break;
            default:
                driver = initFirefoxDriver(appURL);
        }
    }

    // Initialized Chrome driver
    private static WebDriver initChromeDriver(String appURL) {
        // Add logger info
        logger.info("Openning google chrome with new tab..");
        // Add chrome driver path in the system
        System.setProperty("webdriver.chrome.driver", pathToTheResourcesDir + "chromedriver.exe");
        // Set browser options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--test-type");
        options.addArguments("--disable-extensions");
        // Get Chrome driver
        WebDriver driver = new ChromeDriver(options);
        // Maximize browser window
        driver.manage().window().maximize();
        // Open browser with given URL
        driver.navigate().to(appURL);
        return driver;
    }

    // Initialized Firefox driver
    private static WebDriver initFirefoxDriver(String appURL) {
        // Add logger info
        logger.info("Openning Firefox browser..");
        // Get Firefox driver
        WebDriver driver = new FirefoxDriver();
        // Maximize browser window
        driver.manage().window().maximize();
        // Open browser with given URL
        driver.navigate().to(appURL);
        return driver;
    }

    /**
     * Get parameters  from 'test.xml' when class starts to run
     */
    @Parameters({"browserType", "appURL"})
    @BeforeClass
    // Initialized this class
    public void initializeBaseSetup(int browserType, String appURL) {
        try {
            setDriver(browserType, appURL);
            // Wait for browser loading
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error....." + e.getStackTrace());
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        // Close browser
        cleanUp();
        driver.quit();
    }

    private void cleanUp() {
        // Delete browser cookies
        driver.manage().deleteAllCookies();
    }
}
