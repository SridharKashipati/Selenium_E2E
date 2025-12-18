package tests;

import utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        log.info("Setting up WebDriver");
        driver = DriverFactory.createDriver();
        log.info("WebDriver initialized");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            log.info("Closing WebDriver");
            driver.quit();
        }
    }
}
