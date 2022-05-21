package com.example.javafxhttpclient.core.utils;

import com.example.javafxhttpclient.core.fxml.AlertModalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Util {
    public static void showAlertModal(ActionEvent event, Alert.AlertType alertType, String text) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modalFxml = FileManipulator.fxmlLoader(Constants.showModal);
        String mainCss = FileManipulator.css(Constants.mainCss);

        Scene addTreeItemModalScene = new Scene(modalFxml.load(), 500, 160);
        addTreeItemModalScene.getStylesheets().add(mainCss);

        AlertModalController alertModalController = modalFxml.getController();
        alertModalController.setTextColor(alertType);
        alertModalController.setText(text);

        stage.setScene(addTreeItemModalScene);

        // set title
        String title ="%s alert modal".formatted(alertType.toString().toLowerCase());
        stage.setTitle(title.substring(0, 1).toUpperCase() + title.substring(1));

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }
}
