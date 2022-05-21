package com.example.javafxhttpclient.core.enums;

import java.util.HashMap;
import java.util.Map;

public enum ContentTabs {
    JSON("JSON"),
    HEADERS("HEADERS"),
    QUERY("QUERY");

    private final String tab;

    ContentTabs(String method) {
        this.tab = method;
    }

    @Override
    public String toString() {
        return tab;
    }

    public static String[] all() {
        Map<String, String> x = new HashMap<>() {{
            this.put("x","123");
            this.put("z","123");
        }};

        return new String[]{
                JSON.toString(),
                HEADERS.toString(),
                QUERY.toString(),
        };
    }
}
