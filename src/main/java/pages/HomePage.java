package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private final By hotelTabBtn = By.xpath("//span[normalize-space()='Otel']/ancestor::button[@role='tab'][1]");

    public boolean isHotelTabDefault() {
        WebElement btn = find(hotelTabBtn);
        String aria = btn.getAttribute("aria-selected"); // "true" bekliyoruz
        boolean selected = "true".equalsIgnoreCase(aria);
        logger.info("Otel tab aria-selected = {}", aria);
        return selected;
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








