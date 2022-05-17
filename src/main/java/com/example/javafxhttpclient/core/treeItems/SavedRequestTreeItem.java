package com.example.javafxhttpclient.core.treeItems;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.*;

public class SavedRequestTreeItem {
    private final SavedTreeItemType savedTreeItemType;
    private final String name;
    private final TreeItem<String> item;
    private final List<SavedRequestTreeItem> children = new ArrayList<>();

    public SavedRequestTreeItem(SavedTreeItemType savedTreeItemType, String name) {
        this.savedTreeItemType = savedTreeItemType;
        this.name = name;
        this.item = new TreeItem<>(name);
        setImage();
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

    public void setChildren(SavedRequestTreeItem ...params) {
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
}
