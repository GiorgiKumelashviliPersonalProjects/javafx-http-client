package com.example.javafxhttpclient;

import com.example.javafxhttpclient.core.enums.HttpMethods;
import com.example.javafxhttpclient.core.networking.Network;
import com.example.javafxhttpclient.core.networking.Response;

public class NetworkTest {
    public static void main(String[] args) {
        String url = "https://jsonplaceholder.typicode.com/comments";
        String errorMessage = null;
        Response response = null;

        try {
            Network network = new Network(url);
            response = network.send(HttpMethods.GET);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        if (errorMessage != null) {
            System.err.print("Error occurred: " + errorMessage);
            return;
        }

        if (response != null) {
            response.printFormattedJson();
        }
    }
}
