package com.example.javafxhttpclient;

import com.example.javafxhttpclient.utils.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HttpClientDemo extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HttpClientDemo.class.getResource("main.fxml"));
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