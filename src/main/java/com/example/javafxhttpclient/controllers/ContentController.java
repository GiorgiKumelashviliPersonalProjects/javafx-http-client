package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.controllers.tabs.*;
import com.example.javafxhttpclient.core.enums.ContentTabs;
import com.example.javafxhttpclient.core.enums.HttpMethods;
import com.example.javafxhttpclient.core.networking.Network;
import com.example.javafxhttpclient.core.networking.Response;
import com.example.javafxhttpclient.core.utils.Util;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ContentController implements Initializable {
    private final String formatButtonIcon = FontAwesomeIcon.FILE_TEXT_ALT.name();
    private ContentTabs activeTab;

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
    GridPane responseTabGridPane;

    @FXML
    Label responseStatusCodeLabel;

    @FXML
    Label responseTimeLabel;

    @FXML
    Button formatButton;

    @FXML
    TabPane contentTabPane;

    @FXML
    Tab jsonTabComponent;

    @FXML
    Tab headersTabComponent;

    @FXML
    Tab queryTabComponent;

    @FXML
    JsonTabController jsonTabController;

    @FXML
    HeadersTabController headersTabController;

    @FXML
    QueryTabController queryTabController;

    @FXML
    ResponseJsonController responseJsonController;

    @FXML
    ResponseHeadersController responseHeadersController;

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

        // set tabs
        jsonTabComponent.setText(ContentTabs.JSON.toString());
        headersTabComponent.setText(ContentTabs.HEADERS.toString());
        queryTabComponent.setText(ContentTabs.QUERY.toString());

        // tabs are set with enum, so it's safe to use ContentTabs.valueOf
        contentTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            activeTab = ContentTabs.valueOf(newV.getText());

            // set format only on json tab
            formatButton.setVisible(activeTab == ContentTabs.JSON);
        });

        //! Very important for setting positions of all split panes
        Platform.runLater(() -> {
            Stage stage = (Stage) splitPane.getScene().getWindow();

            if (splitPane != null) {
                splitPane.setDividerPositions(0.14f, 0.6f, 0.65f);
            }

            stage.widthProperty().addListener((observableValue, oldValue, newValue) -> splitPane.setDividerPositions(0.14f, 0.6f, 0.65f));
        });

        responseTabGridPane.setPadding(new Insets(5));
    }

    public void onSendButtonClick() {
        // validate url
        if (Network.isNotValidUrl(urlTextField.getText())) {
            String errorText = "Invalid url, please try another one \n(e.g. https://example.com)";
            Util.showAlertModal(Alert.AlertType.ERROR, errorText);
            return;
        }

        // validate json
        if (!Util.isJsonValid(jsonTabController.getJsonContent())) {
            Util.showAlertModal(Alert.AlertType.ERROR, "Invalid json");
            return;
        }

        String url = urlTextField.getText();
        HttpMethods method = HttpMethods.valueOf(httpMethodsCombobox.getValue());
        String jsonContent = jsonTabController.getJsonContent();
        Map<String, String> headerData = headersTabController.getNameAndValues();
        Map<String, String> queryData = queryTabController.getNameAndValues();

        //        System.out.println(url);
        //        System.out.println(method);
        //        System.out.println(jsonContent);
        //        System.out.println(headerData);
        //        System.out.println(queryData);

        // sen request and handle response
        Response response = null;

        try {
            Network network = new Network(url);
            network.setRequestHeaders(headerData);
            network.setRequestBody(jsonContent);
            network.setRequestQuery(queryData);
            response = network.send(method);
        } catch (Exception e) {
            Util.showAlertModal(Alert.AlertType.ERROR, e.getMessage());
            e.printStackTrace();
        }

        if (response == null) {
            return;
        }

        handleResponse(response);
    }

    public void formatJson() {
        jsonTabController.formatJson();
    }

    public String getFormatButtonIcon() {
        return formatButtonIcon;
    }

    private void handleResponse(Response response) {
        if (response != null) {
            responseJsonController.setJsonContent(response.getData());

            responseHeadersController.setResponseHeaders(response.getHeaders());

            if (response.getStatusCode() <= 299) {
                responseStatusCodeLabel.setStyle("-fx-background-color: #2d8a48");
            } else if (response.getStatusCode() <= 399) {
                responseStatusCodeLabel.setStyle("-fx-background-color: #8a8f33");
            } else {
                responseStatusCodeLabel.setStyle("-fx-background-color: #8f3333");
            }

            responseStatusCodeLabel.setText(
                    response.getStatusCode() + " " + Util.getStatusCodeText(response.getStatusCode())
            );
            responseTimeLabel.setText(response.getResponseTime() + " ms");
            System.out.println("end of line");
        }
    }
}
