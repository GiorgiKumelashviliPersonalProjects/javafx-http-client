package com.example.javafxhttpclient.core.utils;

import java.sql.SQLException;
import java.sql.Statement;

public interface GeneralCallable {
    void onCallbackDb(Statement statement) throws SQLException;
}

