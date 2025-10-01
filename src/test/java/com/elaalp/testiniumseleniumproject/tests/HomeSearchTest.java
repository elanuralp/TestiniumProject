package com.elaalp.testiniumseleniumproject.tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeSearchTest extends BaseTest {
    @Test
    void siteAcilabiliyorMu() {
        String title = driver.getTitle();
        Assertions.assertFalse(title == null || title.isBlank(), "Sayfa başlığı boş geldi!");
    }
    @Test
    void isHotelTabDefaultSelected() {
        HomePage homePage = new HomePage();
        assertTrue(homePage.isHotelTabDefault(), "Otel sekmesi seçili değil!");
    }
    @Test
    void antalyaCsvdenYazilirveTiklanir() {
        HomePage home = new HomePage();
        home.typeDestinationFromCsvAndSelect("src/test/resources/testdata/locations.csv");
    }
    @Test
    void nisanIlkHaftaSecimi() {
        HomePage home = new HomePage();
        home.pickApril1to8();

        // en yakın yıl bilgisini al
        int year = (LocalDate.now().getMonthValue() <= 4)
                ? LocalDate.now().getYear()
                : LocalDate.now().getYear() + 1;
        assertTrue(
                home.verifyAprilDates(year),
                "Tarih alanında beklenen aralık yok: 01.04." + year + " - 08.04." + year
        );
    }
    @Test
    void yetiskinSayisiArtirilir() {
        HomePage home = new HomePage();
        home.incrementAdultAndVerify();
    }

    @Test
    void clickSearchAndVerifyResults() {
        HomePage home = new HomePage();
        home.typeDestinationFromCsvAndSelect("src/test/resources/testdata/locations.csv");
        home.pickApril1to8();
        home.incrementAdultAndVerify();
        home.clickSearchButton();
        SearchResultsPage searchResultsPage = new SearchResultsPage().waitUntilReady();
        assertTrue(
                searchResultsPage.verifyUrlContains("antalya"),
                "URL 'antalya' içermiyor!"
        );
        int regionCount = searchResultsPage.selectRandomRegionAndGetCount(); // rastgele bölge seç ve parantezdeki sayıyı kaydet
        searchResultsPage.waitUntilHeaderCountUpdates(regionCount);
        int newHeaderCount = searchResultsPage.getHeaderCount();
        Assertions.assertEquals(
                regionCount,
                newHeaderCount,
                "Filtre sonrası ürün sayısı eşleşmiyor!"
        );
    }
}



