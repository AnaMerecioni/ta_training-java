package com.epam.training.ana_merecioni.webdriver.locators;

import com.epam.training.ana_merecioni.webdriver.locators.config.TestConfig;
import com.epam.training.ana_merecioni.webdriver.locators.driver.DriverFactory;
import com.epam.training.ana_merecioni.webdriver.locators.pages.HomePage;
import com.epam.training.ana_merecioni.webdriver.locators.pages.JobDetailsPage;
import com.epam.training.ana_merecioni.webdriver.locators.pages.JobSearchPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JobSearchTest {

    private WebDriver driver;

    static Stream<String> keywords() {
        return TestConfig.keywords().stream();
    }

    @BeforeEach
    void setUp() {
        driver = DriverFactory.create();
    }

    @ParameterizedTest(name = "keyword = {0}")
    @MethodSource("keywords")
    @DisplayName("Validate that the user can search for a position based on criteria")
    void userCanSearchForPositionBasedOnCriteria(String keyword) {
        JobSearchPage searchPage = new HomePage(driver)
                .open(TestConfig.baseUrl())
                .acceptAllCookies()
                .openCareers()
                .clickStartYourSearchHere()
                .selectLocation(TestConfig.location())
                .enterKeyword(keyword)
                .selectRemote();

        assertEquals(JobSearchPage.locationLabel(TestConfig.location()), searchPage.selectedLocation());
        assertTrue(searchPage.isRemoteSelected(), "Remote filter should be selected");

        JobDetailsPage jobDetails = searchPage
                .clickSearch()
                .openLatestJob();

        assertTrue(jobDetails.containsText(keyword),
                "Job page '" + jobDetails.title() + "' should mention " + keyword);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
