package com.example.javafxhttpclient.entities;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.misc.treeItems.FolderTreeItem;
import com.example.javafxhttpclient.core.misc.treeItems.RequestTreeItem;
import com.example.javafxhttpclient.core.misc.treeItems.SavedRequestTreeItemAbstract;
import com.example.javafxhttpclient.core.utils.Constants;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "FieldMayBeFinal", "unused", "ClassEscapesDefinedScope"})
public class RequestEntity {
    private int id;

    private SavedTreeItemType type;

    private String name;

    @Nullable
    private RequestDataEntity requestDataEntity;

    private SavedRequestTreeItemAbstract fxmlComponent;

    private ImageView imageView;

    private final List<RequestEntity> children = new ArrayList<>();

    public RequestEntity(int id, SavedTreeItemType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;

        // set fxml
        if (type == SavedTreeItemType.REQUEST) {
            fxmlComponent = new RequestTreeItem(id, name);
        }

        if (type == SavedTreeItemType.FOLDER) {
            fxmlComponent = new FolderTreeItem(id, name);
        }

        // set fxml image view
        if (type == SavedTreeItemType.FOLDER) {
            ImageView folderIconImage = Constants.getFolderIconImage();
            imageView = folderIconImage;
            fxmlComponent.setGraphic(folderIconImage);
        }
    }

    // |=====================================================
    // | METHODS
    // |=====================================================

    public RequestEntity cloneEmpty() {
        return new RequestEntity(id, type, name);
    }

    public void removeChild(RequestEntity child) {
        children.remove(child);
    }

    // |=====================================================
    // | SETTERS
    // |=====================================================

    public void setId(int id) {
        this.id = id;
    }

    public void setType(SavedTreeItemType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
        this.fxmlComponent.setValue(name);
    }

    public void setRequestDataEntity(RequestDataEntity requestDataEntity) {
        this.requestDataEntity = requestDataEntity;
    }

    public void setFxmlComponent(SavedRequestTreeItemAbstract fxmlComponent) {
        this.fxmlComponent = fxmlComponent;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setChildren(RequestEntity... params) {
        // add inside class
        children.addAll(Arrays.asList(params));

        // add fxml
        List<TreeItem<String>> fxmlTreeItems = Arrays.stream(params).map(RequestEntity::getFxmlComponent).toList();
        fxmlComponent.getChildren().addAll(fxmlTreeItems);
    }

    public void setChildName(int id, String newName) {
        for (RequestEntity child : children) {
            if (child.getId() == id) {
                var foundIndex = children.indexOf(child);
                var foundElement = children.get(foundIndex);
                foundElement.setName(newName);
                children.set(foundIndex, foundElement);
                break;
            }
        }
    }

    // |=====================================================
    // | GETTERS
    // |=====================================================

    public int getId() {
        return id;
    }

    public SavedTreeItemType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public RequestDataEntity getRequestDataEntity() {
        return requestDataEntity;
    }

    public TreeItem<String> getFxmlComponent() {
        return fxmlComponent;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public List<RequestEntity> getChildren() {
        return children;
    }
}
