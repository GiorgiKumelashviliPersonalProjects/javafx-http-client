package com.example.javafxhttpclient.core.utils;

import com.example.javafxhttpclient.core.fxml.AlertModalController;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
    public static void showAlertModal(Alert.AlertType alertType, String text) {
        Stage stage = new Stage();
        FXMLLoader modalFxml = FileManipulator.fxmlLoader(Constants.showModal);
        String mainCss = FileManipulator.css(Constants.mainCss);
        Scene addTreeItemModalScene;

        try {
            addTreeItemModalScene = new Scene(modalFxml.load(), 500, 160);
            addTreeItemModalScene.getStylesheets().add(mainCss);

            AlertModalController alertModalController = modalFxml.getController();
            alertModalController.setTextColor(alertType);
            alertModalController.setText(text);

            stage.setScene(addTreeItemModalScene);

            // set title
            String title = "%s alert modal".formatted(alertType.toString().toLowerCase());
            stage.setTitle(title.substring(0, 1).toUpperCase() + title.substring(1));

            stage.initModality(Modality.WINDOW_MODAL);
            // stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStatusCodeText(int statusCode) {
        if (statusCode <= 199) {
            return "info";
        } else if (statusCode <= 299) {
            return "ok";
        } else if (statusCode <= 399) {
            return "redirection";
        } else if (statusCode <= 499) {
            return "error";
        } else {
            return "server err";
        }

        // Informational responses (100–199)
        // Successful responses (200–299)
        // Redirection messages (300–399)
        // Client error responses (400–499)
        // Server error responses (500–599)
    }

    public static String appendQueryToUrl(String uri, String appendQuery) {
        URI oldUri;

        try {
            oldUri = new URI(uri);

            String newQuery = oldUri.getQuery();
            if (newQuery == null) {
                newQuery = appendQuery;
            } else {
                newQuery += "&" + appendQuery;
            }

            return String.valueOf(new URI(
                    oldUri.getScheme(),
                    oldUri.getAuthority(),
                    oldUri.getPath(),
                    newQuery,
                    oldUri.getFragment()
            ));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isJsonValid(String inputJson) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        JsonFactory factory = mapper.getFactory();
        JsonParser parser;

        try {
            parser = factory.createParser(inputJson);
            mapper.readTree(parser);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isStringValid(String test) {
        return test != null && !test.isEmpty() && !test.isBlank();
    }

    public static int randInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max + 1);
    }

    public static int randIntBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
