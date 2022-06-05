package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.core.enums.HttpMethods;
import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.modals.AddTreeItemModalWindow;
import com.example.javafxhttpclient.core.modals.CheckModalWindow;
import com.example.javafxhttpclient.core.modals.RenameTreeItemModalWindow;
import com.example.javafxhttpclient.entities.RequestDataEntity;
import com.example.javafxhttpclient.entities.RequestEntity;
import com.example.javafxhttpclient.core.misc.treeItems.FolderTreeItem;
import com.example.javafxhttpclient.core.misc.treeItems.SavedRequestTreeCellImpl;
import com.example.javafxhttpclient.core.misc.treeItems.SavedRequestTreeItemAbstract;
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
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class SidebarController implements Initializable {
    public static SidebarController instance;

    private final TreeItem<String> invisibleRootComponent;

    public List<RequestEntity> rootTreeItems;

    @FXML
    public AnchorPane rootParent;

    @FXML
    public TreeView<String> rootTreeView;

    @FXML
    public TextField filterTextField;

    public SidebarController() {
        invisibleRootComponent = new TreeItem<>(null);

        if (instance == null) {
            instance = this;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootTreeView.setCellFactory(p -> new SavedRequestTreeCellImpl(this));
        rootTreeView.setRoot(invisibleRootComponent);
        rootTreeView.setShowRoot(false);
        debounceFilter();
    }

    // |=====================================================
    // | METHODS
    // |=====================================================

    public void onCreateButtonClick(ActionEvent event) {
        // lose focus
        rootParent.requestFocus();
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
                    try {
                        addTreeItemToSpecificLevel(isTreeViewRootParentFocused, newName, newType);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            event.consume();
        }
    }

    public void rename(ActionEvent event) {
        var selectedTreeItem = rootTreeView.getSelectionModel().getSelectedItem();

        try {
            RenameTreeItemModalWindow renameTreeItemModalWindow = new RenameTreeItemModalWindow();
            renameTreeItemModalWindow.setOldName(selectedTreeItem.getValue());

            renameTreeItemModalWindow.show(() -> {
                String newName = renameTreeItemModalWindow.getNewName();

                if (Util.isStringValid(newName)) {
                    try {
                        renameTreeItemToSpecificLevel(newName);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
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
                    try {
                        deleteTreeItemToSpecificLevel();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            event.consume();
        }
    }

    private void addTreeItemToSpecificLevel(
            boolean isTreeViewRootParentFocused,
            String newName,
            SavedTreeItemType newType
    ) throws SQLException {
        // SQL
        Integer parentId = null;

        if (!isTreeViewRootParentFocused) {
            // edge case where request is created inside folder, and we need parent id
            SavedRequestTreeItemAbstract selectedTreeItem = (SavedRequestTreeItemAbstract) rootTreeView
                    .getSelectionModel()
                    .getSelectedItem();

            if (selectedTreeItem.getClass().equals(FolderTreeItem.class)) {
                // this means its inside folder
                parentId = selectedTreeItem.getId();
            }
        }

        RequestEntity newRootItem = RequestEntity.insertNewEntityInDb(newType, newName, parentId, true);

        if (newType == SavedTreeItemType.REQUEST) {
            RequestDataEntity newRequestDataEntity = RequestDataEntity
                    .insertNewEntityInDb(newRootItem.getId(), "", HttpMethods.GET, true);
            newRootItem.setRequestDataEntity(newRequestDataEntity);
        }


        // JAVAFX (UI update)
        if (isTreeViewRootParentFocused) {
            // fxml
            invisibleRootComponent.getChildren().addAll(Objects.requireNonNull(newRootItem).getFxmlComponent());

            // add data
            rootTreeItems.add(newRootItem);

            return;
        }

        SavedRequestTreeItemAbstract selectedTreeItem = (SavedRequestTreeItemAbstract) rootTreeView
                .getSelectionModel()
                .getSelectedItem();

        if (selectedTreeItem.getClass().equals(FolderTreeItem.class)) {
            if (Objects.requireNonNull(newRootItem).getType() == SavedTreeItemType.FOLDER) {
                Util.showAlertModal(Alert.AlertType.ERROR, "Allowed only request, cannot nest folders !");
                return;
            }

            // fxml ( allowed here only if selected is folder and newRootItem type is request )
            selectedTreeItem.getChildren().add(newRootItem.getFxmlComponent());

            // add data to children of already existing newRootItem in root
            for (RequestEntity parent : rootTreeItems) {
                if (parent.getId() == selectedTreeItem.getId()) {
                    parent.getChildren().add(newRootItem);
                    break;
                }
            }
        } else {
            // fxml
            invisibleRootComponent.getChildren().addAll(Objects.requireNonNull(newRootItem).getFxmlComponent());

            // add data
            rootTreeItems.add(newRootItem);
        }
    }

    private void renameTreeItemToSpecificLevel(String newName) throws SQLException {
        SavedRequestTreeItemAbstract selectedTreeItem = (SavedRequestTreeItemAbstract) rootTreeView.getSelectionModel().getSelectedItem();
        int id = selectedTreeItem.getId();

        // SQL
        RequestEntity.renameByIdDb(id, newName);

        // set fxml
        selectedTreeItem.setValue(newName);

        // update data
        for (RequestEntity parent : rootTreeItems) {
            if (parent.getId() == id) {
                var foundIndex = rootTreeItems.indexOf(parent);
                var foundElement = rootTreeItems.get(foundIndex);
                foundElement.setName(newName);
                rootTreeItems.set(foundIndex, foundElement);
                break;
            }

            for (RequestEntity child : parent.getChildren()) {
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

    private void deleteTreeItemToSpecificLevel() throws SQLException {
        SavedRequestTreeItemAbstract selectedTreeItem = (SavedRequestTreeItemAbstract) rootTreeView.getSelectionModel().getSelectedItem();
        int id = selectedTreeItem.getId();

        // SQL
        RequestEntity.deleteByIdDb(id);

        // fxml
        selectedTreeItem.getParent().getChildren().remove(selectedTreeItem);

        // delete data
        for (RequestEntity parent : rootTreeItems) {
            if (parent.getId() == id) {
                rootTreeItems.remove(parent);
                break;
            }

            for (RequestEntity child : parent.getChildren()) {
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

    // |=====================================================
    // | OTHER
    // |=====================================================

    private void debounceFilter() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        filterTextField.textProperty().addListener((observable, oldValue, filterText) -> {
            pause.setOnFinished(event -> {
                if (filterText.isEmpty()) {
                    invisibleRootComponent.getChildren().clear();
                    invisibleRootComponent.getChildren().addAll(rootTreeItems.stream().map(RequestEntity::getFxmlComponent).toList());
                    return;
                }

                List<RequestEntity> filteredItems = new ArrayList<>();

                // filter ui here
                for (var parent : rootTreeItems) {
                    int childMatched = 0;
                    RequestEntity tempParent = parent.cloneEmpty();
                    tempParent.getFxmlComponent().setExpanded(true);

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

                invisibleRootComponent.getChildren().clear();
                invisibleRootComponent.getChildren().addAll(filteredItems.stream().map(RequestEntity::getFxmlComponent).toList());
            });
            pause.playFromStart();
        });
    }

    public void initializeEntities(List<RequestEntity> requestEntities) {
        rootTreeItems = requestEntities;
        invisibleRootComponent.getChildren().addAll(requestEntities.stream().map(RequestEntity::getFxmlComponent).toList());
    }

    @Nullable
    public RequestEntity findItem(int id) {
        for (RequestEntity parent : rootTreeItems) {
            if (parent.getId() == id) return parent;

            for (RequestEntity child : parent.getChildren())
                if (child.getId() == id) return child;
        }

        return null;
    }
}
