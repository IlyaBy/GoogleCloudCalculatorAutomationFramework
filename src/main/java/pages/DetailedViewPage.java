package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class DetailedViewPage extends AbstractPage{

    private final Logger logger = LogManager.getRootLogger();
    private final String pageUrl = "https://cloud.google.com/products/calculator/estimate-preview/";

    private final By detailedViewCost = By.xpath("//div[div[normalize-space()='Total estimated cost']]/div[contains(text(),'$')]");

    public DetailedViewPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public AbstractPage openPage() {
        driver.navigate().to(pageUrl);
        logger.info("DetailedViewPage page opened");
        return this;
    }

    public String getDetailedViewCost() {
        WebElement detViewCalcEstimatedCost = (new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(detailedViewCost)));

        String detailedViewCostText = detViewCalcEstimatedCost.getText();
        logger.info("Text of detailedViewCost page successfully copied: {}", detailedViewCostText);
        return detailedViewCostText;
    }
}
