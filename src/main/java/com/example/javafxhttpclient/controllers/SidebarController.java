package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.modals.AddTreeItemModalWindow;
import com.example.javafxhttpclient.core.modals.RenameTreeItemModalWindow;
import com.example.javafxhttpclient.core.treeItems.SavedRequestTreeItem;
import com.example.javafxhttpclient.core.treeItems.fragments.FolderTreeItem;
import com.example.javafxhttpclient.core.treeItems.fragments.SavedRequestTreeCellImpl;
import com.example.javafxhttpclient.core.treeItems.fragments.SavedRequestTreeItemAbstract;
import com.example.javafxhttpclient.core.utils.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class SidebarController implements Initializable {
    @FXML
    public AnchorPane rootParent;

    @FXML
    public TreeView<String> savedRequests;

    public TreeItem<String> invisibleRoot;

    public List<SavedRequestTreeItem> rootTreeItems;

    public SidebarController() {
        invisibleRoot = new TreeItem<>(null);

        // temp
        SavedRequestTreeItem root1 = new SavedRequestTreeItem(1, SavedTreeItemType.FOLDER, "Simple api request");
        SavedRequestTreeItem model11 = new SavedRequestTreeItem(2, SavedTreeItemType.REQUEST, "Json test 1");
        SavedRequestTreeItem model12 = new SavedRequestTreeItem(3, SavedTreeItemType.REQUEST, "Json test 2");
        root1.setChildren(model11, model12);

        SavedRequestTreeItem root2 = new SavedRequestTreeItem(4, SavedTreeItemType.FOLDER, "Some folder");
        SavedRequestTreeItem root3 = new SavedRequestTreeItem(5, SavedTreeItemType.REQUEST, "testing");

        List<SavedRequestTreeItem> data = new ArrayList<>();
        data.add(root1);
        data.add(root2);
        data.add(root3);

        setTreeItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        savedRequests.setCellFactory(p -> new SavedRequestTreeCellImpl(this));
        savedRequests.setEditable(true);
        savedRequests.setRoot(invisibleRoot);
        savedRequests.setShowRoot(false);
    }

    // methods
    public void create(ActionEvent event) {
        System.out.println("create");

        try {
            AddTreeItemModalWindow addTreeItemModalWindow = new AddTreeItemModalWindow();
            addTreeItemModalWindow.show(() -> {
                String newName = addTreeItemModalWindow.getDisplayName();
                SavedTreeItemType newType = addTreeItemModalWindow.getType();

                if (newType != null && Util.isStringValid(newName)) {
                    int randomId = Util.randInt(1000);
                    SavedRequestTreeItem newRootItem = new SavedRequestTreeItem(randomId, newType, newName);
                    addTreeItemToSpecificLevel(newRootItem);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            event.consume();
        }
    }

    public void rename(ActionEvent event) {
        System.out.println("rename");
        var selectedTreeItem = savedRequests.getSelectionModel().getSelectedItem();

        try {
            RenameTreeItemModalWindow renameTreeItemModalWindow = new RenameTreeItemModalWindow();
            renameTreeItemModalWindow.setOldName(selectedTreeItem.getValue());

            renameTreeItemModalWindow.show(() -> {
                String newName = renameTreeItemModalWindow.getNewName();

                if (Util.isStringValid(newName)) {
                    renameTreeItemToSpecificLevel(newName);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            event.consume();
        }
    }

    public void delete(ActionEvent event) {
        System.out.println("delete");
        event.consume();
    }

    public void filter(ActionEvent event) {
        event.consume();
    }

    private void setTreeItems(List<SavedRequestTreeItem> data) {
        rootTreeItems = data;
        invisibleRoot.getChildren().addAll(data.stream().map(SavedRequestTreeItem::getItem).toList());
    }

    private void addTreeItemToSpecificLevel(SavedRequestTreeItem item) {
        var selectedTreeItem = savedRequests.getSelectionModel().getSelectedItem();

        rootTreeItems.add(item);

        if (selectedTreeItem != null && selectedTreeItem.getClass().equals(FolderTreeItem.class)) {
            if (item.getSavedTreeItemType() == SavedTreeItemType.FOLDER) {
                Util.showAlertModal(Alert.AlertType.ERROR, "Allowed only request, cannot nest folders !");
                return;
            }

            // allowed here only if selected is folder and item type is request
            selectedTreeItem.getChildren().add(item.getItem());
        } else {
            invisibleRoot.getChildren().addAll(item.getItem());

        }
    }

    private void renameTreeItemToSpecificLevel(String newName) {
        SavedRequestTreeItemAbstract selectedTreeItem = (SavedRequestTreeItemAbstract) savedRequests.getSelectionModel().getSelectedItem();
        int id = selectedTreeItem.getId();

        // set fxml
        selectedTreeItem.setValue(newName);

        // update data in java
        for (SavedRequestTreeItem parent : rootTreeItems) {
            if (parent.getId() == id) {
                var foundIndex = rootTreeItems.indexOf(parent);
                var foundElement = rootTreeItems.get(foundIndex);
                foundElement.setName(newName);
                rootTreeItems.set(foundIndex, foundElement);
                break;
            }

            for (SavedRequestTreeItem child: parent.getChildren()) {
                if (child.getId() == id) {
                    var foundParentIndex = rootTreeItems.indexOf(parent);
                    var foundParentElement = rootTreeItems.get(foundParentIndex);
                    foundParentElement.setChildName(id, newName);
                    rootTreeItems.set(foundParentIndex, foundParentElement);
                    break;
                }
            }
        }
    }
}
