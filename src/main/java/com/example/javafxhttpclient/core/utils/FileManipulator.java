package com.example.javafxhttpclient.core.utils;

import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

@SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
public class FileManipulator {
    private static FileManipulator instance;

    private FileManipulator() {}

    public static FileManipulator getInstance() {
        if (instance == null) {
            instance = new FileManipulator();
        }

        return instance;
    }

    public static String getFileContent(String pathname) throws FileNotFoundException {
        if (pathname == null) {
            return "";
        }

        return new Scanner(new File(pathname)).useDelimiter("\\Z").next();
    }

    public static URL cssUrl(String pathname) {
        return getInstance()
                .getClass()
                .getResource(pathname);
    }

    public static String css(String pathname) {
        return Objects.requireNonNull(getInstance().getClass().getResource(pathname)).toExternalForm();
    }

    public static URL resource(String pathname) {
        return getInstance().getClass().getResource(pathname);
    }

    public static FXMLLoader fxmlLoader(String pathname) {
        return new FXMLLoader(getInstance().getClass().getResource(pathname));
    }
}
