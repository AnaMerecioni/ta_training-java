package com.epam.training.ana_merecioni.webdriver.locators.pages;

import com.epam.training.ana_merecioni.webdriver.locators.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected static final By ACCEPT_ALL_COOKIES_BUTTON = By.id("onetrust-accept-btn-handler");

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TestConfig.timeout());
    }

    protected void acceptCookiesIfPresent(Duration timeout) {
        try {
            WebElement button = new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.elementToBeClickable(ACCEPT_ALL_COOKIES_BUTTON));
            click(button);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(ACCEPT_ALL_COOKIES_BUTTON));
        } catch (TimeoutException ignored) {
        }
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement firstDisplayed(By locator) {
        return wait.until(d -> {
            List<WebElement> elements = d.findElements(locator);
            return elements.stream().filter(WebElement::isDisplayed).findFirst().orElse(null);
        });
    }

    protected void click(WebElement element) {
        scrollIntoView(element);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void click(By locator) {
        click(visible(locator));
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    protected void waitForUrlContaining(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }
}
