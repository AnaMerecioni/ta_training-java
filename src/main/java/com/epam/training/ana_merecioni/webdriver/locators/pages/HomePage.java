package com.epam.training.ana_merecioni.webdriver.locators.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class HomePage extends BasePage {

    private static final By TOP_NAVIGATION = By.className("top-navigation__row");
    private static final By CAREERS_LINK = By.linkText("Careers");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open(String url) {
        driver.get(url);
        return this;
    }

    public HomePage acceptAllCookies() {
        acceptCookiesIfPresent(Duration.ofSeconds(10));
        return this;
    }

    public CareersPage openCareers() {
        WebElement navigation = visible(TOP_NAVIGATION);
        click(navigation.findElement(CAREERS_LINK));
        waitForUrlContaining("/careers");
        return new CareersPage(driver);
    }
}
