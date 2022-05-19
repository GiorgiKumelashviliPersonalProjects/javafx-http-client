package com.example.javafxhttpclient.core.utils;

import javafx.scene.control.Alert;

public class Util {
    public static void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);

        // show the dialog
        alert.show();
    }
}
