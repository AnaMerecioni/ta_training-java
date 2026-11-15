package com.epam.training.ana_merecioni.webdriver.dynamic_loading;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
class DynamicLoadingTest {
     private static final String URL = "https://the-internet.herokuapp.com/dynamic_loading/1";
    private static final Duration TIMEOUT = Duration.ofSeconds(15);
    private static final By START_BUTTON = By.cssSelector("#start button");
    private static final By LOADING_BAR  = By.id("loading");
    private static final By FINISH_TEXT  = By.cssSelector("#finish h4");
    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeEach
    void setUp() {
        // Browserul se alege cu -Dbrowser=firefox / edge. Implicit e chrome.
        String browser = System.getProperty("browser", "chrome");
        driver = switch (browser.toLowerCase()) {
            case "firefox" -> new FirefoxDriver();
            case "edge"    -> new EdgeDriver();
            default        -> new ChromeDriver();
        };
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, TIMEOUT);
        driver.navigate().to(URL);
    }
    @Test
    @DisplayName("Hello World! apare dupa ce dispare bara de incarcare")
    void helloWorldAppearsAfterLoading() {
        wait.until(ExpectedConditions.elementToBeClickable(START_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOADING_BAR));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LOADING_BAR));
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(FINISH_TEXT));
        assertEquals("Hello World!", result.getText());
    }
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}