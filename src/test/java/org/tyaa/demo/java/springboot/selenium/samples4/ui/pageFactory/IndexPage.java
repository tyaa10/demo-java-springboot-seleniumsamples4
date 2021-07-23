package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/* PageObject для начальной страницы веб-приложения - index.html */
public class IndexPage extends AbstractPage {
    // для кнопки "Agree and Proceed" подготавливаем только селектор,
    // потому что она будет отображаться только при первом посещении
    // веб-приложения клиентом
    private By agreeAndProceedButton = By.cssSelector(".call");
    private By closeButton = By.cssSelector(".close");
    private By navLinks = By.cssSelector(".primary-navigation a");
    private By errorMessageBlock = By.cssSelector(".error-message");
    private By loginButton = By.cssSelector(".secondary-navigation .login-button > a");

    private Integer navLinksCount = 0;
    private Integer frameLevelCounter = 0;

    public IndexPage(WebDriver driver) {
        super(driver);
        System.out.println("IndexPage Loaded");
    }

    public IndexPage clickAgreeAndProceedButton() {
        WebElement button = null;
        try{
            driver.findElement(agreeAndProceedButton);
        } catch (NoSuchElementException ex) {}
        Integer frameCounter = 0;
        while(button == null && frameCounter <= 10) {
            System.out.println("CloseButton not found");
            try {
                driver.switchTo().frame(frameCounter);
                button = driver.findElement(agreeAndProceedButton);
                button.click();
            } catch (Exception ex) {
                driver.switchTo().parentFrame();
            }
            frameCounter++;
        }
        if (button == null && frameLevelCounter <= 10) {
            frameLevelCounter++;
            return clickCloseButton();
        }
        this.frameLevelCounter = 0;
        driver.switchTo().defaultContent();
        return new IndexPage(driver);
    }

    public IndexPage clickCloseButton() {
        WebElement button = null;
        try{
            driver.findElement(closeButton);
        } catch (NoSuchElementException ex) {}
        Integer frameCounter = 0;
        while(button == null && frameCounter <= 10) {
            System.out.println("CloseButton not found");
            try {
                driver.switchTo().frame(frameCounter);
                button = driver.findElement(closeButton);
                button.click();
            } catch (Exception ex) {
                driver.switchTo().parentFrame();
            }
            frameCounter++;
        }
        if (button == null && frameLevelCounter <= 10) {
            frameLevelCounter++;
            return clickCloseButton();
        }
        this.frameLevelCounter = 0;
        driver.switchTo().defaultContent();
        return new IndexPage(driver);
    }

    public IndexPage agreeCookies() {
        return clickAgreeAndProceedButton().clickCloseButton();
    }

    public void goThroughAllThePages() throws Exception {
        List<WebElement> navigationLinkElements = driver.findElements(navLinks);
        this.navLinksCount = navigationLinkElements.size();
        /* for (WebElement navLinkElement: navigationLinkElements) {
            navLinkElement.click();
            Thread.sleep(3000);
            driver.navigate().back();
        } */
        for (int i = 1; i <= this.navLinksCount; i++) {
            WebElement navLinkElement =
                 driver.findElement(
                     By.cssSelector(
                         String.format(".primary-navigation li:nth-child(%d)", i)
                     )
                 );
            navLinkElement = navLinkElement.findElement(By.cssSelector("a"));
            navLinkElement.click();
            Thread.sleep(1000);
            if (!checkContent()) {
                throw new Exception(String.format("Page %s Not Found", navLinkElement.getText()));
            }
            driver.navigate().back();
        }
    }

    public IndexPage clickNavLink() {
        driver.switchTo().frame(0);
        WebElement button = null;
        try {
            button = driver.findElement(closeButton);
            button.click();
        } catch (NoSuchElementException ex) {}
        driver.switchTo().defaultContent();
        return new IndexPage(driver);
    }

    public boolean checkContent() {
        WebElement errorBlock = null;
        try {
            errorBlock = driver.findElement(errorMessageBlock);
        } catch (NoSuchElementException ex) {}
        return !(driver.getCurrentUrl().contains("403") || driver.getCurrentUrl().contains("404")) && errorBlock == null;
    }

    public LoginPage clickLoginButton() {
        WebElement loginButtonElement = driver.findElement(loginButton);
        loginButtonElement.click();
        return new LoginPage(driver);
    }

    /* public String getLogOutButtonText() {
        List<WebElement> logOutButtonElement =
            driver.findElements(logOutButton);
        return !logOutButtonElement.isEmpty() ? logOutButtonElement.get(0).getText() : null;
    } */
}
