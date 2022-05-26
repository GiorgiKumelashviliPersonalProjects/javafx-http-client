package com.example.javafxhttpclient.core.treeItems.fragments;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class FolderTreeItem extends SavedRequestTreeItemAbstract {
    private final int id;

    public FolderTreeItem(int id, String name) {
        this.id = id;
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

    public int getId() {
        return id;
    }
}
