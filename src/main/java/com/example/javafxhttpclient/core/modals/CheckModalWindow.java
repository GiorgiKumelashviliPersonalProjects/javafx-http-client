package com.example.javafxhttpclient.core.modals;

import com.example.javafxhttpclient.controllers.fragments.CheckModalController;
import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.core.utils.FileManipulator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckModalWindow {
    private boolean answer;

    public CheckModalWindow() {
    }

    public void show(String checkText, TreeItemModalWindowCallback callback) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modalFxml = FileManipulator.fxmlLoader(Constants.checkModal);
        String mainCss = FileManipulator.css(Constants.mainCss);
        Scene scene = new Scene(modalFxml.load());
        CheckModalController checkModalController = modalFxml.getController();

        if (checkText != null) {
            checkModalController.setText(checkText);
        }

        // on save
        checkModalController.yesButton.setOnAction(e -> {
            answer = true;
            stage.close();
            callback.onClose();
        });

        // on close
        checkModalController.noButton.setOnAction(e -> {
            answer = false;
            stage.close();
            callback.onClose();
        });

        scene.getStylesheets().add(mainCss);
        stage.setScene(scene);
        stage.setTitle("Delete tree item");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public void show(TreeItemModalWindowCallback callback) throws IOException {
        this.show(null, callback);
    }

    public boolean isAnswerYes() {
        return answer;
    }
}
