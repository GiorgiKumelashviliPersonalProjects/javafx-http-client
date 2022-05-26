package com.example.javafxhttpclient.core.utils;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class Constants {
    public static String mainFXML = "/com/example/javafxhttpclient/main.fxml";
    public static String mainCss = "/com/example/javafxhttpclient/styles/core/main.css";
    public static String headerInputComponent = "/com/example/javafxhttpclient/fxml/components/HeaderInputsComponent.fxml";
    public static String jsonHighLights = "/com/example/javafxhttpclient/styles/editorArea/jsonHighlight.css";
    public static String addSavedRequestTreeItem = "/com/example/javafxhttpclient/fxml/components/AddTreeItemModal.fxml";
    public static String renameSavedRequestTreeItem = "/com/example/javafxhttpclient/fxml/components/RenameTreeItemModal.fxml";
    public static String checkModal = "/com/example/javafxhttpclient/fxml/components/CheckModal.fxml";
    public static String showModal = "/com/example/javafxhttpclient/fxml/components/AlertModal.fxml";
    public static String savedRequestTreeItemFolderIcon = "/com/example/javafxhttpclient/images/folderIcon.png";

    // key combinations
    public static KeyCombination newRequestKeyCombination = new KeyCodeCombination(KeyCode.F1, KeyCombination.CONTROL_DOWN);
    public static KeyCombination quitKeyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
    public static KeyCombination minimizeKeyCombination = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.SHIFT_DOWN);
    public static KeyCombination toggleFullScreenKeyCombination = new KeyCodeCombination(KeyCode.F11);
}
