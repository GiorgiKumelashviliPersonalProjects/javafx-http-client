package com.example.javafxhttpclient;

import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.core.utils.FileManipulator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * TODO -> send http request
 * TODO -> show http response
 * TODO -> save everything in local sqlite using jdbc and add when exiting show alert
 * TODO -> all treeview actions (create,rename,delete from modal and context menu and menubar)
 * TODO -> filter tree items
 *
 * TODO -> (BUG) json format button must be only seen on json content (move button inside json fxml)
 * TODO -> shortcut on (ctrl + enter) on sending
 * TODO -> shortcut on (ctrl + L) on formatting json
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainFxml = FileManipulator.fxmlLoader(Constants.mainFXML);
        String mainCss = FileManipulator.css(Constants.mainCss);

        // creating scene
        Scene scene = new Scene(mainFxml.load());
        scene.getStylesheets().add(mainCss);

        // initializing
        stage.setTitle("Http Client");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}