package com.example.javafxhttpclient.controllers.tabs;

import com.example.javafxhttpclient.core.codearea.EditorArea;
import com.example.javafxhttpclient.core.codearea.JSONFormatter;
import com.example.javafxhttpclient.core.codearea.JSONHighlighter;
import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.core.utils.FileManipulator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.fxmisc.flowless.VirtualizedScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ResponseJsonController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    private EditorArea rawInputArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String jsonCss = FileManipulator.css(Constants.jsonHighLights);

        rawInputArea = new EditorArea();
        rawInputArea.addStyleSheet(jsonCss);
        rawInputArea.setText("{}", new JSONFormatter(), new JSONHighlighter());

        VirtualizedScrollPane<EditorArea> codeArea = new VirtualizedScrollPane<>(rawInputArea);
        AnchorPane.setRightAnchor(codeArea, 0.0);
        AnchorPane.setLeftAnchor(codeArea, 0.0);
        AnchorPane.setBottomAnchor(codeArea, 0.0);
        AnchorPane.setTopAnchor(codeArea, 0.0);

        anchorPane.getChildren().add(codeArea);
    }

    public EditorArea getCodeArea() {
        return rawInputArea;
    }

    public void setJsonContent(String content) {
        getCodeArea().setText(content, new JSONFormatter(), new JSONHighlighter());
    }
}
