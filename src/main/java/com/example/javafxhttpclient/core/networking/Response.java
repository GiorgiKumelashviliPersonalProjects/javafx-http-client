package com.example.javafxhttpclient.core.networking;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;

import java.io.IOException;
import java.util.Map;

public class Response {
    public Map<String, String> headers;
    public JSONArray responseJsonData;

    public Response(Map<String, String> headers, JSONArray responseJsonData) {
        this.headers = headers;
        this.responseJsonData = responseJsonData;
    }

    public void printFormattedJson() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = null;

        try {
            tree = mapper.readTree(responseJsonData.toString());
            System.out.println(mapper.writeValueAsString(tree));;
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
