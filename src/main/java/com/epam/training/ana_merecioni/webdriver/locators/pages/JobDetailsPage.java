package com.epam.training.ana_merecioni.webdriver.locators.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JobDetailsPage extends BasePage {

    private static final By JOB_TITLE = By.tagName("h1");
    private static final By JOB_DESCRIPTION = By.cssSelector("[data-testid='description-content']");
    private static final By MAIN_CONTENT = By.tagName("main");

    public JobDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String title() {
        return visible(JOB_TITLE).getText().trim();
    }

    public String description() {
        return visible(JOB_DESCRIPTION).getText();
    }

    public boolean containsText(String text) {
        visible(JOB_DESCRIPTION);
        String content = driver.findElement(MAIN_CONTENT).getText().toLowerCase();
        return content.contains(text.toLowerCase());
    }
}
