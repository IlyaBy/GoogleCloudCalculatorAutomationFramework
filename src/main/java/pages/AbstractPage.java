package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public abstract class AbstractPage {

    protected WebDriver driver;
    protected static final Logger log = LogManager.getLogger(AbstractPage.class);

    protected final int WAIT_TIMEOUT_SECONDS = 10;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;

    }

    protected abstract AbstractPage openPage();

    protected String createAndSwitchToNewTab() {
        Set<String> beforeOpen = driver.getWindowHandles();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        Set<String> afterOpen = driver.getWindowHandles();
        afterOpen.removeAll(beforeOpen);
        String newTab = afterOpen.iterator().next();
        driver.switchTo().window(newTab);
        return newTab;
    }

    protected void waitAndClick(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {

                    WebElement element = d.findElement(locator);

                    ((JavascriptExecutor) d).executeScript("arguments[0].click();", element);
                    return true;
                });
    }

    protected void waitAndSendKeys(By locator, String text) {

        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(locator));

        try {
            if (element.getTagName().equalsIgnoreCase("input") || element.getTagName().equalsIgnoreCase("textarea")) {
                element.clear();
            }
        } catch (Exception e) {
            log.warn("Failed to clear element {}. Reason: {}", locator, e.getMessage());
        }
        element.sendKeys(text);
    }

    protected void scrollAndClick(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {

                    WebElement element = d.findElement(locator);

                    ((JavascriptExecutor) d).executeScript(
                            "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});",
                            element
                    );

                    ((JavascriptExecutor) d).executeScript(
                            "arguments[0].click();",
                            element
                    );

                    return true;
                });
    }
}