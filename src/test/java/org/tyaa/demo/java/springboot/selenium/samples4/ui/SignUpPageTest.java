package org.tyaa.demo.java.springboot.selenium.samples4.ui;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.AccountPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.IndexPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.LoginPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.signup.SignUpPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.signup.VerificationPage;
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
    @ParameterizedTest
    @MethodSource("getCredentialsArguments")
    @Order(1)
    public void givenSignUpPage_whenSignedUp_thenReceivedMail(
        String urlString, String firstName, String lastName,
        String languageCode, String email, String password
    ) throws MessagingException, IOException {
        /* System.out.println("STARTED");
        Message[] messages = Mailer.fetchUnreadMails();
        if (messages.length > 0) {
            Message message = messages[messages.length - 1];
            System.out.println(
                "sendDate: " + message.getSentDate()
                    + " subject:" + message.getSubject()
                    + " content:" + message.getContent()
            );
        }
        System.out.println("FINISHED"); */
        System.out.printf("urlString = %s\n", urlString);
        System.out.println("firstName = " + firstName);
        System.out.println("lastName = " + lastName);
        System.out.println("languageCode = " + languageCode);
        System.out.println("email = " + email);
        System.out.println("password = " + password);
        assertNotNull(urlString);
        assertNotEquals("", urlString);
        setIndexPage(urlString);
        IndexPage newIndexPage = getIndexPage().agreeCookies();
        assertNotNull(newIndexPage);
        SignUpPage signUpPage = newIndexPage.clickSignUpButton();
        assertNotNull(signUpPage);
        assertTrue(signUpPage.checkContent());
        VerificationPage verificationPage = null;
        try {
            verificationPage =
                signUpPage.signUp(firstName, lastName, languageCode, email, password);
        } catch (Exception ex) {
            fail("Registration failed, verification page not found, or content is incorrect ");
        }
        assertNotNull(verificationPage);
        assertTrue(verificationPage.checkContent());
    }
    private Stream<Arguments> getCredentialsArguments() {
        return FileReaders.readStrings("src/test/resources/credentials/signup.local")
            .map(s -> {
                String[] arguments = s.split(" ");
                return Arguments.of(
                    arguments[0], arguments[1], arguments[2],
                    arguments[3], arguments[4], arguments[5]
                );
            });
    }
}
