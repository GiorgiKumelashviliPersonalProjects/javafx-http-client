package com.example.javafxhttpclient.db;

import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.core.utils.FileManipulator;
import com.example.javafxhttpclient.exceptions.DatabaseNotFoundException;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection getDbConnection() throws DatabaseNotFoundException, SQLException {
        URL mainDbPath = FileManipulator.resource(Constants.mainDatabase);

        if (mainDbPath == null) {
            throw new DatabaseNotFoundException(Constants.mainDatabase);
        }

        String url = "jdbc:sqlite:".concat(mainDbPath.getPath());

        return DriverManager.getConnection(url);
    }
}
