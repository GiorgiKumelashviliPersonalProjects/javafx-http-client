package com.example.javafxhttpclient.core.treeItems.fragments;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class FolderTreeItem extends SavedRequestTreeItemAbstr {
    public FolderTreeItem(String name) {
        setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem createNewRequest = new MenuItem("Create Request");
        contextMenu.getItems().addAll(createNewRequest, deleteItem);

        // add context menu and actions
        return contextMenu;
    }
}
