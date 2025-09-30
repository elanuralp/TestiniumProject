package com.elaalp.testiniumseleniumproject.tests;

import core.Config;
import core.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import pages.HomePage;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        logger.info("Test başlıyor...");
        driver = DriverFactory.getDriver();
        driver.get(Config.get("baseUrl"));
        logger.info("URL açıldı: {}", Config.get("baseUrl"));
        new HomePage().closePopups();

    }


    @AfterEach
    public void tearDown() {
        logger.info("Test bitiyor, driver kapatılıyor...");
        DriverFactory.quitDriver();
    }
}
