package pages;

import core.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement find(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected void click(By by) {
        find(by).click();
    }

    protected void type(By by, String text) {
        WebElement el = find(by);
        el.clear();
        el.sendKeys(text);
    }

    protected void scrollIntoView(By by) {
        WebElement el = find(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
    }
}
