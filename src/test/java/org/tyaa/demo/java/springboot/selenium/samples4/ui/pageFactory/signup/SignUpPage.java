package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.signup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.AbstractPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.AccountPage;

import java.util.List;

/* PageObject для страницы регистрации учетной записи */
public class SignUpPage extends AbstractPage {
    private final String BIRTHDAY_DAY_VALUE = "1";
    private final String BIRTHDAY_MONTH_VALUE = "1";
    @FindBy(id = "edit-firstname")
    private WebElement firstNameInput;
    @FindBy(id = "edit-lastname")
    private WebElement lastNameInput;
    // @FindBy(id = "language_preferences")
    private final By languagePreferencesSelect = By.id("language_preferences");;
    @FindBy(id = "edit-email")
    private WebElement emailInput;
    @FindBy(id = "edit-password")
    private WebElement passwordInput;
    @FindBy(id = "day")
    private WebElement daySelect;
    @FindBy(id = "month")
    private WebElement monthSelect;
    // @FindBy(css = "edit-stars .form-checkbox")
    // private WebElement starsCheckbox;
    // @FindBy(css = "edit-terms-of-use .form-checkbox")
    // private WebElement termsCheckbox;
    @FindBy(css = ".form-checkbox")
    private List<WebElement> checkboxes;
    @FindBy(id = "edit-submit-button")
    private WebElement submitButton;
    public SignUpPage(WebDriver driver) {
        super(driver);
        System.out.printf("%s Loaded", SignUpPage.class.getName());
    }
    public VerificationPage signUp(
        String firstName, String lastName,
        String languageCode,
        String email, String password
    ) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        switch (languageCode) {
            case "de_AT":
            case "en_AT": {
                selectLanguage(languageCode);
            }
        }
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        selectBirthday();
        clickCheckboxes();
        submitButton.click();
        return new VerificationPage(driver);
    }
    // Single Select Options: de_AT, en_AT
    public void selectLanguage(String languageCode) {
        Select languageSelect =
            new Select(driver.findElement(languagePreferencesSelect));
        languageSelect.selectByValue(languageCode);
    }
    public void selectBirthday() {
        Select birthdayDaySelect = new Select(daySelect);
        birthdayDaySelect.selectByValue(BIRTHDAY_DAY_VALUE);
        Select birthdayMonthSelect = new Select(monthSelect);
        birthdayMonthSelect.selectByValue(BIRTHDAY_MONTH_VALUE);
    }
    public void clickCheckboxes() {
        // starsCheckbox.click();
        // termsCheckbox.click();
        checkboxes.forEach(WebElement::click);
    }
}
