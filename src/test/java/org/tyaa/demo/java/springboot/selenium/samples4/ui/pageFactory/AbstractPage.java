package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractPage {
    protected WebDriver driver;
    private By errorMessageBlock = By.cssSelector(".error-message");
    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        // запуск контейнера внедрения зависимостей -
        // ссылок на веб-элементы, найденные по селекторам аннотаций @FindBy
        PageFactory.initElements(driver, this);
    }
    public boolean checkContent() {
        WebElement errorBlock = null;
        try {
            errorBlock = driver.findElement(errorMessageBlock);
        } catch (NoSuchElementException ex) {}
        return !(driver.getCurrentUrl().contains("403") || driver.getCurrentUrl().contains("404")) && errorBlock == null;
    }
}
