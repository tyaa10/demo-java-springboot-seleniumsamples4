package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.signup;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.AbstractPage;

/* PageObject для страницы, отображающейся после регистрации учетной записи
* и подтверждения электронной почты */
public class EmailPreferencesUpdatedPage extends SignUpWelcomePage {
    public EmailPreferencesUpdatedPage(WebDriver driver) {
        super(driver);
    }
    @Override
    public boolean checkContent() {
        boolean isContentPresent = false;
        try {
            WebElement confirmEmailPreferencesElement =
                welcomeBlock.findElement(By.cssSelector("ul > li"));
            isContentPresent =
                confirmEmailPreferencesElement.isDisplayed()
                    && !confirmEmailPreferencesElement.getText().isBlank();
        } catch (NoSuchElementException ignored) {}
        return super.checkContent() && isContentPresent;
    }
}
