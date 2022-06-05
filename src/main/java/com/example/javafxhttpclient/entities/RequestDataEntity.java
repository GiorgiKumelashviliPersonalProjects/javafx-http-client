package com.example.javafxhttpclient.entities;

import com.example.javafxhttpclient.core.enums.HttpMethods;
import com.example.javafxhttpclient.core.enums.SavedTreeItemType;
import com.example.javafxhttpclient.core.utils.Util;
import com.example.javafxhttpclient.db.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.Nullable;
import org.json.JSONML;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class RequestDataEntity {
    private final int id;
    private final int requestEntityId;

    private String url;

    private HttpMethods method;

    @Nullable
    private String jsonContent;

    @Nullable
    private Map<String, String> headerData;

    @Nullable
    private Map<String, String> queryData;

    // For sqlite
    public static final String TABLE_NAME = "request_entity_data";
    public static final String ID_COLUMN_NAME = "id";
    public static final String REQUEST_ENTITY_ID_COLUMN_NAME = "request_entity_id";
    public static final String URL_COLUMN_NAME = "url";
    public static final String METHOD_COLUMN_NAME = "method";
    public static final String JSON_COLUMN_NAME = "json"; // text (what an irony :p)
    public static final String HEADERS_COLUMN_NAME = "headers"; // text as json
    public static final String QUERIES_COLUMN_NAME = "queries"; // text as json

    public RequestDataEntity(int id, int requestEntityId, String url, HttpMethods method) {
        this.id = id;
        this.requestEntityId = requestEntityId;
        this.url = url;
        this.method = method;
    }

    // |=====================================================
    // | SETTERS
    // |=====================================================

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(HttpMethods method) {
        this.method = method;
    }

    public void setJsonContent(@Nullable String jsonContent) {
        this.jsonContent = jsonContent;
    }

    public void setHeaderData(@Nullable Map<String, String> headerData) {
        this.headerData = headerData;
    }

    public void setQueryData(@Nullable Map<String, String> queryData) {
        this.queryData = queryData;
    }

    // |=====================================================
    // | GETTERS
    // |=====================================================

    public int getId() {
        return id;
    }

    public int getRequestEntityId() {
        return requestEntityId;
    }

    public String getUrl() {
        return url;
    }

    public HttpMethods getMethod() {
        return method;
    }

    @Nullable
    public String getJsonContent() {
        return jsonContent;
    }

    @Nullable
    public Map<String, String> getHeaderData() {
        return headerData;
    }

    @Nullable
    public Map<String, String> getQueryData() {
        return queryData;
    }

    // |=====================================================
    // | DATABASE
    // |=====================================================

    public static List<RequestDataEntity> getAllEntityFromDb() throws SQLException {
        String selectRequestDataEntitiesRows = "SELECT * FROM %s".formatted(RequestDataEntity.TABLE_NAME);
        List<RequestDataEntity> requestDataEntitiesListTemp = new ArrayList<>();

        DatabaseConnection.executeSelectClbck(stm -> {
            ResultSet requestDataEntitiesData = stm.executeQuery(selectRequestDataEntitiesRows);

            int fetchSize = 0;
            while (requestDataEntitiesData.next()) {
                fetchSize++;
                int id = requestDataEntitiesData.getInt(RequestDataEntity.ID_COLUMN_NAME);
                int requestEntityId = requestDataEntitiesData.getInt(RequestDataEntity.REQUEST_ENTITY_ID_COLUMN_NAME);
                String requestUrl = requestDataEntitiesData.getString(RequestDataEntity.URL_COLUMN_NAME);
                HttpMethods method = HttpMethods.valueOf(requestDataEntitiesData.getString(RequestDataEntity.METHOD_COLUMN_NAME));

                // nullables
                String jsonContent = requestDataEntitiesData.getString(RequestDataEntity.JSON_COLUMN_NAME);

                if (jsonContent != null && !jsonContent.isEmpty()) {
                    jsonContent = Util.deserializeJson(jsonContent);
                    System.out.println(jsonContent);
                }

                Map<String, String> headerData = new HashMap<>();
                Map<String, String> queryData = new HashMap<>();

                String nullableHeaderJson = requestDataEntitiesData.getString(RequestDataEntity.HEADERS_COLUMN_NAME);
                String nullableQueryJson = requestDataEntitiesData.getString(RequestDataEntity.QUERIES_COLUMN_NAME);

                if (nullableHeaderJson != null && !nullableHeaderJson.isEmpty()) {
                    headerData = Util.getMapFromJson(Util.deserializeJson(nullableHeaderJson));
                }

                if (nullableQueryJson != null && !nullableQueryJson.isEmpty()) {
                    queryData = Util.getMapFromJson(Util.deserializeJson(nullableQueryJson));
                }

                // create temp {RequestDataEntity}
                RequestDataEntity temp = new RequestDataEntity(id, requestEntityId, requestUrl, method);
                temp.setJsonContent(jsonContent);
                temp.setHeaderData(headerData);
                temp.setQueryData(queryData);
                requestDataEntitiesListTemp.add(temp);
            }

            System.out.println("requestDataEntitiesData fetchSize: " + fetchSize);
        });

        return requestDataEntitiesListTemp;
    }

    public static RequestDataEntity insertNewEntityInDb(
            int requestEntityIdValue,
            String newUrl,
            HttpMethods newMethod,
            boolean returnValue
    ) throws SQLException {
        String query = "INSERT INTO %s (%s, %s, %s) values (?, ?, ?)"
                .formatted(
                        RequestDataEntity.TABLE_NAME,
                        RequestDataEntity.REQUEST_ENTITY_ID_COLUMN_NAME,
                        RequestDataEntity.URL_COLUMN_NAME,
                        RequestDataEntity.METHOD_COLUMN_NAME
                );

        DatabaseConnection.executeCallbackPrepared(query, stmt -> {
            stmt.setInt(1, requestEntityIdValue);
            stmt.setString(2, newUrl);
            stmt.setString(3, String.valueOf(newMethod));
            stmt.executeUpdate();
        });

        if (returnValue) {
            String selectLastQuery = "SELECT * FROM %s WHERE ID = (SELECT MAX(%s)  FROM %s);"
                    .formatted(RequestDataEntity.TABLE_NAME, RequestDataEntity.ID_COLUMN_NAME, RequestDataEntity.TABLE_NAME);

            final RequestDataEntity[] temp = new RequestDataEntity[1];

            DatabaseConnection.executeSelectClbck(stmt -> {
                ResultSet lastInserted = stmt.executeQuery(selectLastQuery);

                while (lastInserted.next()) {
                    int id = lastInserted.getInt(RequestDataEntity.ID_COLUMN_NAME);
                    int requestEntityId = lastInserted.getInt(RequestDataEntity.REQUEST_ENTITY_ID_COLUMN_NAME);
                    String requestUrl = lastInserted.getString(RequestDataEntity.URL_COLUMN_NAME);
                    HttpMethods method = HttpMethods.valueOf(lastInserted.getString(RequestDataEntity.METHOD_COLUMN_NAME));

                    // create temp {RequestDataEntity}
                    temp[0] = new RequestDataEntity(id, requestEntityId, requestUrl, method);
                }
            });

            return temp[0];
        }

        return null;
    }

    public static void updateColumn(int id, String columnName, String value) throws SQLException {
        String updateQuery = "UPDATE %s SET %s = ? WHERE %s = ?"
                .formatted(RequestDataEntity.TABLE_NAME, columnName, RequestDataEntity.ID_COLUMN_NAME);

        DatabaseConnection.executeCallbackPrepared(updateQuery, stmt -> {
            stmt.setString(1, value);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        });
    }
}
