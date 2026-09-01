package driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverSingleton {

    private static final Logger LOGGER = LogManager.getLogger(DriverSingleton.class);
    private static WebDriver driver;

    private DriverSingleton() {}

    public static WebDriver getDriver() {
        String browser = System.getProperty("browser", "chrome");

        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (driver == null) {

            LOGGER.info("Initializing WebDriver instance for browser: '{}' (Headless mode = {})", browser.toUpperCase(), isHeadless);

            switch (browser.toLowerCase()) {
                case "firefox":
                    LOGGER.debug("Applying custom Firefox capabilities and profiles...");
                    driver = new FirefoxDriver(getFirefoxOptions(isHeadless));
                    break;
                case "edge":
                    LOGGER.debug("Applying custom Edge capabilities...");
                    driver = new EdgeDriver(getEdgeOptions(isHeadless));
                    break;
                case "chrome":
                default:
                    LOGGER.debug("Applying custom Chrome capabilities (--remote-allow-origins)...");
                    driver = new ChromeDriver(getChromeOptions(isHeadless));
                    break;
            }

            driver.manage().window().maximize();
            LOGGER.info("WebDriver instance successfully started and window maximized.");

        }
        return driver;
    }

    private static ChromeOptions getChromeOptions(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        if (isHeadless) {
            LOGGER.debug("Configuring Chrome to run in HEADLESS mode with size 1920x1080.");
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
        }
        return options;
    }

    private static FirefoxOptions getFirefoxOptions(boolean isHeadless) {
        FirefoxOptions options = new FirefoxOptions();
        if (isHeadless) {
            LOGGER.debug("Configuring Firefox to run in HEADLESS mode.");
            options.addArguments("-headless");
            options.addArguments("--window-size=1920,1080");
        }
        return options;
    }

    private static EdgeOptions getEdgeOptions(boolean isHeadless) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        if (isHeadless) {
            LOGGER.debug("Configuring Edge to run in HEADLESS mode with size 1920x1080.");
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
        }
        return options;
    }

    public static void closeDriver() {
        if (driver != null) {
            LOGGER.info("Closing WebDriver instance and quitting browser process.");
            driver.quit();
            driver = null;
        }
    }
}
