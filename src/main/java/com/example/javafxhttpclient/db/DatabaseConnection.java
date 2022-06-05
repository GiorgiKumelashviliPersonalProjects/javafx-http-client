package com.example.javafxhttpclient.db;

import com.example.javafxhttpclient.core.utils.Constants;
import com.example.javafxhttpclient.core.utils.FileManipulator;
import com.example.javafxhttpclient.core.utils.GeneralCallable;
import com.example.javafxhttpclient.core.utils.GeneralCallablePrepared;
import com.example.javafxhttpclient.entities.RequestDataEntity;
import com.example.javafxhttpclient.entities.RequestEntity;
import com.example.javafxhttpclient.exceptions.DatabaseNotFoundException;
import org.sqlite.SQLiteConfig;

import java.net.URL;
import java.sql.*;

public class DatabaseConnection {

    public static Connection getDbConnection() throws DatabaseNotFoundException, SQLException {
        URL mainDbPath = FileManipulator.resource(Constants.mainDatabase);

        if (mainDbPath == null) {
            throw new DatabaseNotFoundException(Constants.mainDatabase);
        }

        String url = "jdbc:sqlite:".concat(mainDbPath.getPath());

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        return DriverManager.getConnection(url, config.toProperties());
    }

    public static ResultSet executeSelect(String query) throws SQLException {
        try (
                Connection databaseConnection = DatabaseConnection.getDbConnection();
                Statement statement = databaseConnection.createStatement()
        ) {
            return statement.executeQuery(query);
        } catch (DatabaseNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }

        //        Statement statement = connection.createStatement();
        //        ResultSet queryOutput = statement.executeQuery(query);

        //        while (queryOutput.next()) {
        //            System.out.println(queryOutput.getString("ID"));
        //            System.out.println(queryOutput.getString("NAME"));
        //        }
    }

    public static void executeSelectClbck(GeneralCallable callable) throws SQLException {
        try (
                Connection databaseConnection = DatabaseConnection.getDbConnection();
                Statement statement = databaseConnection.createStatement()
        ) {
            callable.onCallbackDb(statement);
        } catch (DatabaseNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public static void executeCallbackPrepared(String query, GeneralCallablePrepared callable) throws SQLException {
        try (
                Connection databaseConnection = DatabaseConnection.getDbConnection();
                PreparedStatement statement = databaseConnection.prepareStatement(query)
        ) {
            callable.onCallbackDb(statement);
        } catch (DatabaseNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
