package org.tyaa.demo.java.springboot.selenium.samples4.ui;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BrowserDriverFactory {

    private static Map<String, String> browsers;

    private String browser;
    private Logger log;

    static {
        /*
            Key - Driver File Name
            Value - Driver Class Name
         */
        // new HashMap<>();
        // .put(key, value);

        // Map<String, Object> demoMap = new HashMap<>();
        // new Stream<String>().forEach(s -> System.out.println(s));

        BrowserDriverFactory.browsers =
            Map.of(
                "chrome", "chrome",
                "gecko", "firefox"
            );
    }

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = !browser.isBlank() ? browser.toLowerCase() : "chrome";
        if (!BrowserDriverFactory.browsers.containsKey(this.browser)) {
            throw new IllegalArgumentException(String.format("Driver %s Not Found", this.browser));
        }
        this.log = log;
        this.log.info("Set driver: " + this.browser);
        System.setProperty(
            String.format("webdriver.%s.driver", this.browser),
            String.format("drivers/%sdriver", this.browser)
        );
    }

    public WebDriver getDriver() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String browserClassName = BrowserDriverFactory.browsers.get(this.browser);
        Class<?> browserDriverClass =
            Class.forName(String.format(
                "org.openqa.selenium.%s.%sDriver",
                browserClassName,
                StringUtils.capitalize(browserClassName))
            );
        /*
        *
        * for (Constructor<?> constructor : browserDriverClass.getConstructors()) {
            if (!constructor.isVarArgs()) {
                browserDriverClassConstructor = constructor;
            }
        }
        assert browserDriverClassConstructor != null;
        return (WebDriver) browserDriverClassConstructor.newInstance((Object) null);
        *
        * */
        return (WebDriver) browserDriverClass.getConstructor().newInstance(null);
    }
}
