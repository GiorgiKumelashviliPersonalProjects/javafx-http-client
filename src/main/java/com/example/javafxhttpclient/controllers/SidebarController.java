package com.example.javafxhttpclient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class SidebarController implements Initializable {
    @FXML
    public AnchorPane rootParent;

    @FXML
    public TreeView<String> savedRequests;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> root1 = new TreeItem<>("Simple api request");
        TreeItem<String> root2 = new TreeItem<>("Projects");

        TreeItem<String> model11 = new TreeItem<>("Json test 1");
        TreeItem<String> model12 = new TreeItem<>("Json test 2");

        root1.getChildren().add(model11);
        root1.getChildren().add(model12);

        // for showing multiple roots on tree view
        TreeItem<String> invisibleRoot = new TreeItem<>(null);
        invisibleRoot.getChildren().addAll(root1, root2);
        savedRequests.setRoot(invisibleRoot);
        savedRequests.setShowRoot(false);
    }
}
