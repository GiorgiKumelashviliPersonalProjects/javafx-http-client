package com.example.javafxhttpclient.core.misc.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class RequestTreeItem extends SavedRequestTreeItemAbstract {
    private final int id;

    public RequestTreeItem(int id, String name) {
        this.id = id;
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

    public int getId() {
        return id;
    }
}
