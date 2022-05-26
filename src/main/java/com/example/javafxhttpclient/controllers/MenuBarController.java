package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.core.utils.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {
    @FXML
    MenuBar mainMenuBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Menu fileMenu = new Menu("Files");
        setFileMenu(fileMenu);

        Menu windowMenu = new Menu("Window");
        setWindowMenu(windowMenu);

        Menu helpMenu = new Menu("Help");
        setHelpMenu(helpMenu);

        mainMenuBar.getMenus().addAll(fileMenu, windowMenu, helpMenu);
    }

    public void setFileMenu(Menu menu) {
        // Create new request
        MenuItem newItem = new MenuItem("New Item");
        newItem.setAccelerator(Constants.newRequestKeyCombination);
        newItem.setOnAction(event -> SidebarController.instance.onCreateButtonClick(event));

        // Quit from application
        MenuItem quit = new MenuItem("Quit");
        quit.setAccelerator(Constants.quitKeyCombination);
        quit.setOnAction(event -> ((Stage) mainMenuBar.getScene().getWindow()).close());

        menu.getItems().addAll(newItem, new SeparatorMenuItem(), quit);
    }

    public void setWindowMenu(Menu menu) {
        // Minimize from application
        MenuItem minimize = new MenuItem("Minimize");
        minimize.setAccelerator(Constants.minimizeKeyCombination);
        minimize.setOnAction(event -> ((Stage) mainMenuBar.getScene().getWindow()).setIconified(true));

        // Toggle full screen from application
        MenuItem toggleFullScreen = new MenuItem("Toggle Full Screen");
        toggleFullScreen.setAccelerator(Constants.toggleFullScreenKeyCombination);
        toggleFullScreen.setOnAction(event -> {
            Stage stage = ((Stage) mainMenuBar.getScene().getWindow());
            stage.setFullScreen(!stage.isFullScreen());
        });

        // add item
        menu.getItems().addAll(minimize, toggleFullScreen);
    }

    public void setHelpMenu(Menu menu) {
        // Redirect to browser about page
        MenuItem about = new MenuItem("About");

        // add item
        menu.getItems().addAll(about);
    }
}
