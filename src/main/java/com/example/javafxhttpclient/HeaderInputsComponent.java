package com.example.javafxhttpclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HeaderInputsComponent extends VBox {

    public HeaderInputsComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javafxhttpclient/reusable-components/header-inputs.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("=".repeat(30));
            System.out.println(exception);
            throw new RuntimeException(exception);
        }
    }
}
