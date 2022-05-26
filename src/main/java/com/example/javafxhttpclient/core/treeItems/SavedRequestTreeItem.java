package com.example.javafxhttpclient.core.treeItems;

import static com.example.javafxhttpclient.core.utils.Constants.savedRequestTreeItemFolderIcon;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.treeItems.fragments.FolderTreeItem;
import com.example.javafxhttpclient.core.treeItems.fragments.RequestTreeItem;
import com.example.javafxhttpclient.core.treeItems.fragments.SavedRequestTreeItemAbstract;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class SavedRequestTreeItem {
    private final int id;
    private final SavedTreeItemType savedTreeItemType;
    private String name;
    private SavedRequestTreeItemAbstract item;

    private List<SavedRequestTreeItem> children = new ArrayList<>();

    public SavedRequestTreeItem(int id, SavedTreeItemType savedTreeItemType, String name) {
        this.id = id;
        this.savedTreeItemType = savedTreeItemType;
        this.name = name;
        setItem(id);
        setImage();
    }

    public void setName(String name) {
        this.name = name;
        this.item.setValue(name);
    }

    public void setChildren(SavedRequestTreeItem... params) {
        // add inside class
        children.addAll(Arrays.asList(params));

        // add fxml
        List<TreeItem<String>> fxmlTreeItems = Arrays.stream(params).map(SavedRequestTreeItem::getItem).toList();
        item.getChildren().addAll(fxmlTreeItems);
    }

    public void setChildren(List<SavedRequestTreeItem> children) {
        this.children = children;
    }

    public void clearChildren() {
        this.children = new ArrayList<>();
    }

    public SavedRequestTreeItem cloneEmpty() {
        return new SavedRequestTreeItem(id, savedTreeItemType, name);
    }

    private void setImage() {
        if (savedTreeItemType == SavedTreeItemType.FOLDER) {
            // get folder icon
            InputStream imageStream = getClass().getResourceAsStream(savedRequestTreeItemFolderIcon);
            assert imageStream != null;
            ImageView folderImageIcon = new ImageView(new Image(imageStream));
            folderImageIcon.setFitHeight(15);
            folderImageIcon.setFitWidth(15);

            item.setGraphic(folderImageIcon);
        }
    }

    private void setItem(int id) {
        if (savedTreeItemType == SavedTreeItemType.REQUEST) {
            item = new RequestTreeItem(id, name);
        }

        if (savedTreeItemType == SavedTreeItemType.FOLDER) {
            item = new FolderTreeItem(id, name);
        }
    }

    public int getId() {
        return id;
    }

    public TreeItem<String> getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public SavedTreeItemType getSavedTreeItemType() {
        return savedTreeItemType;
    }

    public List<SavedRequestTreeItem> getChildren() {
        return children;
    }

    public void setChildName(int id, String newName) {
        for (SavedRequestTreeItem child : children) {
            if (child.getId() == id) {
                var foundIndex = children.indexOf(child);
                var foundElement = children.get(foundIndex);
                foundElement.setName(newName);
                children.set(foundIndex, foundElement);
                break;
            }
        }
    }

    public void removeChild(SavedRequestTreeItem child) {
        children.remove(child);
    }
}
