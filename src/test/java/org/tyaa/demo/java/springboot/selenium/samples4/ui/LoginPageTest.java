package org.tyaa.demo.java.springboot.selenium.samples4.ui;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.AccountPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.IndexPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.LoginPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.utils.FileReaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginPageTest extends AbstractPageTest {
    @ParameterizedTest
    @MethodSource("getCredentialsArguments")
    @Order(1)
    public void givenLoginPage_whenSignedIn_thenSignedContentFound(
        String localeCode, String email, String password
    ) throws InterruptedException {
        String urlString = "https://www.starbucks.co.uk/";
        if (!localeCode.equals("uk")) {
            urlString = String.format("https://www.starbucks.%s/", localeCode);
        }
        System.out.printf("urlString = %s\n", urlString);
        System.out.println("email = " + email);
        System.out.println("password = " + password);
        assertNotNull(urlString);
        assertNotEquals("", urlString);
        setIndexPage(urlString);
        IndexPage newIndexPage = getIndexPage().agreeCookies();
        assertNotNull(newIndexPage);
        LoginPage loginPage = newIndexPage.clickLoginButton();
        assertNotNull(loginPage);
        assertTrue(loginPage.checkContent());
        AccountPage accountPage = null;
        try {
            accountPage = loginPage.signIn(email, password);
        } catch (Exception ex) {
            fail("Login failed, account page not found, or content is incorrect ");
        }
        assertNotNull(accountPage);
        assertTrue(accountPage.checkContent());

    }
    private Stream<Arguments> getCredentialsArguments() {
        return FileReaders.readStrings("src/test/resources/credentials/login.txt")
            .map(s -> {
                String[] arguments = s.split(" ");
                return Arguments.of(arguments[0].toLowerCase(), arguments[1], arguments[2]);
            });
    }
}
