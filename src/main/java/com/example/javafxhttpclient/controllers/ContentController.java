package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.utils.DragResizer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ContentController implements Initializable {
    @FXML
    public AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            DragResizer.makeResizable(anchorPane);
        });
    }
}
