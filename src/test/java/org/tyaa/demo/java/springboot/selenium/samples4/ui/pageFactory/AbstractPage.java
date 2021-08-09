package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.models.TextItem;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPage {
    protected WebDriver driver;
    private final By errorMessageBlock = By.cssSelector(".error-message");
    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        // запуск контейнера внедрения зависимостей -
        // ссылок на веб-элементы, найденные по селекторам аннотаций @FindBy
        PageFactory.initElements(driver, this);
        System.out.printf("%s Loaded\n", this.getClass().getName());
    }
    public boolean checkContent() {
        WebElement errorBlock = null;
        try {
            errorBlock = driver.findElement(errorMessageBlock);
        } catch (NoSuchElementException ignored) {}
        return !(driver.getCurrentUrl().contains("403") || driver.getCurrentUrl().contains("404")) && errorBlock == null;
    }
    public LinkedHashMap<String, TextItem> checkContent(LinkedHashMap<String, TextItem> textItems) {
        textItems.forEach((key, textItem) -> {
            try {
                String expectedText = textItem.getText();
                Field webElementLocatorField = this.getClass().getDeclaredField(key);
                webElementLocatorField.setAccessible(true);
                By elementLocator = (By) webElementLocatorField.get(this);
                WebElement element = driver.findElement(elementLocator);
                String actualText = element.getText().trim();
                System.out.printf("expectedText: %s, actualText: %s\n", expectedText, actualText);
                textItem.setPassed(expectedText.equals(actualText));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
        return textItems;
    }
}
