package com.example.javafxhttpclient.core.misc.treeItems;

import com.example.javafxhttpclient.controllers.SidebarController;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseButton;

public class SavedRequestTreeCellImpl extends TreeCell<String> {
    SidebarController sidebarController;

    public SavedRequestTreeCellImpl(SidebarController sidebarController) {
        this.sidebarController = sidebarController;
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            SavedRequestTreeItemAbstract treeItem = (SavedRequestTreeItemAbstract) getTreeItem();
            setText(getItem() == null ? "" : getItem());
            setGraphic(treeItem.getGraphic());

            // treeItem.setExpanded(true);

            // set context menu item actions here
            if (treeItem.createItem != null) treeItem.createItem.setOnAction(sidebarController::create);
            if (treeItem.renameItem != null) treeItem.renameItem.setOnAction(sidebarController::rename);
            if (treeItem.deleteItem != null) treeItem.deleteItem.setOnAction(sidebarController::delete);

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

//        // out of focus
//        focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                System.out.println("lost focus on tree item");
//                sidebarController.rootParent.requestFocus();
////                rootParent
//
//            }
//        });
    }
}


// implementation of renaming inside tree item by using inline text field
//    private void renameAction(SavedRequestTreeItemAbstract treeItem) {
//        String beforeText = getItem();
//        TextField tempTextField = new TextField();
//
//        // remove label
//        setText(null);
//        tempTextField.setText(beforeText);
//
//        // on enter
//        tempTextField.setOnAction(ex -> {
//            setText(Util.isStringValid(tempTextField.getText()) ? tempTextField.getText() : beforeText);
//            setGraphic(treeItem.getGraphic());
//        });
//
//        // out of focus
//        tempTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                setText(Util.isStringValid(tempTextField.getText()) ? tempTextField.getText() : beforeText);
//                setGraphic(treeItem.getGraphic());
//            }
//        });
//
//        setGraphic(tempTextField);
//        tempTextField.requestFocus();
//        tempTextField.end();
//
//        System.out.println("double click");
//    }