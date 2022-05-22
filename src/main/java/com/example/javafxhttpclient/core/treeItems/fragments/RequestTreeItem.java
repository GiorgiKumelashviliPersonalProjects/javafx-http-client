package com.example.javafxhttpclient.core.treeItems.fragments;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.Objects;

public class RequestTreeItem extends SavedRequestTreeItemAbstract {
    public RequestTreeItem(String name) {
        setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            System.out.println("handleDelete");
            e.consume();
        });
        MenuItem renameItem = new MenuItem("Rename");
        renameItem.setOnAction(e -> {
            System.out.println("handleRename");
            e.consume();
        });
        contextMenu.getItems().addAll(deleteItem, renameItem);

        // add context menu and actions
        return contextMenu;
    }

//    public void handleDelete() {
//        if (deleteItem != null) {
//            deleteItem.setOnAction(e -> {
//                System.out.println("handleDelete");
//                e.consume();
//            });
//        }
//    }
//
//    public void handleRename() {
//        if (renameItem != null) {
//            renameItem.setOnAction(e -> {
//                System.out.println("handleRename");
//                e.consume();
//            });
//        }
//    }
}
