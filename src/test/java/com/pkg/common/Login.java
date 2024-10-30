package com.pkg.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login extends BaseDriver {
    private WebDriverWait wait;

    public Login(WebDriverWait wait) {
        this.wait = wait; // Initialize with the passed WebDriverWait
    }

    public void loginFunctionality(String username, String password) {
        getDriver().get("https://www.saucedemo.com");
        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement passWord = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        userName.clear();
        userName.sendKeys(username);
        passWord.clear();
        passWord.sendKeys(password);
        loginButton.click();
    }

    public void logout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id=\"react-burger-menu-btn\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-test=\"logout-sidebar-link\"]"))).click();
    }
}
