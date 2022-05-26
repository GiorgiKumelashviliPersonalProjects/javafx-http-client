package com.example.javafxhttpclient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {
    @FXML
    MenuBar mainMenuBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Menu fileMenu = new Menu("Files");

        MenuItem newRequest = new MenuItem("New Request");

        // key combination
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.F2, KeyCombination.CONTROL_DOWN);
        newRequest.setAccelerator(keyCombination);
        // newRequest.setOnAction(event -> {
        //     System.out.println("triggered");
        //     System.out.println(event.toString());
        // });


        MenuItem newFolder = new MenuItem("New Folder");
        MenuItem save = new MenuItem("Save");
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        MenuItem quit = new MenuItem("Quit");

        fileMenu.getItems().addAll(newRequest, newFolder, save, separatorMenuItem, quit);


        Menu windowMenu = new Menu("Window");
        MenuItem minimize = new MenuItem("Minimize");
        MenuItem toggleFullScreen = new MenuItem("Toggle Full Screen");

        windowMenu.getItems().addAll(minimize, toggleFullScreen);


        Menu helpMenu = new Menu("Help");
        MenuItem about = new MenuItem("About");

        helpMenu.getItems().addAll(about);

        helpMenu.setMnemonicParsing(false);

        mainMenuBar.getMenus().addAll(fileMenu, windowMenu, helpMenu);
    }
}
