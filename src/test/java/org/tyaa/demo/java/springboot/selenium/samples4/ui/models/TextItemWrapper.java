package org.tyaa.demo.java.springboot.selenium.samples4.ui.models;

import java.util.List;

public class TextItemWrapper {

    public static String ROWS_DIMENSION = "ROWS";
    public static String COLUMNS_DIMENSION = "COLUMNS";

    public String range;
    public String majorDimension;
    public List<Object[]> values;

    public TextItemWrapper() {
    }

    public TextItemWrapper(String range, String majorDimension, List<Object[]> values) {
        this.range = range;
        this.majorDimension = majorDimension;
        this.values = values;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getMajorDimension() {
        return majorDimension;
    }

    public void setMajorDimension(String majorDimension) {
        this.majorDimension = majorDimension;
    }

    public List<Object[]> getValues() {
        return values;
    }

    public void setValues(List<Object[]> values) {
        this.values = values;
    }
}
