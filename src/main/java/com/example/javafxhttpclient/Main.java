package com.example.javafxhttpclient;

import com.example.javafxhttpclient.utils.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * TODO -> add filter and plus icon button above tree view
 * TODO -> more padding on tree items and add text icons on tree items just like in insomnia
 * TODO -> add context menu on treeview items
 * TODO -> add json text area
 * TODO -> add headers area
 * TODO -> add response area
 * TODO -> add when exiting show alert and save everything in local sqlite using jdbc
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Constants.MIN_WIDTH, Constants.MIN_HEIGHT);

        // styling
        URL mainStyleResource = getClass().getResource("/com/example/javafxhttpclient/styles/main.css");
        scene.getStylesheets().add(Objects.requireNonNull(mainStyleResource).toExternalForm());

        stage.setTitle("Hello!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}