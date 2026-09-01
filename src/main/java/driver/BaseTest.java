package driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TestListener;
import org.testng.annotations.Listeners;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Listeners({TestListener.class})
public class BaseTest {

    private final Logger logger = LogManager.getLogger(BaseTest.class);

    protected WebDriver driver;

    protected Properties properties;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverSingleton.getDriver();
        properties = new Properties();

        String env = System.getProperty("env", "qa");
        logger.info("Starting test execution on environment: {}", env.toUpperCase());

        String propertyFilePath = "src/main/resources/" + env + ".properties";

        try (FileInputStream fileInputStream = new FileInputStream(propertyFilePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file at path:" + propertyFilePath, e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void stopBrowser() {
        DriverSingleton.closeDriver();
    }
}
