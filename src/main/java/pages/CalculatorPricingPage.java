package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CalculatorPricingPage extends AbstractPage {

    private final Logger logger = LogManager.getLogger(CalculatorPricingPage.class);
    private final String pageUrl = "https://cloud.google.com/products/calculator";

    private final By addToEstimateButton = By.xpath("//h2[contains(text(),'Cost details')]//ancestor::div/following-sibling::div//span[contains(text(),'Add to estimate')]");
    private final By computeEngineTab = By.xpath("//h2[normalize-space()='Compute Engine']");
    private final By numberOfInstancesInput = By.xpath("//input[@type='number' and @value='1']");
    private final By checkBoxAddGPU = By.xpath("//button[@aria-label='Add GPUs']");
    private final By detailedViewButton = By.xpath("//a[@aria-label='Open detailed view']");
    private final By costUpdateIndicator = By.xpath("//div[contains(text(), 'Service cost updated')]");
    private final By estimatedCost = By.xpath("//div[div[normalize-space()='Estimated cost']]//label[contains(text(),'$')]");//3,883.62

    private final By operatingSystemListSelector = By.xpath("//div[@role='combobox'][.//*[text()='Operating System / Software']]");
    private final By machineFamilyListSelector = By.xpath("//div[@role='combobox'][.//*[text()='Machine Family']]");
    private final By seriesListSelector = By.xpath("//div[@role='combobox'][.//*[normalize-space()='Series']]");
    private final By machineTypeListSelector = By.xpath("//div[@role='combobox'][.//*[normalize-space()='Machine type']]/parent::div");
    private final By listOfGPU = By.xpath("//div[@role='combobox'][.//*[normalize-space()='GPU Model']]");
    private final By numberOfGPUSelector = By.xpath("//div[@role='combobox'][.//*[normalize-space()='Number of GPUs']]");
    private final By SSDSelector = By.xpath("//div[@role='combobox'][.//*[normalize-space()='Local SSD']]");
    private final By regionSelector = By.xpath("//div[@role='combobox'][.//*[normalize-space()='Region']]");
    private final By discountOptionsList = By.xpath("//div[@role='combobox'][.//*[normalize-space()='Committed use discount options']]");

    public CalculatorPricingPage(WebDriver driver) {

        super(driver);
    }

    @Override
    public CalculatorPricingPage openPage() {
        driver.navigate().to(pageUrl);
        logger.info("calculatorPricing page opened");
        return this;
    }

    public CalculatorPricingPage createCalculatorPricingPage() {
        GoogleCloudStartPage googlePage = new GoogleCloudStartPage(driver);
        googlePage.openPage();
        googlePage.acceptCookiesIfPresent();
        PricingMenuSearchPage menuSearchPage = googlePage.openPricingMenuCalculator();
        menuSearchPage.openCalculatorPricingPage();
        scrollAndClick(addToEstimateButton);
        waitAndClick(computeEngineTab);
        return this;
    }

    public CalculatorPricingPage setNumberOfInstances(String numberOfInstances) {
        waitAndSendKeys(numberOfInstancesInput, numberOfInstances);
        return this;
    }

    public CalculatorPricingPage selectOperatingSystem(String operatingSystem) {
        scrollAndClick(operatingSystemListSelector);
        waitAndClick(By.xpath("//li[@role='option' and .//span[contains(text(), '" + operatingSystem + "')]]"));
        return this;
    }

    public CalculatorPricingPage selectProvisioningModel(String provisioningModel) {
        scrollAndClick(By.xpath("//div[text()='" + provisioningModel + "']"));
        return this;
    }

    public CalculatorPricingPage selectMachineFamily(String machineFamily) {
        logger.debug("Attempting to open MachineFamily dropdown menu using locator: {}", machineFamilyListSelector);
        scrollAndClick(machineFamilyListSelector);
        logger.debug("Attempting to select MachineFamily option: {}", machineFamily);
        waitAndClick(By.xpath("//li[@role='option' and .//span[contains(text(), '" + machineFamily + "')]]"));
        logger.info("MachineFamily successfully selected: {}", machineFamily);
        return this;
    }

    public CalculatorPricingPage selectSeries(String series) {
        scrollAndClick(seriesListSelector);
        waitAndClick(By.xpath("//li[@role='option'][@data-value='" + series + "']"));
        return this;
    }

    public CalculatorPricingPage selectMachineType(String machineType) {
        scrollAndClick(machineTypeListSelector);
        waitAndClick(By.xpath("//li[@role='option'][@data-value='" + machineType + "']"));
        return this;
    }

    public CalculatorPricingPage selectGPUType(String gpuType) {
        waitAndClick(checkBoxAddGPU);
        waitAndClick(listOfGPU);
        waitAndClick(By.xpath("//li[@role='option'][@data-value='" + gpuType + "']"));
        return this;
    }

    public CalculatorPricingPage selectGPUNumber(String gpuNumber) {
        waitAndClick(numberOfGPUSelector);
        waitAndClick(By.xpath("//li[@role='option'][@data-value='" + gpuNumber + "']"));
        return this;
    }

    public CalculatorPricingPage selectLocalSSD(String localSSD) {
        waitAndClick(SSDSelector);
        waitAndClick(By.xpath("//li[@role='option' and .//span[contains(text(), '" + localSSD + "')]]"));
        return this;
    }

    public CalculatorPricingPage selectRegion(String region) {
        waitAndClick(regionSelector);
        waitAndClick(By.xpath("//li[@role='option' and .//span[contains(text(), '" + region + "')]]"));
        return this;
    }

    public CalculatorPricingPage selectDiscountOptions(String discountOptions) {
        scrollAndClick(discountOptionsList);
        waitAndClick(By.xpath("//li[@role='option' and .//span[contains(text(), '" + discountOptions + "')]]"));
        return this;
    }

    public void openDetailedView() {
        logger.info("Opening detailed view");
        scrollAndClick(detailedViewButton);
    }

    public String getEstimatedCost() {
        logger.debug("Waiting for calculator pricing engine to update...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            shortWait.until(ExpectedConditions.presenceOfElementLocated(costUpdateIndicator));
            logger.debug("Update indicator appeared. Waiting for it to disappear...");
        } catch (TimeoutException e) {

            logger.warn("Update indicator did not appear within 2 seconds, proceeding to cost extraction.");
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(costUpdateIndicator));

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(estimatedCost));

        String finalEstimatedCost = element.getText().trim();
        logger.info("Text of estimated cost successfully copied: '{}'", finalEstimatedCost);
        return finalEstimatedCost;
    }

    public DetailedViewPage switchToDetailedViewPage(int expectedTabsCount) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));

    wait.until(ExpectedConditions.numberOfWindowsToBe(expectedTabsCount));

    List<String> tabs = new ArrayList<>(driver.getWindowHandles());
    driver.switchTo().window(tabs.get(tabs.size() - 1));

    DetailedViewPage detailedViewPage = new DetailedViewPage(driver);

    logger.info("Successfully switched to DetailedViewPage. Current URL: {}", driver.getCurrentUrl());
    return detailedViewPage;
}

    public YopMailSenderPage openYopMaiSenderTab() {

        createAndSwitchToNewTab();
        driver.get("https://yopmail.com/en");
        YopMailSenderPage yopMailSenderPage = new YopMailSenderPage(driver);
        logger.info("Successfully opened and initialized YopMailSenderPage in a new tab");
        return yopMailSenderPage;
    }
}