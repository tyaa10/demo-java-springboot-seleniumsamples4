package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.signup;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.AbstractPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.AccountPage;

/* PageObject для страницы, отображающейся после регистрации учетной записи
* и подтверждения электронной почты */
public class SignUpWelcomePage extends AbstractPage {
    @FindBy(css = ".n05-verification")
    protected WebElement welcomeBlock;
    public SignUpWelcomePage(WebDriver driver) {
        super(driver);
        System.out.printf("%s Loaded", SignUpWelcomePage.class.getName());
    }
    @Override
    public boolean checkContent() {
        boolean isContentPresent = false;
        try {
            WebElement welcomeHeaderElement =
                welcomeBlock.findElement(By.cssSelector("h2"));
            WebElement welcomeSnippetElement =
                welcomeBlock.findElement(By.cssSelector("p"));
            isContentPresent =
                welcomeHeaderElement.isDisplayed() && welcomeSnippetElement.isDisplayed()
                    && !welcomeHeaderElement.getText().isBlank() && !welcomeSnippetElement.getText().isBlank();
        } catch (NoSuchElementException ignored) {}
        return super.checkContent() && isContentPresent;
    }
}
