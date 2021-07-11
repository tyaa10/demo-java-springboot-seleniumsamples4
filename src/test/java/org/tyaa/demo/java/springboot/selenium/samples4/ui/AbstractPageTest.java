package org.tyaa.demo.java.springboot.selenium.samples4.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.IndexPage;

import java.util.concurrent.TimeUnit;

public abstract class AbstractPageTest {

    protected static WebDriver driver;

    private IndexPage indexPage;

    @BeforeAll
    private static void setupAll() {
        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
    }

    @BeforeEach
    private void setupEach() {
        driver = new FirefoxDriver();
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
