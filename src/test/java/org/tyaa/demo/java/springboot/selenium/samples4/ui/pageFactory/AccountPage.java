package org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.models.TextItem;

import java.util.Map;

/* PageObject для страницы учетной записи */
public class AccountPage extends AbstractPage {
    @FindBy(css = ".secondary-navigation span[data-icon='account']")
    private WebElement accountIcon;
    public AccountPage(WebDriver driver) {
        super(driver);
    }
    @Override
    public boolean checkContent() {
        return super.checkContent() && driver.getCurrentUrl().endsWith("account");
    }
}
