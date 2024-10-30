package com.pkg.functionality;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.pkg.common.BaseDriver;

import io.qameta.allure.Story;

import java.time.Duration;
import java.util.List;

public class Checkout extends BaseDriver {
    private Actions action;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        action = new Actions(getDriver());
    }

    @Test(dependsOnMethods = "com.pkg.functionality.AddtoCart.continueShopping")
    @Parameters({"firstName", "lastName", "postalCode"})
    @Story("To check whether checkout functionality is working properly")
    public void doCheckout(String firstname, String lastname, String zipcode) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        WebElement cart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-test=\"shopping-cart-link\"]")));
        action.moveToElement(cart).click().perform();
        List<WebElement> productList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class=\"cart_item\"]/div[2]/a/div")));
        if (productList.size() > 0) {
            WebElement checkout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-test=\"checkout\"]")));
            action.moveToElement(checkout).click().perform();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-test=\"firstName\"]"))).sendKeys(firstname);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-test=\"lastName\"]"))).sendKeys(lastname);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-test=\"postalCode\"]"))).sendKeys(zipcode);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-test=\"continue\"]"))).click();
            String actualCheckoutUrl = getDriver().getCurrentUrl();
            String expectedCheckoutUrl = "https://www.saucedemo.com/checkout-step-two.html";
            Assert.assertEquals(actualCheckoutUrl, expectedCheckoutUrl);
        } else {
            if (getDriver().getCurrentUrl().equals("https://www.saucedemo.com/checkout-step-two.html")) {
                Assert.fail();
            } else {
                Assert.assertTrue(true);
            }
        }
    }

    @Test(dependsOnMethods = "com.pkg.functionality.Checkout.doCheckout")
    @Parameters({"status"})
    @Story("To check the checkout functionality post clicking on Finish or cancel button")
    public void finishOrCancelCheckout(String status) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        if (status.equalsIgnoreCase("finish")) {
            WebElement finishButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-test=\"finish\"]")));
            action.moveToElement(finishButton).click().perform();
            String actualFinishUrl = getDriver().getCurrentUrl();
            String expectedFinishUrl = "https://www.saucedemo.com/checkout-complete.html";
            Assert.assertEquals(actualFinishUrl, expectedFinishUrl);
        } else if (status.equalsIgnoreCase("cancel")) {
            WebElement cancelButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-test=\"cancel\"]")));
            action.moveToElement(cancelButton).click().perform();
            String actualCancelUrl = getDriver().getCurrentUrl();
            String expectedCancelUrl = "https://www.saucedemo.com/inventory.html";
            Assert.assertEquals(actualCancelUrl, expectedCancelUrl);
        } else {
            Assert.fail();
        }
    }
}
