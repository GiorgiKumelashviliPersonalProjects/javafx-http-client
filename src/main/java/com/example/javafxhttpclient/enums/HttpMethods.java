package com.example.javafxhttpclient.enums;

public enum HttpMethods {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    HEAD("HEAD");

    private final String method;

    HttpMethods(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return method;
    }

    public static String[] all() {
        return new String[]{
                GET.toString(),
                POST.toString(),
                PUT.toString(),
                PATCH.toString(),
                DELETE.toString(),
                OPTIONS.toString(),
                HEAD.toString()
        };
    }
}
