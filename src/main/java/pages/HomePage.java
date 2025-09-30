package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private final By hotelTabBtn =
            By.xpath("//button[.//span[normalize-space()='Otel']]");
    private final By destinationInput =
            By.xpath("//div[.//span[normalize-space()='Nereye gideceksiniz?']]//input"
                    + "|//input[contains(@placeholder,'Yurt İçi') or contains(@placeholder,'Konum')]");

    public boolean isHotelTabDefault() {
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(hotelTabBtn));
        String display = (String) ((JavascriptExecutor) driver).executeScript(
                "return window.getComputedStyle(arguments[0],'::after').getPropertyValue('display');",
                btn);
        boolean selected = display != null && !"none".equalsIgnoreCase(display);
        if (selected) {
            logger.info("Doğrulama başarılı: 'Otel' sekmesi şu an varsayılan olarak seçili görünüyor (display={}).", display);
        } else {
            logger.warn("Doğrulama başarısız: 'Otel' sekmesi varsayılan olarak seçili değil (display={}).", display);
        }
        return selected;
    }

    public void typeDestinationFromCsv(String csvPath) {
        String value = utils.CsvUtils.readFirstValue(csvPath);
        WebElement input = wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(destinationInput));
        highlight(input);
        input.click();
        input.clear();
        input.sendKeys(value);
        logger.info("'Nereye gideceksiniz?' alanına CSV’den okunan değer yazıldı: '{}'", value);
    }




    public void closePopups() {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("""
(() => {
  const s='[id^=ins-],[class*="ins-"],[id*="cookie"],[class*="cookie"],svg.show-element,iframe[src*="insider"],iframe[id*="ins"],iframe[class*="ins"],[class*="overlay"],[class*="backdrop"],[class*="modal"]';
  const t=setInterval(()=>{document.querySelectorAll(s).forEach(e=>{try{e.remove();}catch(err){}});},250);
  setTimeout(()=>clearInterval(t),4000);
})();
""");
    }









}








