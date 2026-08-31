package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverSingleton {
    private static WebDriver driver;

    private DriverSingleton() {}

    public static WebDriver getDriver() {
        String browser = System.getProperty("browser", "chrome");

        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (driver == null) {
            switch (browser.toLowerCase()) {
                case "firefox":
                    driver = new FirefoxDriver(getFirefoxOptions(isHeadless));
                    break;
                case "edge":
                    driver = new EdgeDriver(getEdgeOptions(isHeadless));
                    break;
                case "chrome":
                default:
                    driver = new ChromeDriver(getChromeOptions(isHeadless));
                    break;
            }

            driver.manage().window().maximize();
        }
        return driver;
    }

    private static ChromeOptions getChromeOptions(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
        }
        return options;
    }

    private static FirefoxOptions getFirefoxOptions(boolean isHeadless) {
        FirefoxOptions options = new FirefoxOptions();
        if (isHeadless) {
            options.addArguments("-headless");
            options.addArguments("--window-size=1920,1080");
        }
        return options;
    }

    private static EdgeOptions getEdgeOptions(boolean isHeadless) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
        }
        return options;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
