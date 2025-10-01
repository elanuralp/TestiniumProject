package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.Objects;

public class SearchResultsPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(SearchResultsPage.class);


    private final By rerunSearchBtn = By.xpath("//button[.//span[normalize-space()='Yeniden Ara']]");
    private final By productCountHeader = By.cssSelector("h3#urun-sayisi");

    private final By regionFilters = By.cssSelector("div[data-testid='checkbox']");

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
}
