package com.example.javafxhttpclient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class MainController implements Initializable {
    @FXML
    ContentController contentController;

    @FXML
    SidebarController sidebarController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}