package com.example.javafxhttpclient.controllers.fragments;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTreeItemModalController implements Initializable {
    @FXML
    public ComboBox<String> savedTreeItemTypeComboBox;

    @FXML
    public TextField savedTreeItemTextField;

    @FXML
    public Button onAddButton;

    @FXML
    public Button onCloseButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         savedTreeItemTypeComboBox.getItems().addAll(SavedTreeItemType.all());
    }
}
