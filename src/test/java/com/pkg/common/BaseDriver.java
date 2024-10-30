package com.pkg.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;


public class BaseDriver {
    private WebDriver driver;

    @BeforeSuite
    @Parameters({"browser"})
    public void setUp(String browser) throws MalformedURLException {

        // Set the capabilities for each browser
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }

    }


    @AfterSuite
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();// Clean up the ThreadLocal
        }
    }
}
