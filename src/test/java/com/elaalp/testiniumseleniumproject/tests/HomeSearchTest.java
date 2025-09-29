package com.elaalp.testiniumseleniumproject.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeSearchTest extends BaseTest {

    @Test
    void siteAcilabiliyorMu() {
        String title = driver.getTitle();
        Assertions.assertFalse(title == null || title.isBlank(), "Sayfa başlığı boş geldi!");
    }
}
