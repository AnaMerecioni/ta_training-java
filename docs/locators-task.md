# Locators Task – EPAM Careers Job Search

## Test Case

**Name:** Validate that the user can search for a position based on criteria

| # | Step | Data |
|---|------|------|
| 1 | Navigate to https://www.epam.com/ | `base.url` |
| 2 | Click the "Accept All" button in the cookie consent banner | |
| 3 | Find and click the "Careers" link | |
| 4 | Click the "Start your search here" button | |
| 5 | Select "All Locations" in the "Location" field | `search.location` |
| 6 | Enter a programming language name in the "Keywords" field | `search.keywords` (test parameter) |
| 7 | Select the "Remote" option | |
| 8 | Click the "Search" button | |
| 9 | Locate the latest job listing item in the search results | |
| 10 | Click the "View and apply" button | |
| 11 | Validate that the programming language is present on the job description page | |

**Expected result:** the job description page contains the keyword (e.g. Java).

## Locator Mapping Table

| # | Element | Locator type | Locator (Java / Selenium) |
|---|---------|--------------|---------------------------|
| 1 | "Accept All" button (cookie banner) | ID | `By.id("onetrust-accept-btn-handler")` |
| 2 | "Keywords" input | Name | `By.name("search")` |
| 3 | Top navigation menu (container of "Careers") | ClassName | `By.className("top-navigation__row")` |
| 4 | Job title on the job description page | TagName | `By.tagName("h1")` |
| 5 | Job description page content | TagName | `By.tagName("main")` |
| 6 | "Careers" link | LinkText | `By.linkText("Careers")` |
| 7 | "Start your search here" button | PartialLinkText | `By.partialLinkText("START YOUR SEARCH")` |
| 8 | "Location" field | CSS Selector | `By.cssSelector("[data-testid='country-dropdown'] .dropdown__control")` |
| 9 | "Location" selected value | CSS Selector | `By.cssSelector("[data-testid='country-dropdown'] [data-testid='dropdown-value']")` |
| 10 | Job listing items | CSS Selector | `By.cssSelector("[data-testid='accordion-section-container']")` |
| 11 | Job description block | CSS Selector | `By.cssSelector("[data-testid='description-content']")` |
| 12 | "Search" button | XPath (relative path) | `By.xpath("//button[@name='submit_search_box_button']")` |
| 13 | "Remote" checkbox input | XPath (relative path) | `By.xpath("//input[@name='vacancy_type-Remote']")` |
| 14 | "All Locations" option in "Location" list | XPath with operators / functions | `By.xpath("//div[@data-testid='dropdown-option' and normalize-space(.)='All available countries']")` |
| 15 | "Remote" option label | XPath with operators / functions | `By.xpath("//label[starts-with(@for, 'checkbox-vacancy_type-Remote') and contains(., 'Remote')]")` |
| 16 | Latest job listing – "View and apply" link | XPath with axes navigation | `By.xpath("(//span[@data-testid='job-card-title'])[1]/ancestor::a[@data-testid='job-card-link']")` |

On the current careers site the "All Locations" option is labelled "All available countries", and the "View and apply" action of a job listing is the job title link, which opens the job description page.

### Coverage of the mandatory locator types

| Locator type | Rows |
|--------------|------|
| ID | 1 |
| Name | 2 |
| ClassName | 3 |
| TagName | 4, 5 |
| LinkText | 6 |
| PartialLinkText | 7 |
| CSS Selector | 8, 9, 10, 11 |
| XPath (relative path) | 12, 13 |
| XPath with operators / functions | 14, 15 |
| XPath with axes navigation | 16 |

## Project Structure

```
src/main/java/com/epam/training/ana_merecioni/webdriver/locators/
├── config/TestConfig.java
├── driver/DriverFactory.java
└── pages/
    ├── BasePage.java
    ├── HomePage.java
    ├── CareersPage.java
    ├── JobSearchPage.java
    └── JobDetailsPage.java
src/test/java/com/epam/training/ana_merecioni/webdriver/locators/JobSearchTest.java
src/test/resources/test.properties
```

Stack: Java 17, Selenium WebDriver 4, JUnit 5 (parameterized test), Maven, Page Object Model.

## Configuration

`src/test/resources/test.properties`

```
base.url=https://www.epam.com/
browser=chrome
headless=false
timeout.seconds=20
search.location=All Locations
search.keywords=Java
```

Every property can be overridden from the command line with `-D<property>=<value>`. `search.keywords` accepts a comma-separated list; the test runs once for each keyword.

## Run

```
mvn test -Dtest=JobSearchTest
mvn test -Dtest=JobSearchTest -Dsearch.keywords=Java,Python
mvn test -Dtest=JobSearchTest -Dbrowser=firefox -Dheadless=true
```
