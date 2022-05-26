package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.controllers.tabs.*;
import com.example.javafxhttpclient.core.enums.ContentTabs;
import com.example.javafxhttpclient.core.enums.HttpMethods;
import com.example.javafxhttpclient.core.networking.Network;
import com.example.javafxhttpclient.core.networking.Response;
import com.example.javafxhttpclient.core.utils.Util;
import com.example.javafxhttpclient.exceptions.NoJsonResponseException;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ContentController implements Initializable {
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

        Service<Response> backgroundThread = new Service<>() {
            @Override
            protected Task<Response> createTask() {
                return new Task<>() {
                    @Override
                    protected Response call() throws IOException, NoJsonResponseException {
                        Network network = new Network(url);
                        network.setRequestHeaders(headerData);
                        network.setRequestBody(jsonContent);
                        network.setRequestQuery(queryData);

                        return network.send(method);
                    }
                };
            }
        };

        backgroundThread.setOnSucceeded(event -> {
            Response response = (Response) event.getSource().getValue();
            handleResponse(response);
            // System.out.println("fetched" + Util.randInt(100));
        });

        backgroundThread.setOnCancelled(e -> {
            Throwable exception = e.getSource().getException();
            String message = exception.getMessage();

            Util.showAlertModal(Alert.AlertType.ERROR, message);
            exception.printStackTrace();
            // System.out.println("Error message: " + message);
        });

        backgroundThread.setOnFailed(e -> {
            Throwable exception = e.getSource().getException();
            String message = exception.getMessage();

            Util.showAlertModal(Alert.AlertType.ERROR, message);
            exception.printStackTrace();
            // System.out.println("Error message: " + message);
        });

        backgroundThread.restart();
    }

    public void formatJson() {
        jsonTabController.formatJson();
    }

    private void handleResponse(Response response) {
        if (response != null) {
            String statusCodeText = response.getStatusCode() + " " + Util.getStatusCodeText(response.getStatusCode());
            String responseTimeText = response.getResponseTime() + " ms";
            String responseStatusCodeLabelStyle;


            if (response.getStatusCode() <= 299) {
                responseStatusCodeLabelStyle = "-fx-background-color: #2d8a48";
            } else if (response.getStatusCode() <= 399) {
                responseStatusCodeLabelStyle = "-fx-background-color: #8a8f33";
            } else {
                responseStatusCodeLabelStyle = "-fx-background-color: #8f3333";
            }

            // set all response stuff
            responseStatusCodeLabel.setStyle(responseStatusCodeLabelStyle);
            responseJsonController.setJsonContent(response.getData());
            responseHeadersController.setResponseHeaders(response.getHeaders());
            responseStatusCodeLabel.setText(statusCodeText);
            responseTimeLabel.setText(responseTimeText);

            // response done
            System.out.printf("response completed in %s%n", responseTimeText);
        }
    }
}
