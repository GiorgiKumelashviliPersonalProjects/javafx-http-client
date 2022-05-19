package com.example.javafxhttpclient.core.networking;

import com.example.javafxhttpclient.core.enums.HttpMethods;
import com.example.javafxhttpclient.exceptions.NoJsonResponseException;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Network {
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private URL targetURL;
    private String url;

    public Network(String url) throws MalformedURLException {
        if (isNotValidUrl(url) || url == null) {
            throw new MalformedURLException("Url not valid -> method: %s, url: %s".formatted("Not set yet", url));
        }

        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        this.url = url;
        targetURL = new URL(url);
    }

    public Response send(HttpMethods httpMethod) throws IOException, NoJsonResponseException {
        // open connection
        HttpURLConnection httpURLConnection = (HttpURLConnection) targetURL.openConnection();
        // Request setup
        httpURLConnection.setRequestMethod(httpMethod.toString());
        httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        httpURLConnection.setReadTimeout(READ_TIMEOUT);
        httpURLConnection.setRequestProperty("Content-Type", "application/json"); //! ONLY JSON IS SUPPORTED
        //Send request

        InputStreamReader responseStream;
        BufferedReader responseReader;
        StringBuilder responseContent;


        try {
            responseStream = new InputStreamReader(httpURLConnection.getInputStream());
            responseReader = new BufferedReader(responseStream);
            responseContent = new StringBuilder();

            // read content from
            String line;
            while ((line = responseReader.readLine()) != null) responseContent.append(line);
            responseReader.close();

            // String content = httpURLConnection.getContent().toString();
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException("Stream error -> method: %s, url: %s".formatted(httpMethod.toString(), url));
        }

        // for now only permitted json
        if (!httpURLConnection.getContentType().contains("application/json")) {
            throw new NoJsonResponseException("response type is not json -> method: %s, url: %s".formatted(httpMethod.toString(), url));
        }

        // headers
        Map<String, List<String>> map = httpURLConnection.getHeaderFields();
        Map<String, String> headers = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getKey() != null) {
                String value = String.join(",", entry.getValue());
                headers.put(entry.getKey(), value);
            }
        }

        // set json content
        JSONArray responseJsonData;

        try {
            responseJsonData = new JSONArray(responseContent.toString());
        } catch (JSONException jsonException) {
            throw new JSONException("Error parsing json data -> method: %s, url: %s".formatted(httpMethod.toString(), url));
        }

        // disconnect network
        Objects.requireNonNull(httpURLConnection).disconnect();

        // return response object
        return new Response(headers, responseJsonData);
    }

    public Response send(HttpMethods httpMethod, String addUrl) throws IOException, NoJsonResponseException {
        if (addUrl != null) {
            updateUrl(addUrl);
        }

        return this.send(httpMethod);
    }

    private void updateUrl(String addUrl) throws MalformedURLException {
        url += addUrl;

        if (isNotValidUrl(url)) {
            throw new MalformedURLException("Url not valid -> method: %s, url: %s".formatted("Not set yet", url));
        }

        // update target url
        targetURL = new URL(url);
    }

    public static boolean isNotValidUrl(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return false;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return true;
        }
    }
}
