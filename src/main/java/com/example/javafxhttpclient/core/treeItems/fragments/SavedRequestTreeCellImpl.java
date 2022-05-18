package com.example.javafxhttpclient.core.treeItems.fragments;

import javafx.scene.control.TreeCell;

public class SavedRequestTreeCellImpl extends TreeCell<String> {
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem() == null ? "" : getItem());
            setGraphic(getTreeItem().getGraphic());
            setContextMenu(((SavedRequestTreeItemAbstr) getTreeItem()).getMenu());
        }
    }
}
