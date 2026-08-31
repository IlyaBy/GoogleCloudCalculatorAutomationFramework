package utils;

import driver.DriverSingleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test '{}' failed. Attempt to take a screenshot...", result.getName());

        WebDriver driver = DriverSingleton.getDriver();

        if (driver != null) {
            captureScreenshot(driver, result.getName());
        } else {
            logger.warn("Failed to take screenshot");
        }
    }

    private void captureScreenshot(WebDriver driver, String testName) {
        try {

            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + ".png";

            Path targetDir = Paths.get("target", "screenshots");
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            Path destination = targetDir.resolve(fileName);
            Files.copy(source.toPath(), destination);

            logger.info("Screenshot successfully saved: {}", destination.toAbsolutePath());
        } catch (IOException e) {
            logger.error("Error occurred while saving screenshot: {}", e.getMessage());
        }
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}