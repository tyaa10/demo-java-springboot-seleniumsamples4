package org.tyaa.demo.java.springboot.selenium.samples4.ui.models;

public class TextItem {

    private static Integer lastRowNumber = 1;

    private final Integer rowNumber;
    private String key;
    private String text;
    private Boolean passed;

    public TextItem() {
        this.rowNumber = ++lastRowNumber;
    }

    public TextItem(String key, String text, Boolean passed) {
        this.rowNumber = ++lastRowNumber;
        this.key = key;
        this.text = text;
        this.passed = passed;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public static void resetRowNumber() {
        TextItem.lastRowNumber = 1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }
}
