package com.example.javafxhttpclient.core.treeItems.fragments;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

abstract public class SavedRequestTreeItemAbstr extends TreeItem<String> {
    public abstract ContextMenu getMenu();
}
