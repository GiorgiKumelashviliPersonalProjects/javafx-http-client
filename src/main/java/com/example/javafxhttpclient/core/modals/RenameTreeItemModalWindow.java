package com.example.javafxhttpclient.core.modals;

import com.example.javafxhttpclient.controllers.fragments.RenameTreeItemModalController;
import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.core.utils.FileManipulator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RenameTreeItemModalWindow {
    private String newName;
    private String oldName;

    public RenameTreeItemModalWindow() {
    }

    public void show(TreeItemModalWindowCallback callback) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modalFxml = FileManipulator.fxmlLoader(Constants.renameSavedRequestTreeItem);
        String mainCss = FileManipulator.css(Constants.mainCss);
        Scene renameTreeItemModalScene = new Scene(modalFxml.load());

        RenameTreeItemModalController renameTreeItemModalController = modalFxml.getController();

        if (oldName != null) {
            renameTreeItemModalController.savedTreeItemTextField.setText(oldName);
        }

        // on save
        renameTreeItemModalController.onAddButton.setOnAction(e -> {
            // set data
            newName = renameTreeItemModalController.savedTreeItemTextField.getText();

            // close window and callback onClose
            stage.close();
            callback.onClose();
        });

        // on close
        renameTreeItemModalController.onCloseButton.setOnAction(e -> {
            stage.close();
            callback.onClose();
        });

        renameTreeItemModalScene.getStylesheets().add(mainCss);
        stage.setScene(renameTreeItemModalScene);
        stage.setTitle("Rename tree item");
        stage.initModality(Modality.WINDOW_MODAL);

        // remove but don't know reason for this and commented, was causing some problems
        // stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }

    public String getNewName() {
        return newName;
    }

    public void setOldName(String value) {
        oldName = value;
    }
}
