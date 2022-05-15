package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.HeaderInputsComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class TabsController {
    @FXML
    VBox tabContent;

    public void onAddButtonClick(ActionEvent event) {
        HeaderInputsComponent headerInputsComponent = new HeaderInputsComponent();

        tabContent.getChildren().add(headerInputsComponent);
        System.out.println(event);
    }
}
