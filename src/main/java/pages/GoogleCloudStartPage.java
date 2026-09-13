package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class GoogleCloudStartPage extends AbstractPage{

    private final Logger logger = LogManager.getLogger(GoogleCloudStartPage.class);
    private final String pageUrl = "https://cloud.google.com/";
    private final By calculatorCookieAcceptButton = By.xpath("//button[@class='glue-cookie-notification-bar__accept']");
    private final By pricingMenu = By.xpath("//a[contains(text(),'Pricing')]");

    public GoogleCloudStartPage(WebDriver driver) {
        super(driver);

    }

    @Override
    public AbstractPage openPage() {
        driver.navigate().to(pageUrl);
        logger.info("GoogleCloudStartPage page opened");
        return this;
    }

    public PricingMenuSearchPage openPricingMenuCalculator (){
        waitAndClick(pricingMenu);
        return new PricingMenuSearchPage(driver);
    }

    public void acceptCookiesIfPresent() {
        try {
            waitAndClick(calculatorCookieAcceptButton);
        } catch (TimeoutException e) {
            logger.info("Cookie snackbar did not appear");
        }
    }
}
