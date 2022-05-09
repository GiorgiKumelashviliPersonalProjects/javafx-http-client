package com.example.javafxhttpclient;

import com.example.javafxhttpclient.controllers.ContentController;
import com.example.javafxhttpclient.controllers.SidebarController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("AccessStaticViaInstance")
public class HttpClientController implements Initializable {
    @FXML
    SidebarController sidebarController;

    @FXML
    ContentController contentController;

    @FXML
    StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stackPane.setAlignment(contentController.anchorPane, Pos.TOP_RIGHT);
//
//        System.out.println(sidebarController.rootParent);
//        System.out.println(contentController.anchorPane);
    }
}