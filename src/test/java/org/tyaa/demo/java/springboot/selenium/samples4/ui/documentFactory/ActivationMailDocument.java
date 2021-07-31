package org.tyaa.demo.java.springboot.selenium.samples4.ui.documentFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.pageFactory.signup.SignUpWelcomePage;

public class ActivationMailDocument extends AbstractDocument {
    private static final String ACTIVATE_ACCOUNT_HREF_SELECTOR = "a[href*='/account/verification']";
    private Element activateAccountHrefElement;
    public ActivationMailDocument(String htmlDocumentString) {
        super(htmlDocumentString);
        this.activateAccountHrefElement = this.doc.selectFirst(ACTIVATE_ACCOUNT_HREF_SELECTOR);
    }
    public String getActivateAccountHref() {
        return this.activateAccountHrefElement.attr("href");
    }
}
