package com.example.javafxhttpclient.core.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface GeneralCallablePrepared {
    void onCallbackDb(PreparedStatement statement) throws SQLException;
}
