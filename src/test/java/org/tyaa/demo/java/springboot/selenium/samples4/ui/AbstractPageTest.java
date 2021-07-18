package org.tyaa.demo.java.springboot.selenium.samples4.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.IndexPage;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPageTest {

    private static final Logger logger
        = LoggerFactory.getLogger(AbstractPageTest.class);
    private static BrowserDriverFactory driverFactory;
    protected static WebDriver driver;

    private IndexPage indexPage;

    @BeforeAll
    private static void setupAll() {
        String browser = System.getProperty("browser");
        driverFactory =
            new BrowserDriverFactory(browser, logger);
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

    protected void setIndexPage(String indexPageUrlString) {
        driver.get(indexPageUrlString);
        if (driver.manage().getCookies().size() > 0) {
            driver.manage().deleteAllCookies();
            System.out.println("All cookies have been deleted. Page reloading...");
            driver.navigate().refresh();
        }
        this.indexPage = new IndexPage(driver);
    }

    protected void setIndexPage(IndexPage indexPage) {
        this.indexPage = indexPage;
    }

    protected IndexPage getIndexPage() {
        return indexPage;
    }
}
