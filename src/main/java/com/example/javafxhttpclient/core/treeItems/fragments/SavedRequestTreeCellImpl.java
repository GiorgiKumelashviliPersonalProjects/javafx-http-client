package com.example.javafxhttpclient.core.treeItems.fragments;

import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseButton;

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
            SavedRequestTreeItemAbstract treeItem = (SavedRequestTreeItemAbstract) getTreeItem();

            setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 1 && treeItem.getChildren().size() > 0) {
                    treeItem.setExpanded(!treeItem.isExpanded());
                }

                if (e.getButton().equals(MouseButton.SECONDARY)) {
                    treeItem.getMenu().show(getScene().getWindow(), e.getScreenX(), e.getScreenY());
                }

                e.consume();
            });
        }
    }

}