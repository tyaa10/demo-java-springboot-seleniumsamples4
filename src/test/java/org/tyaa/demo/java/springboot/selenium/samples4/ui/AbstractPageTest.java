package org.tyaa.demo.java.springboot.selenium.samples4.ui;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.IndexPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.utils.GoogleSpreadSheetsTextStorage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.utils.ITextStorage;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPageTest {

    private static final Logger logger
        = LoggerFactory.getLogger(AbstractPageTest.class);
    private static BrowserDriverFactory driverFactory;
    protected static WebDriver driver;
    protected static ITextStorage textStorage;

    @BeforeAll
    private static void setupAll() {
        String browser = System.getProperty("browser");
        driverFactory =
            new BrowserDriverFactory(browser, logger);
        textStorage = new GoogleSpreadSheetsTextStorage();
        textStorage.connect(Map.of(
            GoogleSpreadSheetsTextStorage.RESOURCE_INFO_API_KEY,
            "AIzaSyCTltYrJFR_RPg0VvJWOU9kjLfSQcqGYAE",
            GoogleSpreadSheetsTextStorage.RESOURCE_INFO_SPREADSHEET_ID_KEY,
            "1j_VVROTAcI9CwIohEIMZFhqpYggoDFGiKiQssu7f-vQ",
            GoogleSpreadSheetsTextStorage.RESOURCE_INFO_ACCESS_TOKEN,
            "ya29.a0ARrdaM8GtbM_DcBKgvRAurItG-9XGLKO7WuQEFx0ZXfcZT1eVVSRnhxhWKf2VepLDV7rHOfhdUX3zohHy2CRZuVbyb346nGOyD2uKwCK1HeQ5JJOcLI7Qt7fFN6VaOyy6aW3WVnu2bmyXpXBU6d2wDj5IIKq"
        ));
    }

    @BeforeEach
    private void setupEach() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        driver = driverFactory.getDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterEach
    private void disposeEach() {
        driver.quit();
    }

    @AfterAll
    private void disposeAll() {
        textStorage.close();
    }

    protected IndexPage openStartPage(String startPageUrlString) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        driver.get(startPageUrlString);
        if (driver.manage().getCookies().size() > 0) {
            driver.manage().deleteAllCookies();
            System.out.println("All cookies have been deleted. Page reloading...");
            driver.navigate().refresh();
        }
        return new IndexPage(driver);
    }
}
