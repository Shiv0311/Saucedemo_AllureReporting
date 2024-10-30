package com.pkg.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

public abstract class BaseDriver {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final String hubURL = "http://localhost:4444/wd/hub"; // Selenium Grid Hub URL

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String browser) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);

        // Set the capabilities for each browser
        if (browser.equalsIgnoreCase("chrome")) {
            capabilities.setCapability("goog:chromeOptions", new HashMap<String, Object>() {{
                put("args", Arrays.asList("--start-maximized"));
            }});
        } else if (browser.equalsIgnoreCase("firefox")) {
            capabilities.setCapability("moz:firefoxOptions", new HashMap<String, Object>() {{
                put("args", Arrays.asList("--start-maximized"));
            }});
        } else if (browser.equalsIgnoreCase("edge")) {
            capabilities.setCapability("ms:edgeOptions", new HashMap<String, Object>() {{
                put("args", Arrays.asList("--start-maximized"));
            }});
        }

        // Create and return the RemoteWebDriver instance
        driver.set(new RemoteWebDriver(new URL(hubURL), capabilities));
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // Close the browser
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Clean up the ThreadLocal
        }
    }
}
