package com.example.javafxhttpclient.core.treeItems.fragments;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class FolderTreeItem extends SavedRequestTreeItemAbstract {
    public FolderTreeItem(String name) {
        createItem = new MenuItem("Create Request");
        deleteItem = new MenuItem("Delete");
        renameItem = new MenuItem("Rename");

        setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(createItem, deleteItem, renameItem);

        // add context menu and actions
        return contextMenu;
    }
}
