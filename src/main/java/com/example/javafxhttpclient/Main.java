package com.example.javafxhttpclient;

import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.core.utils.FileManipulator;
import com.example.javafxhttpclient.db.DatabaseConnection;
import com.example.javafxhttpclient.exceptions.DatabaseNotFoundException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * !TODO -> check and if not exists initialize tables before starting application
 * 
 * TODO -> save everything in local sqlite using jdbc and add when exiting show alert
 * <p>
 * TODO -> menubar items (save, about)
 * TODO -> shortcut on (ctrl + enter) on sending
 * TODO -> shortcut on (ctrl + L) on formatting json
 * TODO -> ask before quit (Are you sure you ant to quit)
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException, DatabaseNotFoundException {
        FXMLLoader mainFxml = FileManipulator.fxmlLoader(Constants.mainFXML);
        String mainCss = FileManipulator.css(Constants.mainCss);

        // creating scene
        Scene scene = new Scene(mainFxml.load());
        scene.getStylesheets().add(mainCss);

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getDbConnection();

        // initializing
        stage.setTitle("Http Client");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}