package com.example.javafxhttpclient.entities;

import com.example.javafxhttpclient.core.enums.HttpMethods;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("unused")
public class RequestDataEntity {
    private String url;

    private HttpMethods method;

    @Nullable
    private String jsonContent;

    @Nullable
    private Map<String, String> headerData;

    @Nullable
    private Map<String, String> queryData;

    public RequestDataEntity(String url, HttpMethods method) {
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
}
