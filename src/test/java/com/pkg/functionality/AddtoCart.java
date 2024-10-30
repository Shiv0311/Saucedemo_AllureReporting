package com.pkg.functionality;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pkg.common.BaseDriver;

import io.qameta.allure.Story;

import java.time.Duration;
import java.util.List;

public class AddtoCart extends BaseDriver {
    private Actions action;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        action = new Actions(getDriver());
    }

    @Test(dependsOnMethods = "com.pkg.functionality.TestLogin.doLogin")
    @Story("To check whether products are added in cart")
    public void addCartFunctionality() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-test=\"add-to-cart-sauce-labs-backpack\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-test=\"add-to-cart-sauce-labs-bike-light\"]"))).click();
        WebElement OneSie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-test=\"add-to-cart-sauce-labs-onesie\"]")));
        action.moveToElement(OneSie).click().perform();
        WebElement cart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-test=\"shopping-cart-link\"]")));
        action.moveToElement(cart).click().perform();
        List<WebElement> productList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class=\"cart_item\"]/div[2]/a/div")));
        String[] expectedProducts = {"Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Onesie"};
        if (productList.size() == expectedProducts.length) {
            for (int i = 0; i < productList.size(); i++) {
                String actualProduct = productList.get(i).getText();
                String expectedProduct = expectedProducts[i];
                Assert.assertEquals(actualProduct, expectedProduct);
            }
        }
    }

    @Test(dependsOnMethods = "com.pkg.functionality.AddtoCart.addCartFunctionality")
    @Story("To check whether user can continue to add more items and navigated to Products Page without completing checkout process")
    public void continueShopping() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        WebElement continueShopping = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id=\"continue-shopping\"]")));
        action.moveToElement(continueShopping).click().perform();
        String actualUrl = getDriver().getCurrentUrl();
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(actualUrl, expectedUrl);
    }
}
