package org.tyaa.demo.java.springboot.selenium.samples4.ui;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.documentFactory.ActivationMailDocument;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.IndexPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.signup.SignUpPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.signup.SignUpWelcomePage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.signup.VerificationPage;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.utils.FileReaders;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.utils.Mailer;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
    ) throws MessagingException, IOException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // 1. вход на форму регистрации и ее отправка
        System.out.printf("urlString = %s\n", urlString);
        System.out.println("firstName = " + firstName);
        System.out.println("lastName = " + lastName);
        System.out.println("languageCode = " + languageCode);
        System.out.println("email = " + email);
        System.out.println("password = " + password);
        assertNotNull(urlString);
        assertNotEquals("", urlString);
        IndexPage newStartPage =
            openStartPage(urlString).agreeCookies();
        assertNotNull(newStartPage);
        SignUpPage signUpPage = newStartPage.clickSignUpButton();
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
        // 2. чтение предпоследнего непросмотренного письма из почтового ящика,
        // адрес которого был указан при регистрации
        /* System.out.println("Waiting for mails: 10 seconds...");
        Thread.sleep(10000); */
        Message[] messages = Mailer.fetchUnreadMails();
        if (messages.length < 2) {
            fail("Post-Sign Up messages not received");
        }
        // Message emailPreferencesMessage = messages[messages.length - 1];
        Message activationMessage = messages[messages.length - 2];
        System.out.println(
            "sendDate: " + activationMessage.getSentDate()
                + "\nsubject: " + activationMessage.getSubject()
                + "\ncontent: " + activationMessage.getContent()
        );
        String activationMessageText = activationMessage.getContent().toString();
        assertNotEquals(true, activationMessageText.isBlank());
        // 3. поиск ссылки активации аккаунта в теле письма,
        // переход по ней и проверка страницы приветствия
        ActivationMailDocument activationMailDocument =
            new ActivationMailDocument(activationMessageText);
        assertNotNull(activationMailDocument);
        String activateAccountHref = activationMailDocument.getActivateAccountHref();
        assertNotEquals(true, activateAccountHref.isBlank());
        driver.get(activateAccountHref);
        SignUpWelcomePage welcomePage = null;
        try {
            welcomePage = new SignUpWelcomePage(driver);
        } catch (Exception ex) {
            fail("Account activation failed, welcome page not found, or content is incorrect ");
        }
        assertNotNull(welcomePage);
        assertTrue(welcomePage.checkContent());
    }
    /* @Test
    public void checkSampleActivateAccountMail() {
        ActivationMailDocument activationMailDocument =
            new ActivationMailDocument(getSampleActivateAccountMailDoc());
        assertNotNull(activationMailDocument);
        String activateAccountHref = activationMailDocument.getActivateAccountHref();
        assertNotEquals(true, activateAccountHref.isBlank());
        driver.get(activateAccountHref);
        SignUpWelcomePage welcomePage = null;
        try {
            welcomePage = new SignUpWelcomePage(driver);
        } catch (Exception ex) {
            fail("Account activation failed, welcome page not found, or content is incorrect ");
        }
        assertNotNull(welcomePage);
        assertNotNull(welcomePage);
        assertTrue(welcomePage.checkContent());
    } */
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
    /* private String getSampleActivateAccountMailDoc() {
        return FileReaders.readString("src/test/resources/samples/activate_account_mail.html");
    } */
}
