package com.example.javafxhttpclient.core.treeItems.fragments;

import com.example.javafxhttpclient.controllers.SidebarController;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeCell;

public class SavedRequestTreeCellImpl extends TreeCell<String> {
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            setText(getItem() == null ? "" : getItem());
            setGraphic(getTreeItem().getGraphic());
            SavedRequestTreeItemAbstract treeItem = (SavedRequestTreeItemAbstract) getTreeItem();

//            System.out.println(getTreeItem());
//            System.out.println(getTreeItem().getClass());
//            System.out.println(treeItem instanceof RequestTreeItem);
//
//            if (treeItem instanceof RequestTreeItem) {
//                ((RequestTreeItem) treeItem).handleDelete();
//                ((RequestTreeItem) treeItem).handleRename();
//            }

            // more smooth
            setOnContextMenuRequested(e -> {
                treeItem.getMenu().show(getScene().getWindow(), e.getScreenX(), e.getScreenY());
            });
        }
    }

}