package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class YopMailRecipientPage extends AbstractPage{

    private final Logger logger = LogManager.getLogger(YopMailRecipientPage.class);
    private final String pageUrl = "https://yopmail.com/en";

    private final By randomEmailGeneratorButton = By.xpath("//a[@href='email-generator'][.//h3[text()='Random Email generator']]");
    private final By generateNewMailButton = By.xpath("//button[@class='md but text f24 egenbut'][.//span[text()='New']]");//div[@class='nw'][.//span[text()='New']]
    private final By generatedMailField = By.xpath("//div[@id='geny'][.//span[@class='genytxt']]");

    public YopMailRecipientPage(WebDriver driver) {
        super(driver);

    }

    public YopMailRecipientPage openPage() {
        driver.navigate().to(pageUrl);
        logger.info("YopMailRecipientPage page opened");
        return this;
    }

    public YopMailSenderPage returnToYopMailSenderPage() {
        switchToEmailSenderTab();
        return new YopMailSenderPage(driver);
    }

    public YopMailRecipientPage generateEmailToCopy(){
        scrollAndClick(randomEmailGeneratorButton);
        scrollAndClick(generateNewMailButton);
        logger.info("YopMail Recipient page successfully initialized");
        return this;
    }

    public String copyEmail(){
        String generatedMailText = driver.findElement(generatedMailField).getText();
        logger.info("Email successfully copied: '{}'", generatedMailText);
        return generatedMailText;
    }
}
