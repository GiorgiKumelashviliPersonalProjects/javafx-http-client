package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.core.enums.HttpMethods;
import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.entities.RequestDataEntity;
import com.example.javafxhttpclient.entities.RequestEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class MainController implements Initializable {
    @FXML
    ContentController contentController;

    @FXML
    MenuBarController menuBarController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // temp
        RequestEntity root1 = new RequestEntity(1, SavedTreeItemType.FOLDER, "Simple api request");
        RequestEntity model11 = new RequestEntity(2, SavedTreeItemType.REQUEST, "Json test 1");
        RequestDataEntity requestDataEntityForModel11 = new RequestDataEntity("xxx", HttpMethods.POST);
        model11.setRequestDataEntity(requestDataEntityForModel11);

        RequestEntity model12 = new RequestEntity(3, SavedTreeItemType.REQUEST, "Json test 2");
        RequestDataEntity requestDataEntityForModel12 = new RequestDataEntity("https://youtube.com", HttpMethods.HEAD);
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

        RequestEntity root2 = new RequestEntity(4, SavedTreeItemType.FOLDER, "Some folder");
        RequestEntity root3 = new RequestEntity(5, SavedTreeItemType.REQUEST, "testing");

        List<RequestEntity> requestEntities = new ArrayList<>();
        requestEntities.add(root1);
        requestEntities.add(root2);
        requestEntities.add(root3);

        // initialize data
        contentController.sidebarController.initializeEntities(requestEntities);
    }
}