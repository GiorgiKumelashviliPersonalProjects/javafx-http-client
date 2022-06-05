package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.controllers.tabs.*;
import com.example.javafxhttpclient.core.enums.ContentTabs;
import com.example.javafxhttpclient.core.enums.HttpMethods;
import com.example.javafxhttpclient.core.misc.treeItems.SavedRequestTreeItemAbstract;
import com.example.javafxhttpclient.core.networking.Network;
import com.example.javafxhttpclient.core.networking.Response;
import com.example.javafxhttpclient.core.utils.Util;
import com.example.javafxhttpclient.entities.RequestDataEntity;
import com.example.javafxhttpclient.entities.RequestEntity;
import com.example.javafxhttpclient.exceptions.NoJsonResponseException;
import com.google.gson.Gson;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;
import org.reactfx.util.FxTimer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContentController implements Initializable {
    private ContentTabs activeTab;

    private int activeId;

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

    @FXML
    SidebarController sidebarController;

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

        initializeRequestDataEntities();

        // Set listeners
        onUrlChange();
        onMethodChange();
        onJsonChange();
        onHeadersAndQueryChange();
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

    public void initializeRequestDataEntities() {
        sidebarController.rootTreeView.setOnMousePressed(e -> {
            if (e.getClickCount() == 2) {
                SavedRequestTreeItemAbstract item = (SavedRequestTreeItemAbstract) sidebarController
                        .rootTreeView
                        .getSelectionModel()
                        .getSelectedItem();

                activeId = item.getId();
                RequestEntity foundRequest = sidebarController.findItem(activeId);
                RequestDataEntity requestDataEntity = foundRequest != null ? foundRequest.getRequestDataEntity() : null;

                if (requestDataEntity == null) {
                    // clear everything on ui and state
                    urlTextField.setText("");
                    httpMethodsCombobox.setValue(String.valueOf(HttpMethods.GET));
                    jsonTabController.clearJsonContent();
                    headersTabController.clearHeaders();
                    queryTabController.clearQueries();
                    return;
                }

                urlTextField.setText(requestDataEntity.getUrl());
                httpMethodsCombobox.setValue(String.valueOf(requestDataEntity.getMethod()));

                if (requestDataEntity.getJsonContent() != null) {
                    jsonTabController.setJsonContent(requestDataEntity.getJsonContent());
                } else {
                    jsonTabController.clearJsonContent();
                }

                if (requestDataEntity.getHeaderData() != null && requestDataEntity.getHeaderData().size() > 0) {
                    headersTabController.setHeaderData(requestDataEntity.getHeaderData());
                } else {
                    headersTabController.clearHeaders();
                }

                if (requestDataEntity.getQueryData() != null && requestDataEntity.getQueryData().size() > 0) {
                    queryTabController.setQueryData(requestDataEntity.getQueryData());
                } else {
                    queryTabController.clearQueries();
                }
            }
        });
    }

    // |=====================================================
    // | DETECT CHANGES
    // |=====================================================

    public void onUrlChange() {
        // debounce for one second of url change
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        urlTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> {
                RequestEntity dataEntity = sidebarController.findItem(activeId);

                if (dataEntity != null && dataEntity.getRequestDataEntity() != null) {
                    Objects.requireNonNull(dataEntity.getRequestDataEntity()).setUrl(newValue);

                    try {
                        RequestDataEntity.updateColumn(
                                dataEntity.getRequestDataEntity().getId(),
                                RequestDataEntity.URL_COLUMN_NAME,
                                newValue
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pause.playFromStart();
        });
    }

    public void onMethodChange() {
        httpMethodsCombobox
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable1, oldValue1, newValue) -> {
                    RequestEntity dataEntity = sidebarController.findItem(activeId);

                    if (dataEntity != null && dataEntity.getRequestDataEntity() != null) {
                        dataEntity.getRequestDataEntity().setMethod(HttpMethods.valueOf(newValue));
                        try {
                            System.out.println(dataEntity.getRequestDataEntity().getId());
                            System.out.println(newValue);
                            System.out.println(HttpMethods.valueOf(newValue));


                            RequestDataEntity.updateColumn(
                                    dataEntity.getRequestDataEntity().getId(),
                                    RequestDataEntity.METHOD_COLUMN_NAME,
                                    newValue
                            );
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    public void onJsonChange() {
        // debounce for one second of url change
        PauseTransition pause = new PauseTransition(Duration.millis(500));
        jsonTabController
                .getCodeArea()
                .textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    pause.setOnFinished(event -> {
                        System.out.println(123);
                        System.out.println(newValue);

                        if (!Util.isJsonValid(newValue)) {
                            return;
                        }

                        RequestEntity dataEntity = sidebarController.findItem(activeId);

                        if (dataEntity != null && dataEntity.getRequestDataEntity() != null) {
                            dataEntity.getRequestDataEntity().setJsonContent(newValue);

                            try {
                                RequestDataEntity.updateColumn(
                                        dataEntity.getRequestDataEntity().getId(),
                                        RequestDataEntity.JSON_COLUMN_NAME,
                                        newValue
                                );
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    pause.playFromStart();
                });
    }

    /**
     * @important Resource intensive method
     */
    public void onHeadersAndQueryChange() {
        FxTimer.createPeriodic(java.time.Duration.ofSeconds(2), () -> {
            RequestEntity dataEntity = sidebarController.findItem(activeId);

            if (dataEntity != null && dataEntity.getRequestDataEntity() != null) {
                Gson gson = new Gson();

                var headerValues = headersTabController.getNameAndValues();
                var queryValues = queryTabController.getNameAndValues();


                // SQL
                try {
                    RequestDataEntity.updateColumn(
                            dataEntity.getRequestDataEntity().getId(),
                            RequestDataEntity.HEADERS_COLUMN_NAME,
                            gson.toJson(headerValues)
                    );

                    RequestDataEntity.updateColumn(
                            dataEntity.getRequestDataEntity().getId(),
                            RequestDataEntity.QUERIES_COLUMN_NAME,
                            gson.toJson(queryValues)
                    );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                // FXML, STATE
                dataEntity.getRequestDataEntity().setHeaderData(headerValues);
                dataEntity.getRequestDataEntity().setQueryData(queryValues);



                System.out.println(headersTabController.getNameAndValues());
                System.out.println(queryTabController.getNameAndValues());
            }
        }).restart();
    }
}
