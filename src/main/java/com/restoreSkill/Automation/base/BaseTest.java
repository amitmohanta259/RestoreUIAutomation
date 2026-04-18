package com.restoreSkill.Automation.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    public WebDriver driver;

    @BeforeTest(alwaysRun = true)
    public void setUp() {
        LOG.info("Initializing WebDriver (Chrome)");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

//    @AfterTest(alwaysRun = true)
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
