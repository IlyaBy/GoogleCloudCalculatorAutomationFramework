package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


    public class ScreenshotUtils {
        private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);

        private ScreenshotUtils() {

        }

        public static void captureScreenshot(WebDriver driver, String testName) {
            if (driver == null) {
                logger.warn("Driver instance is null. Cannot capture screenshot for test: {}", testName);
                return;
            }

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String screenshotName = testName + "_" + timestamp + ".png";

            try {
                File targetFile = new File("target/screenshots/" + screenshotName);
                Files.copy(screenshot.toPath(), targetFile.toPath());
                logger.info("Screenshot successfully saved: {}", targetFile.getAbsolutePath());
            } catch (IOException e) {
                logger.error("Failed to save screenshot for test {}. Reason: {}", testName, e.getMessage());
            }
        }
    }

