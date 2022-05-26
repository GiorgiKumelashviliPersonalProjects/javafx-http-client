package com.example.javafxhttpclient.core.treeItems;

import com.example.javafxhttpclient.controllers.SidebarController;
import com.example.javafxhttpclient.core.treeItems.fragments.SavedRequestTreeCellImpl;
import com.example.javafxhttpclient.core.treeItems.fragments.SavedRequestTreeItemAbstract;

public class FilteredSavedRequestTreeCellImpl extends SavedRequestTreeCellImpl {
    public FilteredSavedRequestTreeCellImpl(SidebarController sidebarController) {
        super(sidebarController);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty) {
            SavedRequestTreeItemAbstract treeItem = (SavedRequestTreeItemAbstract) getTreeItem();
            treeItem.setExpanded(true);
        }
    }
}