package org.tyaa.demo.java.springboot.selenium.samples4.ui;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.models.TextItem;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.IndexPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.utils.FileReaders;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.utils.KeyCreator;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
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
    public void givenIndexPage_whenLoaded_thenContentFound(String urlString) throws Exception {
        System.out.printf("urlString = %s\n", urlString);
        assertNotNull(urlString);
        assertNotEquals("", urlString);
        IndexPage newStartPage =
            openStartPage(urlString).agreeCookies();
        assertNotNull(newStartPage);
        assertTrue(
            (urlString.contains("403") || urlString.contains("404"))
                != newStartPage.checkContent()
        );
        LinkedHashMap<Integer, Boolean[]> checkContentResults = newStartPage.checkContent(
            textStorage.get(KeyCreator.urlToKey(urlString, "https://www.starbucks."))
        ).values()
            .stream()
            .collect(
                Collectors.toMap(
                    TextItem::getRowNumber,
                    textItem -> new Boolean[]{ textItem.getPassed() },
                    (oldValue, newValue) -> oldValue, LinkedHashMap::new)
            );
        if (checkContentResults.size() > 0) {
            textStorage.set(
                KeyCreator.urlToKey(urlString, "https://www.starbucks."),
                checkContentResults
            );
        }
        checkContentResults.forEach((rowNumber, passed) -> {
            System.out.printf("Text #%s is correct: %s\n", rowNumber, Arrays.toString(passed));
            if (!passed[0]) {
                fail("Wrong content");
            }
        });
        // assertTrue(checkContentResults.containsValue(new Boolean[]{false}), "Wrong content");
    }

    // @EnabledIf("false")
    @ParameterizedTest
    @MethodSource("getIndexUrlStrings")
    @Order(2)
    public void givenIndexPage_whenNavigatingThroughAllThePages_thenSuccess(String urlString) throws InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.printf("urlString = %s\n", urlString);
        assertNotNull(urlString);
        assertNotEquals("", urlString);
        IndexPage newStartPage =
            openStartPage(urlString).agreeCookies();
        assertNotNull(newStartPage);
        try {
            newStartPage.goThroughAllThePages();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    private Stream<String> getIndexUrlStrings() {
        return FileReaders.readStrings("src/test/resources/urls/starbucks.txt");
    }
}
