package com.example.javafxhttpclient.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HeaderInputsComponent extends VBox {
    @FXML
    HBox container;

    @FXML
    Button deleteButton;

    public HeaderInputsComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javafxhttpclient/reusableComponents/HeaderInputsComponent.fxml"));
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
        if (container.getParent() instanceof Pane) {
            ((Pane)container.getParent()).getChildren().remove(container);
        }
    }
}
