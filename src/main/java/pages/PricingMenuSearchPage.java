package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PricingMenuSearchPage extends AbstractPage{

    private final Logger logger = LogManager.getLogger(PricingMenuSearchPage.class);

    private String pageUrl = "https://cloud.google.com/products/calculator";
    private final By CalculatorPricingLink = By.xpath("//div[contains(text(),'Pricing calculator')]");

    public PricingMenuSearchPage(WebDriver driver) {

        super(driver);
    }

    @Override
    public AbstractPage openPage() {
        driver.navigate().to(pageUrl);
        logger.info("PricingMenuSearchPage page opened");
        return this;
    }

    public CalculatorPricingPage openCalculatorPricingPage (){
        waitAndClick(CalculatorPricingLink);
        logger.info("CalculatorPricingPage page opened");
        return new CalculatorPricingPage(driver);
    }
}
