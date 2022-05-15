module com.example.javafxhttpclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;
    requires reactfx;
    requires wellbehavedfx;
    requires java.datatransfer;
    requires java.desktop;

    opens com.example.javafxhttpclient to javafx.fxml;
    opens com.example.javafxhttpclient.controllers to javafx.fxml;

    exports com.example.javafxhttpclient;
    exports com.example.javafxhttpclient.controllers;
    exports com.example.javafxhttpclient.components;
    opens com.example.javafxhttpclient.components to javafx.fxml;
    exports com.example.javafxhttpclient.controllers.tabs;
    opens com.example.javafxhttpclient.controllers.tabs to javafx.fxml;

    exports com.example.javafxhttpclient.demo.hyperlink;
    exports com.example.javafxhttpclient.demo.richtext;
    exports com.example.javafxhttpclient.demo.brackethighlighter;
    exports com.example.javafxhttpclient.demo.lineindicator;

    opens com.example.javafxhttpclient.demo to javafx.graphics;
//    com.example.javafxhttpclient.demo.hyperlink
}