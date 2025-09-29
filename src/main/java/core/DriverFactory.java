package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            DRIVER.set(createDriver());
            logger.info("Yeni WebDriver örneği oluşturuldu: {}", DRIVER.get());
        }
        return DRIVER.get();
    }

    private static WebDriver createDriver() {
        logger.info("Chrome driver başlatılıyor...");
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    public static void quitDriver() {
        WebDriver d = DRIVER.get();
        if (d != null) {
            logger.info("Driver kapatılıyor...");
            d.quit();
            DRIVER.remove();
        }
    }
}
