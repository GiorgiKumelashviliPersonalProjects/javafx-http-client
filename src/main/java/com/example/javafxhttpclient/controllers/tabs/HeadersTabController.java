package com.example.javafxhttpclient.controllers.tabs;

import com.example.javafxhttpclient.components.HeaderInputsComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class HeadersTabController {
    @FXML
    VBox tabContent;

    public void onAddButtonClick(ActionEvent event) {
        HeaderInputsComponent headerInputsComponent = new HeaderInputsComponent();

        tabContent.getChildren().add(headerInputsComponent);
        System.out.println(event);
    }
}
