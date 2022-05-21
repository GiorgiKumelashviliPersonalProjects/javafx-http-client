package com.example.javafxhttpclient.controllers.tabs;

import com.example.javafxhttpclient.core.fxml.HeaderInputsComponent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.util.Map;

public interface HeaderInputs {
    void onAddButtonClick(ActionEvent event);

    ObservableList<HeaderInputsComponent> getHeaderInputComponents();

    Map<String, String> getNameAndValues();
}
