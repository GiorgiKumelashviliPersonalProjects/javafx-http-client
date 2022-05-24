package com.example.javafxhttpclient.core.networking;

import com.example.javafxhttpclient.core.enums.HttpMethods;
import com.example.javafxhttpclient.core.utils.Util;
import com.example.javafxhttpclient.exceptions.NoJsonResponseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Network {
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private URL targetURL;
    private String url;
    private Map<String, String> requestHeaders;

    private String requestBody;

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

    /**
     * response headers - httpURLConnection.getHeaderFields();
     * request headers - httpURLConnection.getRequestProperties();
     */
    public Response send(HttpMethods httpMethod) throws IOException, NoJsonResponseException {
        // open connection
        HttpURLConnection httpURLConnection = (HttpURLConnection) targetURL.openConnection();

        // Request setup
        httpURLConnection.setRequestMethod(httpMethod.toString());
        httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        httpURLConnection.setReadTimeout(READ_TIMEOUT);
        httpURLConnection.setRequestProperty("Content-Type", "application/json"); //! ONLY JSON IS SUPPORTED
        httpURLConnection.setRequestProperty("Accept", "application/json");

        // set headers
        if (requestHeaders != null && requestHeaders.size() != 0) {
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        // set request body
        if (requestBody != null &&
                !requestBody.isBlank() &&
                !requestBody.isEmpty() &&
                !requestBody.equals("{ }") &&
                !requestBody.equals("{}") &&
                httpMethod != HttpMethods.GET
        ) {
            httpURLConnection.setDoOutput(true);
            try (OutputStream os = httpURLConnection.getOutputStream();
                 OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
                osw.write(requestBody);
                osw.flush();
                os.flush();
            }
        }

        long startTime = System.currentTimeMillis();
        long elapsedTime;
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            elapsedTime = System.currentTimeMillis() - startTime;

            br.close();

        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException("Stream error -> method: %s, url: %s".formatted(httpMethod.toString(), url));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // for now only permitted json
        if (!httpURLConnection.getContentType().contains("application/json")) {
            throw new NoJsonResponseException("response type is not json -> method: %s, url: %s".formatted(httpMethod.toString(), url));
        }

        // headers
        int statusCode = httpURLConnection.getResponseCode();
        Map<String, List<String>> map = httpURLConnection.getHeaderFields();
        Map<String, String> headers = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getKey() != null) {
                String value = String.join(",", entry.getValue());
                headers.put(entry.getKey(), value);
            }
        }

        Response response = new Response(headers, sb.toString(), statusCode);
        response.setResponseTime(elapsedTime);

        // disconnect network
        httpURLConnection.disconnect();

        // return response object
        return response;
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
        try {
            // Try creating a valid URL
            new URL(url).toURI();
            return false;
        } catch (Exception e) {
            // If there was an Exception, while creating URL object
            return true;
        }
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        if (requestHeaders == null || requestHeaders.size() == 0) {
            return;
        }

        this.requestHeaders = requestHeaders;
    }

    public void setRequestHeader(String name, String value) {
        requestHeaders.put(name, value);
    }

    public void setRequestBody(String requestBody) {
        if (requestBody == null) {
            return;
        }

        this.requestBody = requestBody;
    }

    public void setRequestQuery(Map<String, String> queryData) throws MalformedURLException {
        if (queryData == null || queryData.size() == 0) {
            return;
        }

        for (Map.Entry<String, String> entry : queryData.entrySet()) {
            url = Util.appendQueryToUrl(url, "%s=%s".formatted(entry.getKey(), entry.getValue()));
        }

        if (isNotValidUrl(url)) {
            throw new MalformedURLException("Url not valid -> method: %s, url: %s".formatted("Not set yet", url));
        }

        targetURL = new URL(url);
    }
}
