module com.example.javafxhttpclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires org.fxmisc.richtext;

    opens com.example.javafxhttpclient to javafx.fxml;
    opens com.example.javafxhttpclient.controllers to javafx.fxml;

    exports com.example.javafxhttpclient;
    exports com.example.javafxhttpclient.controllers;
    exports com.example.javafxhttpclient.components;
    opens com.example.javafxhttpclient.components to javafx.fxml;
    exports com.example.javafxhttpclient.controllers.tabs;
    opens com.example.javafxhttpclient.controllers.tabs to javafx.fxml;
}