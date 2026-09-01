package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class YopMailSenderPage extends AbstractPage{


    private final Logger logger = LogManager.getLogger(YopMailSenderPage.class);
    private final String pageUrl = "https://yopmail.com/en";
    private final By yopMailConsentAcceptButton = By.xpath("//div[@class='fc-footer-buttons']//button[contains(@class, 'fc-cta-consent')]//p[@class='fc-button-label']"); //p[text()='Consent']
    private final By randomEmailGeneratorButton = By.xpath("//a[@href='email-generator'][.//h3[text()='Random Email generator']]");

    private final By checkInboxButton = By.xpath("//span[text()='Check Inbox']");
    private final By newMailButton = By.xpath("//button[@id='newmail']");
    private final By recipientField = By.xpath("//input[@id='msgto']");
    private final By subjectField = By.xpath("//input[@id='msgsubject']");
    private final By messageBodyField = By.xpath("//div[@id='msgbody']");
    private final By sendMailButton = By.xpath("//button[@id='msgsend']");

    private final By mailSentConfirmationMessage = By.xpath("//div[text()='Your message has been sent']");

    public YopMailSenderPage(WebDriver driver) {

        super(driver);
    }

    @Override
    public YopMailSenderPage openPage() {
        driver.navigate().to(pageUrl);
        logger.info("YopMailSender page opened");
        return this;
    }

    public CalculatorPricingPage returnToCalculatorPricingPage() {
        switchToCalculatorPricingTab();
        return new CalculatorPricingPage(driver);
    }

    public YopMailRecipientPage openYopMaiRecipientTab() {

        openAndSwitchToEmailRecipientTab("https://yopmail.com/en");
        logger.info("Successfully switched to YopMail Recipient tab ");
        return new YopMailRecipientPage(driver);
    }

    public YopMailSenderPage generateEmailToSend(){
        waitAndClick(yopMailConsentAcceptButton);
        scrollAndClick(randomEmailGeneratorButton);
        scrollAndClick(checkInboxButton);
        waitAndClick(newMailButton);
        logger.info("YopMail Sender page successfully initialized and ready to send email");
        return this;
    }

    public YopMailSenderPage sendNewEmail(){
        waitAndClick(newMailButton);
        return this;
    }

    public YopMailSenderPage fillInEmailSubjectField(String emailSubject){
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='ifmail']")));
        driver.findElement(subjectField).sendKeys(emailSubject);
        driver.switchTo().defaultContent();
        return this;
    }
    public YopMailSenderPage fillInEmailBodyField(String emailBody){
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='ifmail']")));
        driver.findElement(messageBodyField).sendKeys(emailBody);
        driver.switchTo().defaultContent();
        return this;
    }
    public YopMailSenderPage fillInEmailRecipientField(String emailRecipient){
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='ifmail']")));
        driver.findElement(recipientField).sendKeys(emailRecipient);
        driver.switchTo().defaultContent();
        return this;
    }
    public YopMailSenderPage sendEmail(){
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='ifmail']")));
        driver.findElement(sendMailButton).click();
        driver.switchTo().defaultContent();
        logger.info("Email was sent successively");
        return this;
    }

    public String getTextFromEmailSendConfirmation(){
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='ifmail']")));
        WebElement mailConfirmationMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(mailSentConfirmationMessage));
        String confirmationText = mailConfirmationMessage.getText();

        driver.switchTo().defaultContent();
        logger.info("Email send confirmation message successfully retrieved: '{}'", confirmationText);
        return confirmationText;
    }
}
