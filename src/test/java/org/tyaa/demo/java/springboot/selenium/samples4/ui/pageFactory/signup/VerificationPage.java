package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.signup;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/* PageObject для страницы, отображающейся после начала регистрации учетной записи
* и сообщающей о необходимости подтверждения электронной почты */
public class VerificationPage extends SignUpWelcomePage {
    public VerificationPage(WebDriver driver) {
        super(driver);
        System.out.printf("%s Loaded", VerificationPage.class.getName());
    }
    @Override
    public boolean checkContent() {
        return super.checkContent() && driver.getCurrentUrl().endsWith("account/verification");
    }
}
