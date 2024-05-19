import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.*;  

import java.net.URL;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class BaseTestSuite {
    protected WebDriver driver;
    protected static Properties configProperties;
    private static final String configFilePath =
        Paths.get(System.getProperty("user.dir") + "/test.config").toString();
    
    @BeforeAll
    public static void setupConfig() {
        configProperties = new Properties();
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            configProperties.load(fis);
        } catch (FileNotFoundException ex) {
            Assertions.fail(String.format("Configuration file \"%s\" not found.", configFilePath));
        } catch (IOException ex) {
            Assertions.fail("Could not read the configuration file.");
        }
    }

    @BeforeEach
    public void setupDriver() throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
    }
    
    @AfterEach
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
