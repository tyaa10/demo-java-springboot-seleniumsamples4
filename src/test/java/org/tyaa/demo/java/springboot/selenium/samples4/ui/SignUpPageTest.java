package org.tyaa.demo.java.springboot.selenium.samples4.ui;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.AccountPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.IndexPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.LoginPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.utils.FileReaders;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.utils.Mailer;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignUpPageTest extends AbstractPageTest {
    @Test
    public void givenSignUpPage_whenSignedUp_thenReceivedMail() throws MessagingException, IOException {
        System.out.println("STARTED");
        /* for(Message message : Mailer.fetchUnreadMails()) {
            System.out.println(
                "sendDate: " + message.getSentDate()
                    + " subject:" + message.getSubject()
                    + " content:" + message.getContent()
            );
        } */
        Message[] messages = Mailer.fetchUnreadMails();
        if (messages.length > 0) {
            Message message = messages[messages.length - 1];
            System.out.println(
                "sendDate: " + message.getSentDate()
                    + " subject:" + message.getSubject()
                    + " content:" + message.getContent()
            );
        }
        System.out.println("FINISHED");
    }
    /* @ParameterizedTest
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
    } */
}
