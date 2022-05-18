package com.example.javafxhttpclient.core.treeItems.fragments;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class RequestTreeItem extends SavedRequestTreeItemAbstr {
    public RequestTreeItem(String name) {
        setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete");
        contextMenu.getItems().addAll(deleteItem);

        // add context menu and actions
        return contextMenu;
    }
}
