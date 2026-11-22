package com.epam.training.ana_merecioni.webdriver.locators.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage extends BasePage {

    private static final By START_YOUR_SEARCH_HERE_BUTTON = By.partialLinkText("START YOUR SEARCH");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public JobSearchPage clickStartYourSearchHere() {
        click(firstDisplayed(START_YOUR_SEARCH_HERE_BUTTON));
        waitForUrlContaining("/jobs");
        return new JobSearchPage(driver);
    }
}
