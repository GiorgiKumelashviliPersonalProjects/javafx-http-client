package com.example.javafxhttpclient.core.enums;

public enum SavedTreeItemType {
    REQUEST("REQUEST"),
    FOLDER("FOLDER");

    private final String method;

    SavedTreeItemType(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return method;
    }

    public static String[] all() {
        return new String[]{
                REQUEST.toString(),
                FOLDER.toString()
        };
    }
}
