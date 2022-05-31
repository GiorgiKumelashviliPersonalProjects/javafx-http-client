package com.example.javafxhttpclient.exceptions;

public class DatabaseNotFoundException extends Exception {
    public DatabaseNotFoundException(String path) {
        super("Local sqlite database not found, path: ".concat(path));
    }
}
