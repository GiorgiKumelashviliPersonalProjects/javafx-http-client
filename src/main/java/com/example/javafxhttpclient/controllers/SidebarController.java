package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.treeItems.SavedRequestTreeItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class SidebarController implements Initializable {
    @FXML
    public AnchorPane rootParent;

    @FXML
    public TreeView<String> savedRequests;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SavedRequestTreeItem root1 = new SavedRequestTreeItem(SavedTreeItemType.FOLDER, "Simple api request");
        SavedRequestTreeItem root2 = new SavedRequestTreeItem(SavedTreeItemType.FOLDER, "Some folder");
        SavedRequestTreeItem root3 = new SavedRequestTreeItem(SavedTreeItemType.REQUEST, "testing");

        SavedRequestTreeItem model11 = new SavedRequestTreeItem(SavedTreeItemType.REQUEST, "Json test 1");
        SavedRequestTreeItem model12 = new SavedRequestTreeItem(SavedTreeItemType.REQUEST, "Json test 2");
        root1.setChildren(model11, model12);

        // set root which is not visible
        TreeItem<String> invisibleRoot = new TreeItem<>(null);
        invisibleRoot.getChildren().addAll(root1.getItem(), root2.getItem(), root3.getItem());
        savedRequests.setRoot(invisibleRoot);
        savedRequests.setShowRoot(false);

        // add context menu to whole tree view
        addContextMenu();
    }

    public void addContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("hello1");
        MenuItem item2 = new MenuItem("hello2");
        MenuItem item3 = new MenuItem("hello3");
        MenuItem item4 = new MenuItem("hello4");

        contextMenu.getItems().addAll(item1, item2, item3, item4);
        savedRequests.setContextMenu(contextMenu);
    }
}
