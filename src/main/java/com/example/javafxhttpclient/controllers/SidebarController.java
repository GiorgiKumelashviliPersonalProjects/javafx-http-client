package com.example.javafxhttpclient.controllers;

import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.treeItems.SavedRequestTreeItem;
import com.example.javafxhttpclient.core.treeItems.fragments.SavedRequestTreeCellImpl;
import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.core.utils.FileManipulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class SidebarController implements Initializable {
    @FXML
    public AnchorPane rootParent;

    @FXML
    public TreeView<String> savedRequests;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SavedRequestTreeItem root1 = new SavedRequestTreeItem(SavedTreeItemType.FOLDER, "Simple api request");
        SavedRequestTreeItem root2 = new SavedRequestTreeItem(SavedTreeItemType.FOLDER, "Some folder");
        SavedRequestTreeItem root3 = new SavedRequestTreeItem(SavedTreeItemType.REQUEST, "testing");

        SavedRequestTreeItem model11 = new SavedRequestTreeItem(SavedTreeItemType.REQUEST, "Json test 1");
        SavedRequestTreeItem model12 = new SavedRequestTreeItem(SavedTreeItemType.REQUEST, "Json test 2");
        root1.setChildren(model11, model12);

        // set root which is not visible
        TreeItem<String> invisibleRoot = new TreeItem<>(null);
        invisibleRoot.getChildren().addAll(root1.getItem(), root2.getItem(), root3.getItem());


        savedRequests.setCellFactory(p -> {
            SavedRequestTreeCellImpl savedRequestTreeCell = new SavedRequestTreeCellImpl();

            //TODO here is single click toggle
//            savedRequestTreeCell.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
//                System.out.println("==================");
//                System.out.println(e.toString());
//
//                System.out.println(e.getClickCount());
//                System.out.println(e.getButton());
//
//                System.out.println(e.getClickCount() % 2 == 1);
//                System.out.println(e.getButton().equals(MouseButton.PRIMARY));
//
//                if (e.getClickCount() % 2 == 1 && e.getButton().equals(MouseButton.PRIMARY)) {
//                    System.out.println("consumed");
//                    e.consume();
//
//                }
//
//                System.out.println("==================");
//            });
            return savedRequestTreeCell;
        });
        savedRequests.setRoot(invisibleRoot);
        savedRequests.setShowRoot(false);
    }

    public void openModal(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modalFxml = FileManipulator.fxmlLoader(Constants.addSavedRequestTreeItem);
        String mainCss = FileManipulator.css(Constants.mainCss);

        Scene addTreeItemModalScene = new Scene(modalFxml.load());
        addTreeItemModalScene.getStylesheets().add(mainCss);

        stage.setScene(addTreeItemModalScene);
        stage.setTitle("My modal window");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }
}
