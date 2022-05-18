package com.example.javafxhttpclient.core.treeItems;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.treeItems.fragments.FolderTreeItem;
import com.example.javafxhttpclient.core.treeItems.fragments.RequestTreeItem;
import com.example.javafxhttpclient.core.treeItems.fragments.SavedRequestTreeItemAbstr;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.*;

public class SavedRequestTreeItem {
    private boolean addContextMenu = true;
    private final SavedTreeItemType savedTreeItemType;
    private final String name;
    private TreeItem<String> item;
    private final List<SavedRequestTreeItem> children = new ArrayList<>();

    public SavedRequestTreeItem(SavedTreeItemType savedTreeItemType, String name) {
        this.savedTreeItemType = savedTreeItemType;
        this.name = name;
        setItem();
        setImage();
    }

    private void setItem() {
        if (savedTreeItemType == SavedTreeItemType.REQUEST) {
            item = new RequestTreeItem(name);
        }

        if (savedTreeItemType == SavedTreeItemType.FOLDER) {
            item = new FolderTreeItem(name);
        }
    }

    public List<SavedRequestTreeItem> getChildren() {
        return children;
    }

    public SavedTreeItemType getSavedTreeItemType() {
        return savedTreeItemType;
    }

    public TreeItem<String> getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public void setChildren(SavedRequestTreeItem... params) {
        // add inside class
        children.addAll(Arrays.asList(params));

        // add fxml
        List<TreeItem<String>> x = Arrays.stream(params).map(SavedRequestTreeItem::getItem).toList();
        item.getChildren().addAll(x);
    }

    private void setImage() {
        if (savedTreeItemType == SavedTreeItemType.FOLDER) {
            // get folder icon
            InputStream imageStream = getClass().getResourceAsStream("/com/example/javafxhttpclient/icons8-envelope-50.png");
            assert imageStream != null;
            ImageView folderImageIcon = new ImageView(new Image(imageStream));
            folderImageIcon.setFitHeight(20);
            folderImageIcon.setFitWidth(20);

            item.setGraphic(folderImageIcon);
        }
    }

    private void addContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem createNewRequest = new MenuItem("Create Request");

        if (savedTreeItemType == SavedTreeItemType.FOLDER) {
            MenuItem createNewFolder = new MenuItem("Create Folder");
            contextMenu.getItems().add(createNewFolder);
        }

        contextMenu.getItems().addAll(createNewRequest, deleteItem);
    }
}
