package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class SearchResultsPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(SearchResultsPage.class);


    private final By rerunSearchBtn = By.xpath("//button[.//span[normalize-space()='Yeniden Ara']]");
    private final By productCountHeader = By.cssSelector("h3#urun-sayisi");
    private final By regionLabels = By.cssSelector("div[data-testid='checkbox'] span");
    private final By showMoreBtn = By.xpath("//div[normalize-space()='Daha Fazla Göster']");


    public SearchResultsPage waitUntilReady() {
        find(rerunSearchBtn);
        return this;

    }

    public boolean verifyUrlContains(String keyword) {
        String url = Objects.requireNonNull(driver.getCurrentUrl()).toLowerCase();
        boolean ok = url.contains(keyword.toLowerCase());
        if (ok) {
            logger.info("URL doğrulandı: {}", url);
        } else {
            logger.error("URL '{}' içermiyor! → {}", keyword, url);
        }
        return ok;
    }

    public int getHeaderCount() {
        String text = find(productCountHeader).getText(); // Antalya (520)
        String digits = text.replaceAll("\\D+", "");
        return digits.isEmpty() ? 0 : Integer.parseInt(digits);
    }

    public void clickShowMoreIfVisible() {
        List<WebElement> elements = findAll(showMoreBtn);
        if (!elements.isEmpty() && elements.getFirst().isDisplayed()) {
            WebElement btn = elements.getFirst();
            scrollIntoView(btn);
            click(btn);
            logger.info("'Daha Fazla Göster' tıklandı.");
            shortPause();
        } else {
            logger.info("'Daha Fazla Göster' bulunamadı (opsiyonel).");
        }
    }



    public int selectRandomRegionAndGetCount() {
        clickShowMoreIfVisible();
        List<WebElement> options = findAll(regionLabels);
        if (options.isEmpty()) throw new IllegalStateException("Hiç bölge bulunamadı!");
        int idx = (int) (Math.random() * options.size());
        WebElement chosen = options.get(idx);
        scrollIntoView(chosen);
        click(chosen);
        String txt = chosen.getText();
        String digits = txt.replaceAll("\\D+", "");
        int count = digits.isEmpty() ? 0 : Integer.parseInt(digits);
        logger.info("Rastgele bölge seçildi: {} ({} adet)", txt, count);
        return count;
    }

    public void waitUntilHeaderCountUpdates(int expected) {
        wait.until(d -> {
            String text = d.findElement(productCountHeader).getText();
            String digits = text.replaceAll("\\D+", "");
            if (digits.isEmpty()) return false;
            return Integer.parseInt(digits) == expected;
        });
        logger.info("Header sayısı {} olarak güncellendi.", expected);
    }





}
