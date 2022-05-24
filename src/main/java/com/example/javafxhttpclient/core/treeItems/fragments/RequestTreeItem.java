package com.example.javafxhttpclient.core.treeItems.fragments;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class RequestTreeItem extends SavedRequestTreeItemAbstract {
    public RequestTreeItem(String name) {
        deleteItem = new MenuItem("Delete");
        renameItem = new MenuItem("Rename");

        setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(deleteItem, renameItem);

        // add context menu and actions
        return contextMenu;
    }
}
