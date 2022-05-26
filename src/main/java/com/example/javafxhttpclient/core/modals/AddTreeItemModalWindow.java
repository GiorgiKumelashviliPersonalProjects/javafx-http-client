package com.example.javafxhttpclient.core.modals;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.fxml.AddTreeItemModalController;
import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.core.utils.FileManipulator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddTreeItemModalWindow {
    private String displayName;
    private SavedTreeItemType type;

    public AddTreeItemModalWindow() {
    }

    public void show(TreeItemModalWindowCallback callback) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modalFxml = FileManipulator.fxmlLoader(Constants.addSavedRequestTreeItem);
        String mainCss = FileManipulator.css(Constants.mainCss);

        Scene addTreeItemModalScene = new Scene(modalFxml.load());

        AddTreeItemModalController addTreeItemModalController = modalFxml.getController();

        // on save
        addTreeItemModalController.onAddButton.setOnAction(e -> {
            String selectedItemType = addTreeItemModalController.savedTreeItemTypeComboBox.getSelectionModel().getSelectedItem();

            // set data
            displayName = addTreeItemModalController.savedTreeItemTextField.getText();
            type = selectedItemType != null ? SavedTreeItemType.valueOf(selectedItemType) : null;

            // close window and callback onClose
            stage.close();
            callback.onClose();
        });

        // on close
        addTreeItemModalController.onCloseButton.setOnAction(e -> {
            stage.close();
            callback.onClose();
        });

        addTreeItemModalScene.getStylesheets().add(mainCss);
        stage.setScene(addTreeItemModalScene);
        stage.setTitle("Add tree item modal");
        stage.initModality(Modality.WINDOW_MODAL);

        // remove but don't know reason for this and commented, was causing some problems
        // stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }

    public String getDisplayName() {
        return displayName;
    }

    public SavedTreeItemType getType() {
        return type;
    }
}
