package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/* PageObject для страницы учетной записи */
public class AccountPage extends AbstractPage {
    private By errorMessageBlock = By.cssSelector(".error-message");
    @FindBy(css = ".secondary-navigation span[data-icon='account']")
    private WebElement accountIcon;
    public AccountPage(WebDriver driver) {
        super(driver);
        System.out.printf("%s Loaded", AccountPage.class.getName());
    }
    public boolean checkContent() {
        WebElement errorBlock = null;
        try {
            errorBlock = driver.findElement(errorMessageBlock);
        } catch (NoSuchElementException ex) {}
        System.out.println("url = " + driver.getCurrentUrl());
        System.out.println("account = " + driver.getCurrentUrl().endsWith("account"));
        return !(driver.getCurrentUrl().contains("403") || driver.getCurrentUrl().contains("404"))
            && errorBlock == null
            && driver.getCurrentUrl().endsWith("account");
    }
}
