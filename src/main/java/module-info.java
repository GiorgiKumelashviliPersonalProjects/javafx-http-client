module com.example.javafxhttpclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.example.javafxhttpclient to javafx.fxml;
    opens com.example.javafxhttpclient.controllers to javafx.fxml;

    exports com.example.javafxhttpclient;
    exports com.example.javafxhttpclient.controllers;
}