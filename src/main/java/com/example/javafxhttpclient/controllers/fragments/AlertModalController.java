package com.example.javafxhttpclient.controllers.fragments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertModalController implements Initializable {
    private static final int MAX_WIDTH = 490;

    @FXML
    Text alertText;

    public void onClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertText.setWrappingWidth(MAX_WIDTH);
        alertText.maxWidth(MAX_WIDTH);
        alertText.setFont(Font.font(20));
    }

    public void setTextColor(Alert.AlertType alertType) {
        switch (alertType) {
            case ERROR -> alertText.setFill(Color.valueOf("#ff9494"));
            case WARNING -> alertText.setFill(Color.valueOf("#fdff8c"));
            case INFORMATION -> alertText.setFill(Color.valueOf("#69c5ff"));
            default -> alertText.setFill(Color.WHITE);
        }
    }

    public void setText(String text) {
        alertText.setText(text);
    }
}
