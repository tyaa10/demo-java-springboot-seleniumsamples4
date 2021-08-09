package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.models.TextItem;

import java.util.Map;

/* PageObject для страницы входа в учетную запись */
public class LoginPage extends AbstractPage {
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
    }
    @Override
    public boolean checkContent() {
        return super.checkContent() && driver.getCurrentUrl().endsWith("account/login");
    }
    public AccountPage signIn(String email, String password) {
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        submitButton.click();
        return new AccountPage(driver);
    }
}
