package com.example.javafxhttpclient.controllers.fragments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RenameTreeItemModalController implements Initializable {
    @FXML
    public TextField savedTreeItemTextField;

    @FXML
    public Button onAddButton;

    @FXML
    public Button onCloseButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set focus on text field
        savedTreeItemTextField.requestFocus();
        savedTreeItemTextField.end();
    }
}
