package pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import utils.CsvUtils;
import java.time.LocalDate;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private final By hotelTabBtn        = By.xpath("//button[.//span[normalize-space()='Otel']]");
    private final By destinationInput   = By.xpath("//input[contains(@placeholder,'Yurt İçi')]");

    private final By antalyaSuggestion = By.xpath("//*[contains(text(),'Antalya')]");


    private final By dateOpenArea       = By.xpath("//div[@class='sc-1afe4354-3 dkrcXi' and normalize-space()='Giriş - Çıkış Tarihleri']");
    private final By nextMonthBtn       = By.xpath("//div[contains(@class,'DayPickerNavigation')]//button[last()]");
    private final By captions           = By.cssSelector(".CalendarMonth_caption, .DayPicker-Caption");
    private final By dateBox            = By.xpath("//input[contains(@placeholder,'Tarih') or contains(@placeholder,'Check-in')] | //div[contains(text(),'.') and contains(text(),'-')]");


    private boolean isAprilVisible(int year) {
        return driver.findElements(captions).stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .noneMatch(t -> (t.contains("Nisan") || t.contains("April")) && t.contains(String.valueOf(year)));
    }

    private WebElement getMonthContainer(int year) {
        String y = String.valueOf(year);
        By container = By.xpath(
                "//*[contains(@class,'CalendarMonth') and (@data-visible='true' or not(contains(@class,'hidden')))" +
                        " and .//div[contains(@class,'CalendarMonth_caption')][contains(.,'"+y+"') and (contains(.,'Nisan') or contains(.,'April'))]]");
        return find(container);
    }

    private WebElement findDay(WebElement monthPanel, int year, int day) {
        String enabled = "not(@disabled) and (@aria-disabled='false' or not(@aria-disabled)) " +
                "and not(contains(@class,'outside')) and not(contains(@class,'blocked')) " +
                "and not(contains(@class,'disabled'))";

        By locator = By.xpath(
                ".//*[self::button or self::td or self::div][" + enabled +
                        " and (contains(@aria-label,' " + day + " Nisan') or contains(@aria-label,'April " + day + "'))]"
        );

        return monthPanel.findElements(locator).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new TimeoutException("Gün bulunamadı (aria-label): " + day + ".04." + year));
    }



    public boolean isHotelTabDefault() {
        WebElement btn = find(hotelTabBtn);
        String display = (String) ((JavascriptExecutor) driver)
                .executeScript("return window.getComputedStyle(arguments[0],'::after').getPropertyValue('display');", btn);
        boolean selected = display != null && !"none".equalsIgnoreCase(display);
        logger.info("'Otel' sekmesi varsayılan mı? → {}", selected);
        return selected;
    }

    public void typeDestinationFromCsvAndSelect(String csvPath) {
        String value = CsvUtils.readFirstValue(csvPath);
        WebElement input = findClickable(destinationInput);
        input.clear();
        input.sendKeys(value);
        logger.info("CSV’den okunan değer yazıldı: {}", value);
        WebElement opt = waitPresent(antalyaSuggestion, 5);
        jsClick(opt);
        logger.info("Antalya önerisi bulundu ve seçildi.");
        waitAttributeContains(destinationInput, "value", "Antalya", 5);
    }

    public void pickApril1to8() {
        clearOverlays();
        click(dateOpenArea);

        int year = (LocalDate.now().getMonthValue() <= 4) ? LocalDate.now().getYear()
                : LocalDate.now().getYear() + 1;

        for (int i = 0; i < 18 && isAprilVisible(year); i++) {
            click(nextMonthBtn);
            shortPause();
        }
        if (isAprilVisible(year)) throw new TimeoutException("Nisan " + year + " bulunamadı!");

        WebElement aprilPanel = getMonthContainer(year);
        WebElement d1 = findDay(aprilPanel, year, 1);
        WebElement d8 = findDay(aprilPanel, year, 8);


        click(d1); shortPause(); click(d8);
        logger.info("1–8 Nisan {} başarıyla seçildi.", year);
    }

    public boolean verifyAprilDates(int year) {
        String expected = String.format("01.04.%d - 08.04.%d", year, year);
        WebElement el = find(dateBox);
        String actual = el.getTagName().equalsIgnoreCase("input") ? el.getAttribute("value") : el.getText();
        boolean ok = actual != null && actual.contains(expected);
        if (ok) logger.info("Tarih doğrulandı → {}", actual);
        else    logger.error("Tarih yanlış! Beklenen '{}', bulunan '{}'", expected, actual);
        return ok;
    }
}
