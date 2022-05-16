package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.core.enums.HttpMethods;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ContentController implements Initializable {
    @FXML
    public AnchorPane anchorPane;

    @FXML
    public SplitPane splitPane;

    @FXML
    public GridPane gridPane;

    @FXML
    public ComboBox<String> httpMethodsCombobox;

    @FXML
    public TextField urlTextField;

    @FXML
    public Button sendButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // grid layout for first column
        final ColumnConstraints col1 = new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        final ColumnConstraints col2 = new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        final ColumnConstraints col3 = new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);

        // for filling url text field
        col2.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        httpMethodsCombobox.getItems().addAll(HttpMethods.all());
        httpMethodsCombobox.setValue(HttpMethods.GET.toString());

        //! Very important for setting positions of all split panes
        Platform.runLater(() -> {
            Stage stage = (Stage) splitPane.getScene().getWindow();
            stage.widthProperty().addListener((observableValue, oldValue, newValue) -> {
                splitPane.setDividerPositions(0.14f, 0.6f, 0.65f);
            });
        });
    }
}
