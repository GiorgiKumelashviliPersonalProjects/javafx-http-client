package com.example.javafxhttpclient.controllers.tabs;

import com.example.javafxhttpclient.core.models.fxml.ResponseTableModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ResponseHeadersController implements Initializable {
    @FXML
    TableView<ResponseTableModel> headersTableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Table view
        TableColumn<ResponseTableModel, String> name = new TableColumn<>("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 50% width

        TableColumn<ResponseTableModel, String> value = new TableColumn<>("VALUE");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        value.setCellFactory(TextFieldTableCell.forTableColumn());
        value.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 50% width


        headersTableList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        headersTableList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        headersTableList.getSelectionModel().setCellSelectionEnabled(true);

        headersTableList.getColumns().add(name);
        headersTableList.getColumns().add(value);

//        final int COUNT = 10;
//        ObservableList<ResponseTableModel> responseHeaders = FXCollections.observableArrayList();
//        for (int i = 0; i < COUNT; i++) {
//            responseHeaders.add(new ResponseTableModel(i + "asd", i + "2222asd"));
//        }
//        headersTableList.setItems(responseHeaders);
//        dynamicallyComputeHeight(responseHeaders);
    }


    public void setResponseHeaders(Map<String, String> responseHeaders) {
        ObservableList<ResponseTableModel> tempHeaders = FXCollections.observableArrayList();

        for (Map.Entry<String, String> entry : responseHeaders.entrySet()) {
            tempHeaders.add(new ResponseTableModel(entry.getKey(), entry.getValue()));
        }

        headersTableList.setItems(tempHeaders);
        dynamicallyComputeHeight(tempHeaders);
    }

    private void dynamicallyComputeHeight(List<ResponseTableModel> responseHeaders) {
        // dynamic computing of height for table
        if (responseHeaders.size() > 30) {
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
