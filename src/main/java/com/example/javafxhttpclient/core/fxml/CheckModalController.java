package com.example.javafxhttpclient.core.fxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CheckModalController implements Initializable {
    private static final int MAX_WIDTH = 490;
    private static final String DEFAULT_TEXT = "Are you sure you want to do this ?";

    @FXML
    public Button yesButton;

    @FXML
    public Button noButton;

    @FXML
    public Text checkText;

    public void setText(String text) {
        checkText.setText(Optional.ofNullable(text).orElse(DEFAULT_TEXT));
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkText.setWrappingWidth(MAX_WIDTH);
        checkText.maxWidth(MAX_WIDTH);
        checkText.setFont(Font.font(20));
        checkText.setText(DEFAULT_TEXT);
        checkText.setFill(Color.valueOf("#fdff8c"));
    }
}
