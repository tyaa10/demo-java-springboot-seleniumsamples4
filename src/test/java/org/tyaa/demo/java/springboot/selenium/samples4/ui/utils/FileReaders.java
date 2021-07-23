package org.tyaa.demo.java.springboot.selenium.samples4.ui.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;

public class FileReaders {
    public static Stream<String> readStrings(String filePath) {
        Stream<String> urls = null;
        try {
            BufferedReader reader =
                new BufferedReader(
                    new FileReader(
                        new File(filePath)
                            .getAbsoluteFile()
                    )
                );
            urls = reader.lines();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return urls;
    }
}
