package com.example.javafxhttpclient.controllers.tabs;

import com.example.javafxhttpclient.controllers.fragments.HeaderInputsComponent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class HeadersTabController implements HeaderInputs {
    @FXML
    public VBox tabContent;

    public void onAddButtonClick(ActionEvent event) {
        HeaderInputsComponent headerInputsComponent = new HeaderInputsComponent();
        tabContent.getChildren().add(headerInputsComponent);
    }

    public ObservableList<HeaderInputsComponent> getHeaderInputComponents() {
        return (ObservableList<HeaderInputsComponent>) (ObservableList) tabContent.getChildren();
    }

    public Map<String, String> getNameAndValues() {
        Map<String, String> headers = new HashMap<>();

        // get header key and value
        for (HeaderInputsComponent headerInputsComponent : getHeaderInputComponents()) {
            Pair<String, String> pair = headerInputsComponent.getNameValue();

            // check everything on both name and value and trim as well !!!
            if (pair.getValue() != null &&
                    pair.getValue() != null &&
                    !pair.getKey().isBlank() &&
                    !pair.getKey().isEmpty() &&
                    !pair.getValue().isBlank() &&
                    !pair.getValue().isEmpty()
            ) {
                headers.put(pair.getKey().trim(), pair.getValue().trim());
            }
        }

        return headers;
    }

    public void setHeaderData(Map<String, String> headerData) {
        tabContent.getChildren().clear();

        headerData.forEach((k, v) -> {
            HeaderInputsComponent headerInputsComponent = new HeaderInputsComponent();
            headerInputsComponent.nameTextField.setText(k);
            headerInputsComponent.valueTextField.setText(v);
            tabContent.getChildren().add(headerInputsComponent);
        });
    }

    public void clearHeaders() {
        tabContent.getChildren().clear();
        HeaderInputsComponent headerInputsComponent = new HeaderInputsComponent();
        tabContent.getChildren().add(headerInputsComponent);
    }

}
