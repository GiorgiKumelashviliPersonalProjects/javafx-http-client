package com.example.javafxhttpclient.controllers.tabs;

import com.example.javafxhttpclient.core.fxml.HeaderInputsComponent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class QueryTabController implements HeaderInputs {
    @FXML
    VBox queryTabContent;

    public void onAddButtonClick(ActionEvent event) {
        HeaderInputsComponent headerInputsComponent = new HeaderInputsComponent();

        queryTabContent.getChildren().add(headerInputsComponent);
        System.out.println(event);
    }

    public ObservableList<HeaderInputsComponent> getHeaderInputComponents() {
        return (ObservableList<HeaderInputsComponent>) (ObservableList) queryTabContent.getChildren();
    }

    public Map<String, String> getNameAndValues() {
        Map<String, String> headers = new HashMap<>();

        System.out.println(getHeaderInputComponents());

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
                System.out.println("========================");
                headers.put(pair.getKey().trim(), pair.getValue().trim());
            }
        }

        return headers;
    }
}
