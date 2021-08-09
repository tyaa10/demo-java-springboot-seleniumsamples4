package org.tyaa.demo.java.springboot.selenium.samples4.ui.utils;

import org.tyaa.demo.java.springboot.selenium.samples4.ui.models.TextItem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ITextStorage {
    Boolean connect(Map<String, String> resourceInfo);
    LinkedHashMap<String, TextItem> get(String pageKey) throws Exception;
    void set(String pageKey, String textKey) throws Exception;
    void set(String pageKey, LinkedHashMap<Integer, Boolean[]> results) throws Exception;
    Boolean close();
}
