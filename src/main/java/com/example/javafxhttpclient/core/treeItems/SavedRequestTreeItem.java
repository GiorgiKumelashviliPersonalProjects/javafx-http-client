package com.example.javafxhttpclient.core.treeItems;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.treeItems.fragments.FolderTreeItem;
import com.example.javafxhttpclient.core.treeItems.fragments.RequestTreeItem;
import com.example.javafxhttpclient.core.utils.Constants;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class SavedRequestTreeItem {
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

    public TreeItem<String> getItem() {
        return item;
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
            InputStream imageStream = getClass().getResourceAsStream(Constants.savedRequestTreeItemFolderIcon);
            assert imageStream != null;
            ImageView folderImageIcon = new ImageView(new Image(imageStream));
            folderImageIcon.setFitHeight(15);
            folderImageIcon.setFitWidth(15);

            item.setGraphic(folderImageIcon);
        }
    }
}
