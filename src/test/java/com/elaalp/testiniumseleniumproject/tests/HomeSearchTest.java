package com.elaalp.testiniumseleniumproject.tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import java.time.LocalDate;

public class HomeSearchTest extends BaseTest {

    @Test
    void siteAcilabiliyorMu() {
        String title = driver.getTitle();
        Assertions.assertFalse(title == null || title.isBlank(), "Sayfa başlığı boş geldi!");
    }

    @Test
    void isHotelTabDefaultSelected() {
        HomePage homePage = new HomePage();
        Assertions.assertTrue(homePage.isHotelTabDefault(), "Otel sekmesi seçili değil!");
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

        Assertions.assertTrue(
                home.verifyAprilDates(year),
                "Tarih alanında beklenen aralık yok: 01.04." + year + " - 08.04." + year
        );
    }










}
