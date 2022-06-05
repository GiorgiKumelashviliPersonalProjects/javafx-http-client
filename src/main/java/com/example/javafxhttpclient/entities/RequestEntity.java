package com.example.javafxhttpclient.entities;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.misc.treeItems.FolderTreeItem;
import com.example.javafxhttpclient.core.misc.treeItems.RequestTreeItem;
import com.example.javafxhttpclient.core.misc.treeItems.SavedRequestTreeItemAbstract;
import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.db.DatabaseConnection;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "FieldMayBeFinal", "unused", "ClassEscapesDefinedScope"})
public class RequestEntity {
    private int id;

    private int requestEntityId;

    private SavedTreeItemType type;

    private String name;

    @Nullable
    private RequestDataEntity requestDataEntity;

    private SavedRequestTreeItemAbstract fxmlComponent;

    private ImageView imageView;

    private final List<RequestEntity> children = new ArrayList<>();

    // For sqlite
    public static final String TABLE_NAME = "request_entity";
    public static final String ID_COLUMN_NAME = "id";
    public static final String REQUEST_ENTITY_ID_COLUMN_NAME = "request_entity_id";
    public static final String TYPE_COLUMN_NAME = "type";
    public static final String NAME_COLUMN_NAME = "name";

    public RequestEntity(int id, int requestEntityId, SavedTreeItemType type, String name) {
        this.id = id;
        this.requestEntityId = requestEntityId;
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
        return new RequestEntity(id, requestEntityId, type, name);
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

    public int getRequestEntityId() {
        return requestEntityId;
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

    // |=====================================================
    // | DATABASE
    // |=====================================================
    public static List<RequestEntity> getAllEntityFromDb() throws SQLException {
        String selectRequestEntitiesRow = "SELECT * FROM %s".formatted(RequestEntity.TABLE_NAME);
        List<RequestEntity> tempRequestEntitiesList = new ArrayList<>();

        DatabaseConnection.executeSelectClbck(stm -> {
            ResultSet requestEntitiesData = stm.executeQuery(selectRequestEntitiesRow);

            int fetchSize = 0;
            while (requestEntitiesData.next()) {
                fetchSize++;

                int id = requestEntitiesData.getInt(RequestEntity.ID_COLUMN_NAME);
                int requestEntityId = requestEntitiesData.getInt(RequestEntity.REQUEST_ENTITY_ID_COLUMN_NAME);
                SavedTreeItemType type = SavedTreeItemType.valueOf(requestEntitiesData.getString(RequestEntity.TYPE_COLUMN_NAME));
                String name = requestEntitiesData.getString(RequestEntity.NAME_COLUMN_NAME);

                // create temp {RequestEntity}
                RequestEntity temp = new RequestEntity(id, requestEntityId, type, name);
                tempRequestEntitiesList.add(temp);
            }

            System.out.println("requestEntitiesData fetchSize: " + fetchSize);
        });

        return tempRequestEntitiesList;
    }

    public static RequestEntity insertNewEntityInDb(
            SavedTreeItemType newType,
            String newName,
            Integer parentId,
            boolean returnValue
    ) throws SQLException {
        String query = "INSERT INTO %s (%s, %s, %s) values (?, ?, ?)"
                .formatted(
                        RequestEntity.TABLE_NAME,
                        RequestEntity.REQUEST_ENTITY_ID_COLUMN_NAME,
                        RequestEntity.TYPE_COLUMN_NAME,
                        RequestEntity.NAME_COLUMN_NAME
                );

        DatabaseConnection.executeCallbackPrepared(query, stmt -> {
            stmt.setString(1, parentId != null ? String.valueOf(parentId) : null);
            stmt.setString(2, String.valueOf(newType));
            stmt.setString(3, newName);
            stmt.executeUpdate();
        });

        if (returnValue) {
            String selectLastQuery = "SELECT * FROM %s WHERE ID = (SELECT MAX(%s)  FROM %s);"
                    .formatted(RequestEntity.TABLE_NAME, RequestEntity.ID_COLUMN_NAME, RequestEntity.TABLE_NAME);

            final RequestEntity[] temp = new RequestEntity[1];

            DatabaseConnection.executeSelectClbck(stmt -> {
                ResultSet lastInserted = stmt.executeQuery(selectLastQuery);

                while (lastInserted.next()) {
                    int id = lastInserted.getInt(RequestEntity.ID_COLUMN_NAME);
                    int requestEntityId = lastInserted.getInt(RequestEntity.REQUEST_ENTITY_ID_COLUMN_NAME);
                    SavedTreeItemType type = SavedTreeItemType.valueOf(lastInserted.getString(RequestEntity.TYPE_COLUMN_NAME));
                    String name = lastInserted.getString(RequestEntity.NAME_COLUMN_NAME);

                    // create temp {RequestEntity}
                    temp[0] = new RequestEntity(id, requestEntityId, type, name);
                }
            });

            return temp[0];
        }

        return null;
    }

    public static void deleteByIdDb(int id) throws SQLException {
        String deleteQuery = "DELETE FROM %s WHERE %s = ?".formatted(RequestEntity.TABLE_NAME, RequestEntity.ID_COLUMN_NAME);

        DatabaseConnection.executeCallbackPrepared(deleteQuery, stmt -> {
            stmt.setString(1, String.valueOf(id));
            stmt.executeUpdate();
        });
    }
}
