package pages;

import core.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait; // default 10 sn

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    protected WebElement findClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }



    protected void click(WebElement element) {
        try { element.click(); }
        catch (ElementNotInteractableException e) { jsClick(element); }
    }
    protected void click(By locator) { click(findClickable(locator)); }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }


    protected void clearOverlays() {
        ((JavascriptExecutor) driver).executeScript("""
            (() => {
              const s='[id^=ins-],[class*="ins-"],[id*="cookie"],[class*="cookie"],iframe[src*="insider"],[class*="overlay"],[class*="backdrop"],[class*="modal"]';
              document.querySelectorAll(s).forEach(e=>{try{e.remove();}catch(_){}}); })();
        """);
    }

    protected void waitAttributeContains(By locator, String attr, String value, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.attributeContains(locator, attr, value));
    }
    protected void shortPause() { try { Thread.sleep(120); } catch (InterruptedException ignored) {} }
}
