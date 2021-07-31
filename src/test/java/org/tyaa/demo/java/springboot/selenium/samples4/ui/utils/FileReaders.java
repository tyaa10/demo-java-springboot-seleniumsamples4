package org.tyaa.demo.java.springboot.selenium.samples4.ui.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReaders {
    public static String readString(String filePath) {
        String string = "";
        try {
            string = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }
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
