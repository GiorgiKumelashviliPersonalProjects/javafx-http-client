package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.controllers.tabs.HeadersTabController;
import com.example.javafxhttpclient.controllers.tabs.JsonTabController;
import com.example.javafxhttpclient.controllers.tabs.QueryTabController;
import com.example.javafxhttpclient.core.enums.ContentTabs;
import com.example.javafxhttpclient.core.enums.HttpMethods;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

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
            stage.widthProperty().addListener((observableValue, oldValue, newValue) -> {
                splitPane.setDividerPositions(0.14f, 0.6f, 0.65f);
            });
        });
    }

    public void onSendButtonClick(ActionEvent event) {
        // validate url
//        if (Network.isNotValidUrl(urlTextField.getText())) {
//            try {
//                String errorText = "Invalid url, please try another one \n(e.g. https://example.com)";
//                Util.showAlertModal(event, Alert.AlertType.ERROR, errorText);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            return;
//        }

        String url = urlTextField.getText();
        String method = httpMethodsCombobox.getValue();
        String jsonContent = jsonTabController.getJsonContent();
        Map<String, String> headerData = headersTabController.getNameAndValues();
        Map<String, String> queryData = queryTabController.getNameAndValues();

        System.out.println(url);
        System.out.println(method);
        System.out.println(jsonContent);
        System.out.println(headerData);
        System.out.println(queryData);
    }

    public void formatJson() {
        jsonTabController.formatJson();
    }

    public String getFormatButtonIcon() {
        return formatButtonIcon;
    }

    public void test(MouseEvent event) {
//        System.out.println(event.getTarget().getClass());
//        var x = (Text) event.getTarget();

//        System.out.println(x.get);
//        System.out.println(event);
    }
}
