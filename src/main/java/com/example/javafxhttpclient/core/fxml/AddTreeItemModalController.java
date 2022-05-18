package com.example.javafxhttpclient.core.fxml;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTreeItemModalController implements Initializable {
    @FXML
    public ComboBox<String> savedTreeItemTypeComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        savedTreeItemTypeComboBox.getItems().addAll(SavedTreeItemType.all());
    }

    public void onClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
