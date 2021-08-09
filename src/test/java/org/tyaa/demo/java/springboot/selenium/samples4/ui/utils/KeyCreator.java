package org.tyaa.demo.java.springboot.selenium.samples4.ui.utils;

public class KeyCreator {
    public static String urlToKey(String url, String baseUrl) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url.replace(baseUrl, "").replace("/", "_");
    }
}
