package com.example.javafxhttpclient.core.treeItems.fragments;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class RequestTreeItem extends SavedRequestTreeItemAbstr {
    public RequestTreeItem(String name) {
        setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem rename = new MenuItem("Rename");
        contextMenu.getItems().addAll(deleteItem, rename);

        // add context menu and actions
        return contextMenu;
    }
}
