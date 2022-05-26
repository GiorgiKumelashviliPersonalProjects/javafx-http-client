package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.modals.AddTreeItemModalWindow;
import com.example.javafxhttpclient.core.modals.CheckModalWindow;
import com.example.javafxhttpclient.core.modals.RenameTreeItemModalWindow;
import com.example.javafxhttpclient.core.treeItems.SavedRequestTreeItem;
import com.example.javafxhttpclient.core.treeItems.fragments.FolderTreeItem;
import com.example.javafxhttpclient.core.treeItems.fragments.SavedRequestTreeCellImpl;
import com.example.javafxhttpclient.core.treeItems.fragments.SavedRequestTreeItemAbstract;
import com.example.javafxhttpclient.core.utils.Util;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class SidebarController implements Initializable {
    public static SidebarController instance;

    @FXML
    public AnchorPane rootParent;

    @FXML
    public TreeView<String> savedRequestsTreeView;

    @FXML
    public TextField filterTextField;

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

        addTreeItems(data);

        if (instance == null) {
            instance = this;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        savedRequestsTreeView.setCellFactory(p -> new SavedRequestTreeCellImpl(this));
        savedRequestsTreeView.setRoot(invisibleRoot);
        savedRequestsTreeView.setShowRoot(false);
        debounceFilter();
    }

    // methods

    public void onCreateButtonClick(ActionEvent event) {
        // lose focus
        rootParent.requestFocus();
        System.out.println("rootParent");
        System.out.println(rootParent.isFocused());
        this.create(event);
    }

    public void create(ActionEvent event) {
        // memoize here
        final boolean isTreeViewRootParentFocused = rootParent.isFocused();

        try {
            AddTreeItemModalWindow addTreeItemModalWindow = new AddTreeItemModalWindow();
            addTreeItemModalWindow.show(() -> {
                String newName = addTreeItemModalWindow.getDisplayName();
                SavedTreeItemType newType = addTreeItemModalWindow.getType();

                if (newType != null && Util.isStringValid(newName)) {
                    int randomId = Util.randInt(1000);
                    SavedRequestTreeItem newRootItem = new SavedRequestTreeItem(randomId, newType, newName);
                    addTreeItemToSpecificLevel(newRootItem, isTreeViewRootParentFocused);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            event.consume();
        }
    }

    public void rename(ActionEvent event) {
        var selectedTreeItem = savedRequestsTreeView.getSelectionModel().getSelectedItem();

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
        CheckModalWindow checkModalWindow = new CheckModalWindow();

        try {
            checkModalWindow.show(() -> {
                if (checkModalWindow.isAnswerYes()) {
                    // delete
                    deleteTreeItemToSpecificLevel();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            event.consume();
        }
    }

    public void debounceFilter() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        filterTextField.textProperty().addListener((observable, oldValue, filterText) -> {
            pause.setOnFinished(event -> {
                if (filterText.isEmpty()) {
                    invisibleRoot.getChildren().clear();
                    invisibleRoot.getChildren().addAll(rootTreeItems.stream().map(SavedRequestTreeItem::getItem).toList());
                    return;
                }

                List<SavedRequestTreeItem> filteredItems = new ArrayList<>();

                // filter ui here
                for (var parent : rootTreeItems) {
                    int childMatched = 0;
                    SavedRequestTreeItem tempParent = parent.cloneEmpty();
                    tempParent.getItem().setExpanded(true);

                    // first check children
                    for (var child : parent.getChildren()) {
                        if (child.getName().contains(filterText)) {
                            childMatched++;
                            tempParent.getChildren().add(child);
                            tempParent.setChildren(child);
                        }
                    }

                    if (tempParent.getName().contains(filterText) || childMatched != 0) {
                        filteredItems.add(tempParent);
                    }
                }

                invisibleRoot.getChildren().clear();
                invisibleRoot.getChildren().addAll(filteredItems.stream().map(SavedRequestTreeItem::getItem).toList());
            });
            pause.playFromStart();
        });
    }

    private void addTreeItems(List<SavedRequestTreeItem> data) {
        rootTreeItems = data;
        invisibleRoot.getChildren().addAll(data.stream().map(SavedRequestTreeItem::getItem).toList());
    }

    private void addTreeItemToSpecificLevel(SavedRequestTreeItem item, boolean isTreeViewRootParentFocused) {
        System.out.println(isTreeViewRootParentFocused);

        if (isTreeViewRootParentFocused) {
            // fxml
            invisibleRoot.getChildren().addAll(item.getItem());

            // add data
            rootTreeItems.add(item);

            return;
        }

        SavedRequestTreeItemAbstract selectedTreeItem = (SavedRequestTreeItemAbstract) savedRequestsTreeView
                .getSelectionModel()
                .getSelectedItem();

        if (selectedTreeItem.getClass().equals(FolderTreeItem.class)) {
            if (item.getSavedTreeItemType() == SavedTreeItemType.FOLDER) {
                Util.showAlertModal(Alert.AlertType.ERROR, "Allowed only request, cannot nest folders !");
                return;
            }

            // fxml ( allowed here only if selected is folder and item type is request )
            selectedTreeItem.getChildren().add(item.getItem());

            // add data to children of already existing item in root
            for (SavedRequestTreeItem parent : rootTreeItems) {
                if (parent.getId() == selectedTreeItem.getId()) {
                    parent.getChildren().add(item);
                    break;
                }
            }
        } else {
            // fxml
            invisibleRoot.getChildren().addAll(item.getItem());

            // add data
            rootTreeItems.add(item);
        }
    }

    private void renameTreeItemToSpecificLevel(String newName) {
        SavedRequestTreeItemAbstract selectedTreeItem = (SavedRequestTreeItemAbstract) savedRequestsTreeView.getSelectionModel().getSelectedItem();
        int id = selectedTreeItem.getId();

        // set fxml
        selectedTreeItem.setValue(newName);

        // update data
        for (SavedRequestTreeItem parent : rootTreeItems) {
            if (parent.getId() == id) {
                var foundIndex = rootTreeItems.indexOf(parent);
                var foundElement = rootTreeItems.get(foundIndex);
                foundElement.setName(newName);
                rootTreeItems.set(foundIndex, foundElement);
                break;
            }

            for (SavedRequestTreeItem child : parent.getChildren()) {
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

    private void deleteTreeItemToSpecificLevel() {
        SavedRequestTreeItemAbstract selectedTreeItem = (SavedRequestTreeItemAbstract) savedRequestsTreeView.getSelectionModel().getSelectedItem();
        int id = selectedTreeItem.getId();

        // fxml
        selectedTreeItem.getParent().getChildren().remove(selectedTreeItem);

        // delete data
        for (SavedRequestTreeItem parent : rootTreeItems) {
            if (parent.getId() == id) {
                rootTreeItems.remove(parent);
                break;
            }

            for (SavedRequestTreeItem child : parent.getChildren()) {
                if (child.getId() == id) {
                    var foundParentIndex = rootTreeItems.indexOf(parent);
                    var foundParentElement = rootTreeItems.get(foundParentIndex);
                    foundParentElement.removeChild(child);
                    rootTreeItems.set(foundParentIndex, foundParentElement);
                    break;
                }
            }
        }
    }
}
