package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.controllers.tabs.JsonTabController;
import com.example.javafxhttpclient.core.codearea.JSONFormatter;
import com.example.javafxhttpclient.core.codearea.JSONHighlighter;
import com.example.javafxhttpclient.core.enums.HttpMethods;
import com.example.javafxhttpclient.core.networking.Network;
import com.example.javafxhttpclient.core.utils.Util;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private final String formatButtonIcon = FontAwesomeIcon.FILE_TEXT_ALT.name();

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

    @FXML
    JsonTabController jsonTabController;

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


    public void onSendButtonClick(ActionEvent event) {
        // validate url
        if (Network.isNotValidUrl(urlTextField.getText())) {
            Util.showAlert(Alert.AlertType.ERROR, "Invalid url");
            return;
        }

        String url = urlTextField.getText();
        String method = httpMethodsCombobox.getValue();
        String jsonContent = jsonTabController.getJsonContent();

        // TODO
        // get headers
        // get queries
    }

    public void formatJson() {
        jsonTabController.formatJson();
    }

    public String getFormatButtonIcon() {
        return formatButtonIcon;
    }
}
