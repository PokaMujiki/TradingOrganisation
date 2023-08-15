package com.project;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
