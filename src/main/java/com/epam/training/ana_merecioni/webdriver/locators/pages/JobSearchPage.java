package com.epam.training.ana_merecioni.webdriver.locators.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class JobSearchPage extends BasePage {

    private static final String ALL_LOCATIONS = "All Locations";
    private static final String ALL_LOCATIONS_OPTION_LABEL = "All available countries";

    private static final By KEYWORDS_INPUT = By.name("search");
    private static final By LOCATION_DROPDOWN =
            By.cssSelector("[data-testid='country-dropdown'] .dropdown__control");
    private static final By LOCATION_SELECTED_VALUE =
            By.cssSelector("[data-testid='country-dropdown'] [data-testid='dropdown-value']");
    private static final String LOCATION_OPTION_TEMPLATE =
            "//div[@data-testid='dropdown-option' and normalize-space(.)='%s']";
    private static final By REMOTE_CHECKBOX_LABEL =
            By.xpath("//label[starts-with(@for, 'checkbox-vacancy_type-Remote') and contains(., 'Remote')]");
    private static final By REMOTE_CHECKBOX = By.xpath("//input[@name='vacancy_type-Remote']");
    private static final By SEARCH_BUTTON = By.xpath("//button[@name='submit_search_box_button']");
    private static final By JOB_CARDS = By.cssSelector("[data-testid='accordion-section-container']");
    private static final By LATEST_JOB_VIEW_LINK = By.xpath(
            "(//span[@data-testid='job-card-title'])[1]/ancestor::a[@data-testid='job-card-link']");

    private String keyword = "";

    public JobSearchPage(WebDriver driver) {
        super(driver);
        visible(KEYWORDS_INPUT);
        acceptCookiesIfPresent(Duration.ofSeconds(5));
    }

    public JobSearchPage selectLocation(String location) {
        String optionLabel = locationLabel(location);
        click(LOCATION_DROPDOWN);
        click(By.xpath(String.format(LOCATION_OPTION_TEMPLATE, optionLabel)));
        wait.until(ExpectedConditions.textToBe(LOCATION_SELECTED_VALUE, optionLabel));
        return this;
    }

    public static String locationLabel(String location) {
        return ALL_LOCATIONS.equalsIgnoreCase(location) ? ALL_LOCATIONS_OPTION_LABEL : location;
    }

    public String selectedLocation() {
        return visible(LOCATION_SELECTED_VALUE).getText().trim();
    }

    public JobSearchPage enterKeyword(String keyword) {
        this.keyword = keyword;
        typeKeyword();
        return this;
    }

    public JobSearchPage selectRemote() {
        if (!isRemoteSelected()) {
            click(firstDisplayed(REMOTE_CHECKBOX_LABEL));
            wait.until(d -> isRemoteSelected());
            waitForUrlContaining("vacancy_type=remote");
        }
        if (!keyword.isEmpty()) {
            wait.until(d -> {
                if (!keyword.equals(keywordValue())) {
                    typeKeyword();
                }
                return keyword.equals(keywordValue());
            });
        }
        return this;
    }

    public boolean isRemoteSelected() {
        return driver.findElements(REMOTE_CHECKBOX).stream().anyMatch(WebElement::isSelected);
    }

    public JobSearchPage clickSearch() {
        click(SEARCH_BUTTON);
        if (!keyword.isEmpty()) {
            waitForUrlContaining("search=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8));
        }
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(JOB_CARDS, 0));
        return this;
    }

    public JobDetailsPage openLatestJob() {
        WebElement link = visible(LATEST_JOB_VIEW_LINK);
        click(link);
        waitForUrlContaining("/vacancy/");
        return new JobDetailsPage(driver);
    }

    private void typeKeyword() {
        WebElement input = visible(KEYWORDS_INPUT);
        click(input);
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        input.sendKeys(keyword);
    }

    private String keywordValue() {
        return driver.findElement(KEYWORDS_INPUT).getAttribute("value");
    }
}
