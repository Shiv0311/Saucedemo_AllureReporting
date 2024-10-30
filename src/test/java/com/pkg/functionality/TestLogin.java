package com.pkg.functionality;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.pkg.common.BaseDriver;

import io.qameta.allure.Story;

import java.time.Duration;

public class TestLogin extends BaseDriver {

    @Test
    @Parameters({"username", "password"})
    @Story("To check the login functionality of SauceDemo")
    public void doLogin(String username, String password) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        getDriver().get("https://www.saucedemo.com");

        WebElement userNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));

        userNameField.clear();
        userNameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton.click();

        // Verify login
        WebElement inventoryContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        Assert.assertTrue(inventoryContainer.isDisplayed(), "User is not logged in successfully.");
    }

    @Test(dependsOnMethods = "doLogin")
    @Story("To check the logout functionality of SauceDemo")
    public void doLogout() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        WebElement burgerMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-burger-menu-btn")));
        burgerMenu.click();

        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
        logoutButton.click();

        // Verify logout
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/", "User is not logged out successfully.");
    }
}
