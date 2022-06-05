package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.entities.RequestDataEntity;
import com.example.javafxhttpclient.entities.RequestEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    ContentController contentController;

    @FXML
    MenuBarController menuBarController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // main data list
        List<RequestEntity> requestEntitiesList;
        List<RequestDataEntity> requestDataEntitiesListTemp;

        try {
            requestEntitiesList = RequestEntity.getAllEntityFromDb();
            requestDataEntitiesListTemp = RequestDataEntity.getAllEntityFromDb();

            // 1 combine request entities and request data entities
            for (RequestDataEntity entity : requestDataEntitiesListTemp) {
                if (requestEntitiesList.size() > 0) {
                    requestEntitiesList
                            .stream()
                            .filter(e -> e.getId() == entity.getRequestEntityId())
                            .forEach(e -> e.setRequestDataEntity(entity));
                }
            }

            // 2 set tres hierarchy (must be second)
            List<Integer> removeIds = new ArrayList<>();

            for (RequestEntity guaranteedRequestEntity: requestEntitiesList) {
                if (requestEntitiesList.size() > 0) {

                    requestEntitiesList
                            .stream()
                            .filter(e -> e.getId() == guaranteedRequestEntity.getRequestEntityId())
                            .forEach(e -> {
                                e.setChildren(guaranteedRequestEntity);
                                removeIds.add(guaranteedRequestEntity.getId());
                            });
                }
            }

            // remove sub dirs
            requestEntitiesList.removeIf(e -> removeIds.contains(e.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // initialize data
        contentController.sidebarController.initializeEntities(requestEntitiesList);
    }
}

/*
// temp
RequestEntity root1 = new RequestEntity(1, 0, SavedTreeItemType.FOLDER, "Simple api request");
RequestEntity model11 = new RequestEntity(2, root1.getRequestEntityId(), SavedTreeItemType.REQUEST, "Json test 1");

RequestDataEntity requestDataEntityForModel11 = new RequestDataEntity(1, model11.getId(), "xxx", HttpMethods.POST);
model11.setRequestDataEntity(requestDataEntityForModel11);

RequestEntity model12 = new RequestEntity(3, root1.getRequestEntityId(), SavedTreeItemType.REQUEST, "Json test 2");
RequestDataEntity requestDataEntityForModel12 = new RequestDataEntity(2, model12.getId(), "https://youtube.com", HttpMethods.HEAD);
requestDataEntityForModel12.setHeaderData(new HashMap<>() {{
    this.put("dd", "sadsa");
    this.put("dd1", "sadsa");
    this.put("dd2", "sadsa");
    this.put("dd3", "sadsa");
    this.put("dd4", "sadsa");
}});
requestDataEntityForModel12.setQueryData(new HashMap<>() {{
    this.put("xx3", "sadsa");
    this.put("xx4", "sadsa");
}});
requestDataEntityForModel12.setJsonContent("""
        {
        "testing": "json"
        }
        """);

model12.setRequestDataEntity(requestDataEntityForModel12);

root1.setChildren(model11, model12);

RequestEntity root2 = new RequestEntity(4, 0, SavedTreeItemType.FOLDER, "Some folder");
RequestEntity root3 = new RequestEntity(5, 0, SavedTreeItemType.REQUEST, "testing");

List<RequestEntity> requestEntities = new ArrayList<>();
requestEntities.add(root1);
requestEntities.add(root2);
requestEntities.add(root3);

contentController.sidebarController.initializeEntities(requestEntities);
*/