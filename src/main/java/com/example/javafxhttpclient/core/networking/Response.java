package com.example.javafxhttpclient.core.networking;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.Map;

public class Response {
    private Map<String, String> headers;
    private String data;
    private int statusCode;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private long responseTime;

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public Response(Map<String, String> headers, String responseJsonData, int statusCode) {
        this.headers = headers;
        this.data = responseJsonData;
        this.statusCode = statusCode;
    }

    public void printFormattedJson() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree;

        try {
            tree = mapper.readTree(data.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

//    public static String parseCommentsJson(String responseBody) {
//        for (int i = 0; i < comments.length(); i++) {
//            JSONObject comment = comments.getJSONObject(0);
//
//            var id = comment.getInt("id");
//            var postId = comment.getInt("postId");
//            var name = comment.getString("name");
//            var email = comment.getString("email");
//            var body = comment.getString("body");
//
//            System.out.println("===============");
//            System.out.println(id + "|" + postId + "|" + name + "|" + email + "|" + body.substring(0, 20));
//        }
//
//        return null;
//    }
