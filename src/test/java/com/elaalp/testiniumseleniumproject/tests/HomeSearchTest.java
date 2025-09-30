package com.elaalp.testiniumseleniumproject.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.HomePage;

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
}
