package com.example.javafxhttpclient.core.misc.codearea;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JSONFormatter implements Formatter {
    private static ObjectMapper mapper;

    public JSONFormatter() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Override
    public String format(String unformatted) throws IOException {
        JsonNode tree = mapper.readTree(unformatted);
        return mapper.writeValueAsString(tree);
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName();
    }
}