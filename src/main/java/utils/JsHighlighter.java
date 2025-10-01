package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsHighlighter {

    private JsHighlighter() {}

    public static void highlight(WebDriver driver, WebElement el) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String originalStyle = el.getAttribute("style");

        js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                el, "border: 3px solid yellow; background-color: yellow;");
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                el, originalStyle);
    }
}
