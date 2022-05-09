package com.example.javafxhttpclient.utils;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class DragResizer {
    private final Region region;

    public DragResizer(Region aRegion) {
        this.region = aRegion;
    }

    public static void makeResizable(Region region) {
        final DragResizer resizer = new DragResizer(region);

        region.setOnMouseDragged(resizer::mouseDragged);
        region.setOnMouseMoved(resizer::mouseOver);
        region.setOnMouseReleased(resizer::mouseReleased);
    }

    protected void mouseReleased(MouseEvent event) {
        region.setCursor(Cursor.DEFAULT);
    }

    protected void mouseOver(MouseEvent event) {
        double mousePosition = event.getX();

        if (mousePosition <= 50) {
            region.setCursor(Cursor.H_RESIZE);
        } else {
            region.setCursor(Cursor.DEFAULT);
        }
    }

    private void mouseDragged(MouseEvent event) {
        double mousePosition = event.getX();

        if (region.getWidth() - event.getX() < 10) {
            return;
        }

        if (mousePosition < 0) {
            int absPos = Math.abs((int) mousePosition);
            region.setPrefWidth(region.getWidth() + absPos);
            region.setMaxWidth(region.getWidth() + absPos);
        }
        else {
            region.setPrefWidth(region.getWidth() - event.getX());
            region.setMaxWidth(region.getWidth() - event.getX());
        }
    }
}