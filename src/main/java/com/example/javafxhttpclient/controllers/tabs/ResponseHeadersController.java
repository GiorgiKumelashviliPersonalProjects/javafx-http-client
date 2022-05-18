package com.example.javafxhttpclient.controllers.tabs;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ResponseHeadersController implements Initializable {
    @FXML
    TableView<String> headersTableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final int COUNT = 10;
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i = 0; i < COUNT; i++) list.add(i + "asd");

        // Table view
        TableColumn<String, Integer> name = new TableColumn<>("NAME");
        TableColumn<String, Integer> value = new TableColumn<>("VALUE");

        name.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 50% width
        value.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 50% width
        headersTableList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        headersTableList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        headersTableList.getSelectionModel().setCellSelectionEnabled(true);

        // must be changed
        headersTableList.getColumns().addAll(name, value);
        headersTableList.getColumns().forEach(c -> c.setCellValueFactory(data -> new SimpleObjectProperty("Lorem ipsum")));
        headersTableList.getItems().addAll(list);


        // dynamic computing of height for table
        if (list.size() > 30) {
            AnchorPane.setBottomAnchor(headersTableList, 10.0);
        } else {
            // dynamic height computing of table
            headersTableList.setFixedCellSize(25);
            headersTableList.prefHeightProperty().bind(headersTableList
                    .fixedCellSizeProperty()
                    .multiply(Bindings.size(headersTableList.getItems()).add(1.1))
            );
            headersTableList.minHeightProperty().bind(headersTableList.prefHeightProperty());
            headersTableList.maxHeightProperty().bind(headersTableList.prefHeightProperty());
        }
    }
}
