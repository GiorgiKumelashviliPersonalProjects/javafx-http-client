package com.example.javafxhttpclient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("AccessStaticViaInstance")
public class MainController implements Initializable {
    @FXML
    ContentController contentController;

    @FXML
    StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stackPane.setAlignment(contentController.anchorPane, Pos.TOP_RIGHT);
    }
}