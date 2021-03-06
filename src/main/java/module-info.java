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
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires org.json;

    requires java.sql;
    requires org.xerial.sqlitejdbc;

    requires event.bus;
    requires com.google.gson;

    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;
    requires annotations;

    exports com.example.javafxhttpclient.exceptions;

    exports com.example.javafxhttpclient.core.misc.codearea;
    exports com.example.javafxhttpclient.core.enums;

    exports com.example.javafxhttpclient.controllers;
    opens com.example.javafxhttpclient.controllers to javafx.fxml;

    exports com.example.javafxhttpclient;
    opens com.example.javafxhttpclient to javafx.fxml;

    exports com.example.javafxhttpclient.controllers.tabs;
    opens com.example.javafxhttpclient.controllers.tabs to javafx.fxml;

    exports com.example.javafxhttpclient.core.models.fxml;
    exports com.example.javafxhttpclient.entities;
    exports com.example.javafxhttpclient.controllers.fragments;
    opens com.example.javafxhttpclient.controllers.fragments to javafx.fxml;
}