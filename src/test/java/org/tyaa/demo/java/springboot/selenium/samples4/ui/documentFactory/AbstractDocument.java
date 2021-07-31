package org.tyaa.demo.java.springboot.selenium.samples4.ui.documentFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class AbstractDocument {
    protected Document doc;
    public AbstractDocument(String htmlDocumentString) {
        this.doc = Jsoup.parse(htmlDocumentString);
    }
}
