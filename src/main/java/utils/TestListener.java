package utils;

import driver.DriverSingleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

   @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test '{}' failed. Attempt to take a screenshot...", result.getName());

        WebDriver driver = DriverSingleton.getDriver();

        if (driver != null) {
            ScreenshotUtils.captureScreenshot(driver, result.getName());
        } else {
            logger.warn("Failed to take a screenshot");
        }
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}