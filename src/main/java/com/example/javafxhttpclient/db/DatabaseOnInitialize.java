package com.example.javafxhttpclient.db;

import com.example.javafxhttpclient.entities.RequestDataEntity;
import com.example.javafxhttpclient.entities.RequestEntity;
import com.example.javafxhttpclient.exceptions.DatabaseNotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOnInitialize {
    public static void onInit() throws SQLException {
        Connection databaseConnection = null;
        Statement statement = null;

        try {
            databaseConnection = DatabaseConnection.getDbConnection();
            statement = databaseConnection.createStatement();

            String initializeQuery = """
                    CREATE TABLE IF NOT EXISTS [main].request_entity (
                      %s INTEGER PRIMARY KEY AUTOINCREMENT,
                      %s INTEGER,
                      %s TEXT NOT NULL,
                      %s TEXT NOT NULL,
                      
                      CONSTRAINT fk_column
                          FOREIGN KEY (%s)
                          REFERENCES request_entity (%s)
                    );
                    
                    CREATE TABLE IF NOT EXISTS [main].request_entity_data (
                      %s INTEGER PRIMARY KEY AUTOINCREMENT,
                      %s INTEGER NOT NULL,
                      %s TEXT NOT NULL,
                      %s TEXT NOT NULL,
                      %s TEXT,
                      %s TEXT,
                      %s TEXT,
                      
                      CONSTRAINT fk_column
                          FOREIGN KEY (%s)
                          REFERENCES request_entity (%s)
                    );
                    """
                    .formatted(
                            RequestEntity.ID_COLUMN_NAME,
                            RequestEntity.REQUEST_ENTITY_ID_COLUMN_NAME,
                            RequestEntity.TYPE_COLUMN_NAME,
                            RequestEntity.NAME_COLUMN_NAME,

                            // foreign keys
                            RequestEntity.REQUEST_ENTITY_ID_COLUMN_NAME,
                            RequestEntity.ID_COLUMN_NAME,

                            RequestDataEntity.ID_COLUMN_NAME,
                            RequestDataEntity.REQUEST_ENTITY_ID_COLUMN_NAME,
                            RequestDataEntity.URL_COLUMN_NAME,
                            RequestDataEntity.METHOD_COLUMN_NAME,
                            RequestDataEntity.JSON_COLUMN_NAME,
                            RequestDataEntity.HEADERS_COLUMN_NAME,
                            RequestDataEntity.QUERIES_COLUMN_NAME,

                            // foreign keys
                            RequestDataEntity.REQUEST_ENTITY_ID_COLUMN_NAME,
                            RequestEntity.ID_COLUMN_NAME
                    );

            statement.executeUpdate(initializeQuery);
            System.out.println("initialize query execution success");
        } catch (DatabaseNotFoundException | SQLException e) {
            System.out.println("=============");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (databaseConnection != null) databaseConnection.close();
        }
    }

    public static void test(Connection connection) throws SQLException {
        String query = "SELECT * FROM MAIN";
        Statement statement = connection.createStatement();
        ResultSet queryOutput = statement.executeQuery(query);

        while (queryOutput.next()) {
            System.out.println(queryOutput.getString("ID"));
            System.out.println(queryOutput.getString("NAME"));
        }
    }
}
