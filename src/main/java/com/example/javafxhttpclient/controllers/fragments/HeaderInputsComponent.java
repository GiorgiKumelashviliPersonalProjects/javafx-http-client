package com.example.javafxhttpclient.controllers.fragments;

import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.core.utils.FileManipulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;

public class HeaderInputsComponent extends VBox {
    @FXML
    VBox root;

    @FXML
    Button deleteButton;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField valueTextField;

    public HeaderInputsComponent() {
        FXMLLoader fxmlLoader = FileManipulator.fxmlLoader(Constants.headerInputComponent);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        deleteButton.setOnAction(this::deleteMySelf);
    }

    public void deleteMySelf(ActionEvent event) {
        if (root.getParent() instanceof Pane) {
            ((Pane) root.getParent()).getChildren().remove(root);
        }
    }

    public Pair<String, String> getNameValue() {
        return new Pair<>(nameTextField.getText(), valueTextField.getText());
    }
}
