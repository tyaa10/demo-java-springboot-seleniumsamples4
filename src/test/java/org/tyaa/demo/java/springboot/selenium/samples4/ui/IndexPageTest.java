package org.tyaa.demo.java.springboot.selenium.samples4.ui;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.IndexPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

// @SpringJUnitConfig(IndexPageTest.Config.class)
// режим создания одиночного экземпляра класса тестов для всех кейсов
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// активация контейнера выполнения кейсов согласно пользовательской нумерации
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IndexPageTest extends AbstractPageTest {

    /* @Configuration
    static class Config {} */

    @ParameterizedTest
    @MethodSource("getIndexUrlStrings")
    @Order(1)
    public void givenIndexPage_whenLoaded_thenContentFound(String urlString) throws InterruptedException {
        System.out.printf("urlString = %s\n", urlString);
        assertNotNull(urlString);
        assertNotEquals("", urlString);
        setIndexPage(urlString);
        IndexPage newIndexPage = getIndexPage().agreeCookies();
        assertNotNull(newIndexPage);
        setIndexPage(newIndexPage);
        assertTrue(getIndexPage().checkContent());
    }

    // @EnabledIf("false")
    @ParameterizedTest
    @MethodSource("getIndexUrlStrings")
    @Order(2)
    public void givenIndexPage_whenNavigatingThroughAllThePages_thenSuccess(String urlString) throws InterruptedException {
        System.out.printf("urlString = %s\n", urlString);
        assertNotNull(urlString);
        assertNotEquals("", urlString);
        setIndexPage(urlString);
        IndexPage newIndexPage = getIndexPage().agreeCookies();
        assertNotNull(newIndexPage);
        setIndexPage(newIndexPage);
        try {
            getIndexPage().goThroughAllThePages();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    private Stream<String> getIndexUrlStrings() {
        Stream<String> urls = null;
        try {
            BufferedReader reader =
                new BufferedReader(
                    new FileReader(
                        new File("src/test/resources/urls/starbucks.txt")
                            .getAbsoluteFile()
                    )
                );
            urls = reader.lines();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return urls;
    }
}
