package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.entities.RequestEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class MainController implements Initializable {
    @FXML
    ContentController contentController;

    @FXML
    MenuBarController menuBarController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // temp
        RequestEntity root1 = new RequestEntity(1, SavedTreeItemType.FOLDER, "Simple api request");
        RequestEntity model11 = new RequestEntity(2, SavedTreeItemType.REQUEST, "Json test 1");
        RequestEntity model12 = new RequestEntity(3, SavedTreeItemType.REQUEST, "Json test 2");
        root1.setChildren(model11, model12);

        RequestEntity root2 = new RequestEntity(4, SavedTreeItemType.FOLDER, "Some folder");
        RequestEntity root3 = new RequestEntity(5, SavedTreeItemType.REQUEST, "testing");

        List<RequestEntity> requestEntities = new ArrayList<>();
        requestEntities.add(root1);
        requestEntities.add(root2);
        requestEntities.add(root3);

        //TODO create from here
        contentController.sidebarController.setEntities(requestEntities);
    }
}