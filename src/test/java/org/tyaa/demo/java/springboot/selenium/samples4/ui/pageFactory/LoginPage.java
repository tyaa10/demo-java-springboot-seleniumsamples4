package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/* PageObject для страницы входа в учетную запись */
public class LoginPage extends AbstractPage {
    private By errorMessageBlock = By.cssSelector(".error-message");
    // Элементы формы входа пытаемся найти сразу при обработке модели страницы
    // контейнером IoC Selenium-а,
    // потому что форма должна отображаться всегда сразу после загрузки
    // этой страницы
    @FindBy(css = "#edit-email")
    private WebElement emailInput;
    @FindBy(css = "#edit-password")
    private WebElement passwordInput;
    @FindBy(css = "#edit-submit")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        System.out.printf("%s Loaded", LoginPage.class.getName());
    }

    public boolean checkContent() {
        WebElement errorBlock = null;
        try {
            errorBlock = driver.findElement(errorMessageBlock);
        } catch (NoSuchElementException ex) {}
        System.out.println("account = " + driver.getCurrentUrl().endsWith("account/login"));
        return !(driver.getCurrentUrl().contains("403") || driver.getCurrentUrl().contains("404"))
            && errorBlock == null
            && driver.getCurrentUrl().endsWith("account/login");
    }

    public AccountPage signIn(String email, String password) throws InterruptedException {
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        // Thread.sleep(5000);
        submitButton.click();
        // Thread.sleep(1000000);
        return new AccountPage(driver);
    }
}
