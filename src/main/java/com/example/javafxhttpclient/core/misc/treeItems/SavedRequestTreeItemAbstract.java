package com.example.javafxhttpclient.core.misc.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import org.jetbrains.annotations.Nullable;

abstract public class SavedRequestTreeItemAbstract extends TreeItem<String> {
    public abstract ContextMenu getMenu();

    @Nullable
    public MenuItem createItem;

    @Nullable
    public MenuItem deleteItem;

    @Nullable
    public MenuItem renameItem;

    public int id;

    public abstract int getId();
}
